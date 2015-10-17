package com.whoops.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoops.po.FavorPost;
import com.whoops.po.Post;
import com.whoops.po.ResultObject;
import com.whoops.service.IFavorPostService;
import com.whoops.service.IPostService;
import com.whoops.util.CalendarUtil;
import com.whoops.web.BaseController;

@Controller
@RequestMapping
public class FavorPostController extends BaseController {

	@Autowired
	private IFavorPostService favorPostService;
	
	@Autowired
	private IPostService postService;
	
	@ResponseBody
	@RequestMapping("/favorPost/add")
	public ResultObject addFavorPost(FavorPost favorPost){
		ResultObject result = new ResultObject(1);
		favorPostService.addFavorPost(favorPost);
		
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/favorPost/list")
	public ResultObject listFavorPost(FavorPost favorPost,Integer pageNum){
		ResultObject result = new ResultObject(1);
		int[] page = this.page(pageNum);
		favorPost.setStartPage(page[0]);
		favorPost.setEndPage(page[1]);
		List<Post> list = postService.listFavorPostByUid(favorPost);
		processPostList(list);
		
		result.setData(list);
		return result;
	}
	
	private void processPostList(List<Post> list) {
		if (list != null && list.size() > 0) {
			for (Post post : list) {
				processPost(post);
			}
		}
	}

	private void processPost(Post post) {
		Date nowDate = new Date();
		Date date = post.getCreateDate();
		int min = CalendarUtil.minBetween(date, nowDate);
		if (min > 60 && min < 24 * 60) {
			post.setCreateDateLabel(min / 60 + "h");
		} else if (min >= 24 * 60) {
			post.setCreateDateLabel(min / (60 * 24) + "d");
		} else {
			post.setCreateDateLabel(min + "m");
		}

		int likeNum = post.getLikeNum() - post.getDislikeNum();
		post.setLikeNum(likeNum);
	}
}
