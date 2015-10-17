package com.whoops.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoops.po.Comment;
import com.whoops.po.Post;
import com.whoops.po.ResultObject;
import com.whoops.service.ICommentService;
import com.whoops.util.CalendarUtil;

@Controller
@RequestMapping
public class CommentController {

	@Autowired
	private ICommentService commentService;

	@ResponseBody
	@RequestMapping("/comment/getCommentByPostId")
	public ResultObject getCommentByPostId(Integer postId) {
		ResultObject resultObject = new ResultObject(1);
		List<Comment> list = commentService.selectCommentByPostId(postId);
		processCommentList(list);
		resultObject.setData(list);

		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/comment/add")
	public ResultObject add(Comment comment) {
		ResultObject resultObject = new ResultObject(1);
		
		if (StringUtils.hasLength(comment.getContent())) {
			String content = comment.getContent();
			if (content.startsWith("Optional(") && content.endsWith(")")) {
//				content.replace("Optional(", "");
				content = content.substring(10, content.length() - 2);
				comment.setContent(content);
			}
		}
		commentService.inserComment(comment);

		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/comment/like")
	public ResultObject like(Comment comment) {
		ResultObject resultObject = new ResultObject(1);
		resultObject.addMsg("success");
		int result = commentService.updateLikeById(comment);
		resultObject.setResult(result);
		resultObject.setData(comment);
		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/comment/unlike")
	public ResultObject unlike(Comment comment) {
		ResultObject resultObject = new ResultObject(1);
		resultObject.addMsg("success");
		int result = commentService.updateUnlikeById(comment);
		resultObject.setResult(result);
		resultObject.setData(comment);
		return resultObject;
	}
	
	
	private void processCommentList(List<Comment> list) {
		Date nowDate = new Date();
		if (list != null && list.size() > 0) {
			for (Comment comment : list) {
				Date date = comment.getCreateDate();
				int min = CalendarUtil.minBetween(date, nowDate);
				if (min > 60 && min < 24 * 60) {
					comment.setCreateDateLabel(min / 60 + "h");
				} else if (min >= 24 * 60) {
					comment.setCreateDateLabel(min / (60 * 24) + "d");
				} else {
					comment.setCreateDateLabel(min + "m");
				}
				int likeNum = comment.getLikeNum() - comment.getDislikeNum();
				comment.setLikeNum(likeNum);
			}
		}
	}

	
}
