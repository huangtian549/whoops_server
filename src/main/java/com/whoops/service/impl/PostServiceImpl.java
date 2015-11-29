package com.whoops.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoops.dao.AuditPostMapper;
import com.whoops.dao.FavorPostMapper;
import com.whoops.dao.LikePostMapper;
import com.whoops.dao.MsgMapper;
import com.whoops.dao.PostMapper;
import com.whoops.po.AuditPost;
import com.whoops.po.AuditPostExample;
import com.whoops.po.FavorPost;
import com.whoops.po.FavorPostExample;
import com.whoops.po.LikePost;
import com.whoops.po.LikePostExample;
import com.whoops.po.Msg;
import com.whoops.po.Post;
import com.whoops.service.IPostService;

@Transactional
@Service
public class PostServiceImpl implements IPostService {
	
	Logger logger = Logger.getLogger(PostServiceImpl.class);

	@Autowired
	private MsgMapper msgDao;
	
	@Autowired
	private PostMapper postDao;
	
	@Autowired
	private AuditPostMapper auditPostDao;
	
	@Autowired
	private LikePostMapper likePostDao;
	
	@Autowired
	private FavorPostMapper favorPostDao;

	static String listNewByLocation = "select post.*, "
			+ " ACOS(SIN((? * 3.1415) / 180 ) *SIN((latitude * 3.1415) / 180 ) +COS((? * 3.1415) / 180 ) * COS((latitude * 3.1415) / 180 ) *COS(( ? * 3.1415) / 180 - (longitude * 3.1415) / 180 ) ) * 3959 as distance "
			+ " from post where  latitude > ?-1 and  latitude < ? +1 and longitude > ? -1 and  longitude < ? +1 " + " having distance < ?" + " order by create_date desc limit ?,?";

	static String listHotByLocation = "select post.*, "
			+ " ACOS(SIN((? * 3.1415) / 180 ) *SIN((latitude * 3.1415) / 180 ) +COS((? * 3.1415) / 180 ) * COS((latitude * 3.1415) / 180 ) *COS(( ? * 3.1415) / 180 - (longitude * 3.1415) / 180 ) ) * 3959 as distance "
			+ " from post where  latitude > ?-1 and  latitude < ? +1 and longitude > ? -1 and  longitude < ? +1 " + " having distance < ?" + " order by likeNum desc limit ?, ?";

	static String listNewBySchool = "select * FROM post p WHERE p.school_id = ? order by create_date desc limit ?,?";

	static String listHotBySchool = "select * FROM post p WHERE p.school_id = ? order by like_num desc limit ?,?";

	static String addPost = "insert into post(content, like_num, dislike_num, image, longitude,latitude,school_id,nick_name,create_date) values(?,?,?,?,?,?,?,?,?)";

	@Override
	public List<Post> listNewByLocation(Post post) {
		List<Post> list = postDao.listNewByLocation(post);
		addLikeAndUnlikePostFlag(list, post.getUid());
		addFavorPostFlag(list, post.getUid());
		return list;
	}

	@Override
	public void addPost(Post post) {
		postDao.insertSelective(post);


	}

	@Override
	public List<Post> listHotByLocation(Post post) {
		List<Post> list = postDao.listHotByLocation(post);
		addFavorPostFlag(list, post.getUid());
		addLikeAndUnlikePostFlag(list, post.getUid());
		return list;
	}

	@Override
	public List<Post> listNewBySchool(Post post) {
		List<Post> list = postDao.listNewBySchool(post);
		addLikeAndUnlikePostFlag(list, post.getUid());
		addFavorPostFlag(list, post.getUid());
		return list;
	}

	@Override
	public List<Post> listHotBySchool(Post post) {
		List<Post> list = postDao.listHotBySchool(post);
		addLikeAndUnlikePostFlag(list, post.getUid());
		addFavorPostFlag(list, post.getUid());
		return list;
	}

	@Override
	public List<Post> listByUid(Post post) {
		List<Post> list = postDao.listByUid(post);
		addLikeAndUnlikePostFlag(list, post.getUid());
		return list;
	}

	@Override
	public List<Post> listByCommentAndUid(Post post) {
		List<Post> list =  postDao.listByCommentAndUid(post);
		addLikeAndUnlikePostFlag(list, post.getUid());
		addFavorPostFlag(list, post.getUid());
		return list;
	}

	@Override
	public Post getPostById(Integer id, Integer uid) {
		Post post =  postDao.selectByPrimaryKey(id);
		if(uid != null){
			FavorPostExample example = new FavorPostExample();
			example.createCriteria().andUidEqualTo(uid);
			List<FavorPost> favorPosts = favorPostDao.selectByExample(example );
			if (favorPosts != null && favorPosts.size() > 0 ) {
				for (FavorPost favorPost : favorPosts) {
					
						if (post.getId().intValue() == favorPost.getPostId().intValue()) {
							post.setIsFavor("favor");
						}
					
				}
			}
			
			List<Post> list = new ArrayList<Post>();
			list.add(post);
			addLikeAndUnlikePostFlag(list, uid);
		}
		return post;
	}

	@Override
	public void updatePostById(Post post) {
		postDao.updateByPrimaryKeySelective(post);

	}

	@Override
	public int[] updateLikeById(Post post) {

		LikePostExample example = new LikePostExample();
		example.createCriteria().andPostIdEqualTo(post.getId()).andUidEqualTo(post.getUid());
		
		List<LikePost> list = likePostDao.selectByExample(example);
		Post post2 = postDao.selectByPrimaryKey(post.getId());
		if(list != null && list.size() > 0){
			boolean isDisliked = false;
			int likePostId = 0;
			for (LikePost likePost : list) {
				if (likePost.getType() == -1){
					isDisliked = true;
					likePostId = likePost.getId();
				}
			}
			//如果现在post的状态是某个用户先dislike，又点击like,则like+1,dislike-1, 删除dislike记录，添加一条like记录
			if (isDisliked) {
				int likeNum = post2.getLikeNum() + 1;
				post.setLikeNum(likeNum); //如果没有喜欢过，则加1
				post.setDislikeNum(post2.getDislikeNum() - 1);
				
				logger.info("like_num record:postId:" + post.getId() + ":old likeNum:"+ post2.getLikeNum() + " new likeNum:" + post.getLikeNum() + " dislikeNum:" + post.getDislikeNum());
				
				postDao.updateByPrimaryKeySelective(post);
				LikePost record = new LikePost();
				record.setPostId(post.getId());
				record.setUid(post.getUid());
				record.setType(1);
				likePostDao.insertSelective(record );
				likePostDao.deleteByPrimaryKey(likePostId);
				addMsg(post2.getUid(), post2.getContent(), "someone cancelled to like and unlike your post ", post2.getId(),4);
				return new int[] { likeNum - post2.getDislikeNum() + 1, 1};
			} else {
				int likeNum = post2.getLikeNum() - 1;
				post.setLikeNum(likeNum); //如果已经喜欢过了，再点击，则是取消喜欢
				logger.info("like_num record:postId:" + post.getId() + ":old likeNum:"+ post2.getLikeNum() + " new likeNum:" + post.getLikeNum() + " dislikeNum:" + post.getDislikeNum());
				postDao.updateByPrimaryKeySelective(post);
				likePostDao.deleteByPrimaryKey(list.get(0).getId());
				addMsg(post2.getUid(), post2.getContent(), "someone cancelled to like your post ",post2.getId(),3);
				return new int[] { likeNum - post2.getDislikeNum(), 0};

			}
		}else{
			int likeNum = post2.getLikeNum() + 1;
			post.setLikeNum(likeNum); //如果没有喜欢过，则加1
			logger.info("like_num record:postId:" + post.getId() + ":old likeNum:"+ post2.getLikeNum() + " new likeNum:" + post.getLikeNum() + " dislikeNum:" + post.getDislikeNum());
			postDao.updateByPrimaryKeySelective(post);
			LikePost record = new LikePost();
			record.setPostId(post.getId());
			record.setUid(post.getUid());
			record.setType(1);
			likePostDao.insertSelective(record );
			addMsg(post2.getUid(), post2.getContent(), "someone like your post ",post2.getId(),2);
			return new int[] { likeNum - post2.getDislikeNum(), 1};
		}
		

	}

	@Override
	public int[] updateUnlikeById(Post post) {

		LikePostExample example = new LikePostExample();
		example.createCriteria().andPostIdEqualTo(post.getId()).andUidEqualTo(post.getUid());

		List<LikePost> list = likePostDao.selectByExample(example);
		Post post2 = postDao.selectByPrimaryKey(post.getId());
		if(list != null && list.size() > 0){
			boolean isLike = false;
			int likePostId = 0;
			for (LikePost likePost : list) {
				if(likePost.getType() == 1){
					isLike = true;
					likePostId = likePost.getId();
				}
			}
			if (isLike) {
				int dislikeNum = post2.getDislikeNum() + 1;
				post.setDislikeNum(dislikeNum); //如果没有dislike过，则加1
				post.setLikeNum(post2.getLikeNum() - 1);
				
				logger.info("like_num record:postId:" + post.getId() + ":old likeNum:"+ post2.getLikeNum() + " new likeNum:" + post.getLikeNum() + "old dislikeNum:" + post2.getDislikeNum() + " dislikeNum:" + post.getDislikeNum());
				postDao.updateByPrimaryKeySelective(post);
				LikePost record = new LikePost();
				record.setPostId(post.getId());
				record.setUid(post.getUid());
				record.setType(-1);
				likePostDao.insertSelective(record );
				likePostDao.deleteByPrimaryKey(likePostId);
				addMsg(post2.getUid(), post2.getContent(), "someone cancelled to unlike and like your post ",post2.getId(), 2);
				return new int[] { post2.getLikeNum() -1 - dislikeNum, -1};
				
			} else {
				int dislikeNum = post2.getDislikeNum() - 1;
				post.setDislikeNum(dislikeNum); //如果已经dislike过了，再点击，则是取消喜欢
				
				logger.info("like_num record:postId:" + post.getId() + ":old likeNum:"+ post2.getLikeNum() + " new likeNum:" + post.getLikeNum() + " dislikeNum:" + post.getDislikeNum());
				postDao.updateByPrimaryKeySelective(post);
				likePostDao.deleteByPrimaryKey(list.get(0).getId());
				addMsg(post2.getUid(), post2.getContent(), "someone cancelled to unlike your post ",post2.getId(),5);
				return new int[] { post2.getLikeNum() - dislikeNum, 0};
				
			}
		}else{
			int dislikeNum = post2.getDislikeNum() + 1;
			post.setDislikeNum(dislikeNum); //如果没有dislike过，则加1
			postDao.updateByPrimaryKeySelective(post);
			logger.info("like_num record:postId:" + post.getId() + ":old likeNum:"+ post2.getLikeNum() + " new likeNum:" + post.getLikeNum() + " dislikeNum:" + post.getDislikeNum());
			LikePost record = new LikePost();
			record.setPostId(post.getId());
			record.setUid(post.getUid());
			record.setType(-1);
			likePostDao.insertSelective(record );
			addMsg(post2.getUid(), post2.getContent(), "someone unlike your post ",post2.getId(),4);
			return new int[] { post2.getLikeNum() - dislikeNum, -1};
		}

	}

	@Override
	public List<Post> listHotAll(Post post) {
		List<Post> postList = postDao.listHotAll(post);
		addFavorPostFlag(postList, post.getUid());
		addLikeAndUnlikePostFlag(postList, post.getUid());
		return postList;
	}

	@Override
	public List<Post> listFavorPostByUid(FavorPost favorPost) {
		List<Post> postList = postDao.listFavorPostByUid(favorPost);
		addFavorPostFlag(postList, favorPost.getUid());
		addLikeAndUnlikePostFlag(postList, favorPost.getUid());
		return postList;
	}
	
	
	
	private void addMsg(int uid, String content, String flag, Integer postId, Integer type){
		Msg record = new Msg();
		if(content != null && content.length() > 20){
			content = content.substring(0, 20);
		}
		record.setMsg(flag + "\"" + content + "\"");
		record.setPostId(postId);
		record.setUid(uid);
		record.setStatus(0);
		record.setType(type);
		msgDao.insert(record);
	}

	@Override
	public void reportPost(AuditPost auditPost) {
		AuditPostExample example = new AuditPostExample();
		example.createCriteria().andPostIdEqualTo(auditPost.getPostId()).andUidEqualTo(auditPost.getUid());
		List<AuditPost>  list = auditPostDao.selectByExample(example);
		if(list != null && list.size() > 0){
			return;
		}else{
			auditPostDao.insertSelective(auditPost);
		}
		
	}
	
	private void addFavorPostFlag(List<Post> list, Integer uid){
		FavorPostExample example = new FavorPostExample();
		example.createCriteria().andUidEqualTo(uid);
		List<FavorPost> favorPosts = favorPostDao.selectByExample(example );
		if (favorPosts != null && favorPosts.size() > 0 && list != null) {
			for (FavorPost favorPost : favorPosts) {
				for (Post post : list) {
					if (post.getId().intValue() == favorPost.getPostId().intValue()) {
						post.setIsFavor("favor");
					}
				}
			}
		}
	}
	
	private void addLikeAndUnlikePostFlag(List<Post> list, Integer uid){
		LikePostExample example = new LikePostExample();
		example.createCriteria().andUidEqualTo(uid);
		List<LikePost> likePosts = likePostDao.selectByExample(example );
		if (likePosts != null && likePosts.size() > 0 && list != null) {
			for (LikePost likePost : likePosts) {
				for (Post post : list) {
					if (post.getId().intValue() == likePost.getPostId().intValue()) {
						if (likePost.getType() == 1) {
							post.setIsLike("1");
						}else {
							post.setIsLike("-1");
						}
					}
				}
			}
		}
	}
	
}
