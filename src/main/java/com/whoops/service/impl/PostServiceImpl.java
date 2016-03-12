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
import com.whoops.po.PostExample;
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
		List<Post> list = null;
		if (post.getStartPage() <= 1) {
			PostExample example = new PostExample();
			example.createCriteria().andActivityIdEqualTo(1).andSchoolIdEqualTo(post.getSchoolId());
			list = postDao.selectByExample(example );
			List<Post> list2= postDao.listNewBySchool(post);
			list.addAll(list2);
		}else {
			list = postDao.listNewBySchool(post);
		}
		
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
		addFavorPostFlag(list, post.getUid());
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
				LikePost record = new LikePost();
				record.setPostId(post.getId());
				record.setUid(post.getUid());
				record.setType(1);
				likePostDao.insertSelective(record );
				likePostDao.deleteByPrimaryKey(likePostId);
				
				int likeNum = post2.getLikeNum() + 1;
				int disLikeNum = post2.getDislikeNum();
				post.setLikeNum(likeNum); //如果没有喜欢过，则加1
				if (disLikeNum >= 1) {
					disLikeNum -= 1;
				}
				post.setDislikeNum(disLikeNum);
				
				logger.info("like_num record:postId:" + post.getId() + ":old likeNum:"+ post2.getLikeNum() + " new likeNum:" + post.getLikeNum() + " dislikeNum:" + post.getDislikeNum());
				post.setUid(null); //会从接口中传入uid,但是此uid是点击dislike的用户id，不是发帖人的用户id，所以清空
				postDao.updateByPrimaryKeySelective(post);
				addMsg(post2.getUid(), post2.getContent(), "Someone liked your post ", post2.getId(),4);
				return new int[] { likeNum - disLikeNum, 1};
			} else {
				int likeNum = post2.getLikeNum();
				if (post2.getLikeNum() >= 1) {
					likeNum = post2.getLikeNum() - 1;
					
				}
				post.setLikeNum(likeNum); //如果已经喜欢过了，再点击，则是取消喜欢
				logger.info("like_num record:postId:" + post.getId() + ":old likeNum:"+ post2.getLikeNum() + " new likeNum:" + post.getLikeNum() + " dislikeNum:" + post.getDislikeNum());
				post.setUid(null); //会从接口中传入uid,但是此uid是点击like的用户id，不是发帖人的用户id，所以清空
				postDao.updateByPrimaryKeySelective(post);
				likePostDao.deleteByPrimaryKey(list.get(0).getId());
//				addMsg(post2.getUid(), post2.getContent(), "Someone cancelled to like your post ",post2.getId(),3);
				return new int[] { likeNum - post2.getDislikeNum(), 0};

			}
		}else{
			LikePost record = new LikePost();
			record.setPostId(post.getId());
			record.setUid(post.getUid());
			record.setType(1);
			likePostDao.insertSelective(record );
			
			post.setUid(null); //会从接口中传入uid,但是此uid是点击like的用户id，不是发帖人的用户id，所以清空
			int likeNum = post2.getLikeNum() + 1;
			post.setLikeNum(likeNum); //如果没有喜欢过，则加1
			logger.info("like_num record:postId:" + post.getId() + ":old likeNum:"+ post2.getLikeNum() + " new likeNum:" + post.getLikeNum() + " dislikeNum:" + post.getDislikeNum());
			postDao.updateByPrimaryKeySelective(post);
			addMsg(post2.getUid(), post2.getContent(), "Someone liked your post ",post2.getId(),2);
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
				LikePost record = new LikePost();
				record.setPostId(post.getId());
				record.setUid(post.getUid());
				record.setType(-1);
				likePostDao.insertSelective(record );
				likePostDao.deleteByPrimaryKey(likePostId);
				
				int dislikeNum = post2.getDislikeNum() + 1;
				int likeNum = post2.getLikeNum();
				post.setDislikeNum(dislikeNum); //如果没有dislike过，则加1
				if (likeNum >= 1) {
					likeNum = likeNum - 1;
				}
				post.setLikeNum(likeNum);
				
				logger.info("like_num record:postId:" + post.getId() + ":old likeNum:"+ post2.getLikeNum() + " new likeNum:" + post.getLikeNum() + "old dislikeNum:" + post2.getDislikeNum() + " dislikeNum:" + post.getDislikeNum());
				post.setUid(null); //会从接口中传入uid,但是此uid是点击dislike的用户id，不是发帖人的用户id，所以清空
				postDao.updateByPrimaryKeySelective(post);
				addMsg(post2.getUid(), post2.getContent(), "Someone disliked your post ",post2.getId(), 2);
				return new int[] { likeNum - dislikeNum, -1};
				
			} else {
				int dislikeNum = post2.getDislikeNum();
				if (post2.getDislikeNum() >= 1) {
					dislikeNum = post2.getDislikeNum() - 1;
				}
				post.setDislikeNum(dislikeNum); //如果已经dislike过了，再点击，则是取消喜欢
				
				logger.info("like_num record:postId:" + post.getId() + ":old likeNum:"+ post2.getLikeNum() + " new likeNum:" + post.getLikeNum() + " dislikeNum:" + post.getDislikeNum());
				post.setUid(null); //会从接口中传入uid,但是此uid是点击dislike的用户id，不是发帖人的用户id，所以清空
				postDao.updateByPrimaryKeySelective(post);
				likePostDao.deleteByPrimaryKey(list.get(0).getId());
//				addMsg(post2.getUid(), post2.getContent(), "Someone cancelled to disliked your post ",post2.getId(),5);
				return new int[] { post2.getLikeNum() - dislikeNum, 0};
				
			}
		}else{
			LikePost record = new LikePost();
			record.setPostId(post.getId());
			record.setUid(post.getUid());
			record.setType(-1);
			likePostDao.insertSelective(record );
			
			post.setUid(null); //会从接口中传入uid,但是此uid是点击dislike的用户id，不是发帖人的用户id，所以清空
			int dislikeNum = post2.getDislikeNum() + 1;
			post.setDislikeNum(dislikeNum); //如果没有dislike过，则加1
			postDao.updateByPrimaryKeySelective(post);
			logger.info("like_num record:postId:" + post.getId() + ":old likeNum:"+ post2.getLikeNum() + " new likeNum:" + post.getLikeNum() + " dislikeNum:" + post.getDislikeNum());
			addMsg(post2.getUid(), post2.getContent(), "Someone disliked your post ",post2.getId(),4);
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
		if (uid == null) {
			return;
		}
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
		if (uid == null) {
			return;
		}
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

	@Override
	public List<Post> listByActivity(Post post) {
		List<Post> list = new ArrayList<Post>();
		if(post.getStartPage() <= 1) {
			Post post2 = postDao.selectByPrimaryKey(1836);
			list.add(post2);
		}
		List<Post> list1 = postDao.listNewActivityPost(post);
		List<Post> list2 = postDao.listByActivity(post);
		list.addAll(list1);
		list.addAll(list2);
		addLikeAndUnlikePostFlag(list, post.getUid());
		addFavorPostFlag(list, post.getUid());
		return list;
	}

	@Override
	public List<Post> listByContent(String content) {
		PostExample example = new PostExample();
		example.createCriteria().andContentLike(content);
		return postDao.selectByExample(example );
	}

	@Override
	public void deletePost(Post post) {
		postDao.deleteByPrimaryKey(post.getId());
		
	}

	@Override
	public void updatePost(Post post) {
		postDao.updateByPrimaryKeySelective(post);
	}

	@Override
	public List<Post> listNewActivityPost(Post post) {
		return postDao.listNewActivityPost(post);
	}
	
}
