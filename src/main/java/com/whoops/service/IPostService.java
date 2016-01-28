package com.whoops.service;

import java.util.List;

import com.whoops.po.AuditPost;
import com.whoops.po.FavorPost;
import com.whoops.po.Post;

public interface IPostService {

	void addPost(Post post);

	Post getPostById(Integer id, Integer uid);

	void updatePostById(Post post);

	List<Post> listHotByLocation(Post post);

	List<Post> listNewBySchool(Post post);

	List<Post> listHotBySchool(Post post);

	List<Post> listNewByLocation(Post post);

	List<Post> listByUid(Post post);
	
	List<Post> listHotAll(Post post);

	List<Post> listByCommentAndUid(Post post);

	int[] updateUnlikeById(Post post);

	int[] updateLikeById(Post post);
	
	List<Post> listFavorPostByUid(FavorPost favorPost);
	
	void reportPost(AuditPost auditPost);
	
	List<Post> listByActivity(Post post);
}
