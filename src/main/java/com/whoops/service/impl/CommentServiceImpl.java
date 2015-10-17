package com.whoops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoops.dao.CommentMapper;
import com.whoops.dao.LikeCommentMapper;
import com.whoops.dao.MsgMapper;
import com.whoops.dao.PostMapper;
import com.whoops.po.Comment;
import com.whoops.po.CommentExample;
import com.whoops.po.LikeComment;
import com.whoops.po.LikeCommentExample;
import com.whoops.po.Msg;
import com.whoops.po.Post;
import com.whoops.service.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private MsgMapper msgDao;
	@Autowired
	private PostMapper postDao;
	@Autowired
	private CommentMapper commentDao;
	@Autowired
	private LikeCommentMapper likeCommentDao;
	
	@Override
	public int selectCommentCountByPostId(int postId) {
		CommentExample example = new CommentExample();
		example.createCriteria().andPostIdEqualTo(postId);
		return commentDao.countByExample(example);
	}

	@Override
	public List<Comment> selectCommentByPostId(int postId) {
		CommentExample example = new CommentExample();
		example.createCriteria().andPostIdEqualTo(postId);
		return commentDao.selectByExample(example);
	}

	@Override
	public void inserComment(Comment comment) {
		commentDao.insertSelective(comment);
		Post post = postDao.selectByPrimaryKey(comment.getPostId());
		post.setCommentCount(post.getCommentCount() + 1);
		postDao.updateByPrimaryKeySelective(post);
		
		Msg record = new Msg();
		String content = post.getContent();
		if(content != null && content.length() > 20){
			content = content.substring(0, 20);
		}
		record.setMsg("@@someone_reply@@ " + "\"" + post.getContent() + "\"");
		record.setUid(comment.getUid());
		record.setPostId(comment.getPostId());
		record.setStatus(0);
		record.setType(1);
		msgDao.insert(record );
	}

	@Override
	public int updateLikeById(Comment comment) {
		LikeCommentExample example = new LikeCommentExample();
		example.createCriteria().andCommentIdEqualTo(comment.getId()).andUidEqualTo(comment.getUid()).andUidEqualTo(comment.getUid());
		
		List<LikeComment> list = likeCommentDao.selectByExample(example); 
		Comment comment2 = commentDao.selectByPrimaryKey(comment.getId());
		if(list != null && list.size() > 0){
			boolean isDisliked = false;
			int likePostId = 0;
			for (LikeComment likeComment : list) {
				if (likeComment.getType() == -1){
					isDisliked = true;
					likePostId = likeComment.getId();
				}
			}
			//如果现在post的状态是某个用户先dislike，又点击like,则like+1,dislike-1, 删除dislike记录，添加一条like记录
			if (isDisliked) {
				int likeNum = comment2.getLikeNum() + 1;
				comment.setLikeNum(likeNum); //如果没有喜欢过，则加1
				comment.setDislikeNum(comment2.getDislikeNum() - 1);
				commentDao.updateByPrimaryKeySelective(comment);
				LikeComment record = new LikeComment();
				record.setCommentId(comment.getId());
				record.setUid(comment.getUid());
				record.setType(1);
				likeCommentDao.insertSelective(record );
				likeCommentDao.deleteByPrimaryKey(likePostId);
				addMsg(comment2.getUid(), comment2.getContent(), "@@someone_cancel_unlike_and_like_comment@@ ", comment2.getPostId());
				return likeNum - comment2.getDislikeNum() + 1;
			} else {
				int likeNum = comment2.getLikeNum() - 1;
				comment.setLikeNum(likeNum); //如果已经喜欢过了，再点击，则是取消喜欢
				commentDao.updateByPrimaryKeySelective(comment);
				likeCommentDao.deleteByPrimaryKey(list.get(0).getId());
				addMsg(comment2.getUid(), comment2.getContent(), "@@someone_cancel_like_comment@@ ", comment2.getPostId());
				return likeNum - comment2.getDislikeNum();

			}
		}else{
			int likeNum = comment2.getLikeNum() + 1;
			comment.setLikeNum(likeNum); //如果没有喜欢过，则加1
			commentDao.updateByPrimaryKeySelective(comment);
			LikeComment record = new LikeComment();
			record.setCommentId(comment.getId());
			record.setUid(comment.getUid());
			record.setType(1);
			likeCommentDao.insertSelective(record );
			addMsg(comment2.getUid(), comment2.getContent(), "@@someone_like_comment@@ ", comment2.getPostId());
			return likeNum - comment2.getDislikeNum();
		}

	}

	@Override
	public int updateUnlikeById(Comment comment) {
		LikeCommentExample example = new LikeCommentExample();
		example.createCriteria().andCommentIdEqualTo(comment.getId()).andUidEqualTo(comment.getUid());

		List<LikeComment> list = likeCommentDao.selectByExample(example);
		Comment comment2 = commentDao.selectByPrimaryKey(comment.getId());
		if(list != null && list.size() > 0){
			boolean isLike = false;
			int likeCommentId = 0;
			for (LikeComment likePost : list) {
				if(likePost.getType() == 1){
					isLike = true;
					likeCommentId = likePost.getId();
				}
			}
			if (isLike) {
				int dislikeNum = comment2.getDislikeNum() + 1;
				comment.setDislikeNum(dislikeNum); //如果没有dislike过，则加1
				comment.setLikeNum(comment2.getLikeNum() - 1);
				commentDao.updateByPrimaryKeySelective(comment);
				LikeComment record = new LikeComment();
				record.setCommentId(comment.getId());
				record.setUid(comment.getUid());
				record.setType(-1);
				likeCommentDao.insertSelective(record );
				likeCommentDao.deleteByPrimaryKey(likeCommentId);
				addMsg(comment2.getUid(), comment2.getContent(), "@@someone_cancel_unlike_and_like_comment@@ ", comment2.getPostId());
				return comment2.getLikeNum() -1 - dislikeNum;
				
			} else {
				int dislikeNum = comment2.getDislikeNum() - 1;
				comment.setDislikeNum(dislikeNum); //如果已经dislike过了，再点击，则是取消喜欢
				commentDao.updateByPrimaryKeySelective(comment);
				likeCommentDao.deleteByPrimaryKey(list.get(0).getId());
				addMsg(comment2.getUid(), comment2.getContent(), "@@someone_cancel_unlike_comment@@ ", comment2.getPostId());
				return comment2.getLikeNum() - dislikeNum;
				
			}
		}else{
			int dislikeNum = comment2.getDislikeNum() + 1;
			comment.setDislikeNum(dislikeNum); //如果没有dislike过，则加1
			commentDao.updateByPrimaryKeySelective(comment);
			LikeComment record = new LikeComment();
			record.setCommentId(comment.getId());
			record.setUid(comment.getUid());
			record.setType(-1);
			likeCommentDao.insertSelective(record );
			addMsg(comment2.getUid(), comment2.getContent(), "@@someone_unlike_comment@@ ", comment2.getPostId());
			return comment2.getLikeNum() - dislikeNum;
		}

	}

	private void addMsg(int uid, String content, String flag, Integer postId){
		Msg record = new Msg();
		if(content != null && content.length() > 20){
			content = content.substring(0, 20);
		}
		record.setMsg(flag + "\"" + content + "\"");
		record.setUid(uid);
		record.setStatus(0);
		record.setType(2);
		record.setPostId(postId);
		msgDao.insert(record);
	}
}
