package com.whoops.service;

import java.util.List;

import com.whoops.po.Comment;

public interface ICommentService {

	int selectCommentCountByPostId(int postId);

	List<Comment> selectCommentByPostId(int postId);

	void inserComment(Comment comment);

	int updateLikeById(Comment comment);

	int updateUnlikeById(Comment comment);
	
	List<Comment> selectCommentByContent(String content);
	
	void deleteComment(Comment comment);

}
