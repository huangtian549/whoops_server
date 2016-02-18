package com.whoops.web.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.whoops.po.AuditPost;
import com.whoops.po.Post;
import com.whoops.po.ResultObject;
import com.whoops.po.School;
import com.whoops.service.IPostService;
import com.whoops.service.ISchoolService;
import com.whoops.util.CalendarUtil;
import com.whoops.web.BaseController;

@Controller
@RequestMapping("/post/*")
public class PostController extends BaseController {

	@Autowired
	private IPostService postService;
	
	@Autowired
	private ISchoolService schoolService;

	@ResponseBody
	@RequestMapping("/post/add")
	public ResultObject add(Post po, @RequestParam("file")MultipartFile[] file, HttpServletRequest request) throws Exception {
		String separator = "/";
		String path = "/home/data";
//		String path = "/Users/liunaikun/Pictures";


		Date date = new Date();
		String dateString = CalendarUtil.getDateString(date, CalendarUtil.SHORT_DATE_FORMAT_NO_DASH);
		path = path + separator + dateString;
		File filePath = new File(path);
		// 判断文件夹是否存在,如果不存在则创建文件夹
		if (!filePath.exists()) {
			filePath.mkdir();
		}
		
		if(file != null && file.length > 0){
			StringBuilder sb =  new StringBuilder();
			for (int i = 0; i < file.length; i++) {
				String uuid = UUID.randomUUID().toString();
				String fileName = uuid + ".jpg";
				FileUtils.copyInputStreamToFile(file[i].getInputStream(), new File(path, fileName));
				String imagePath = path + "/" + fileName;
				imagePath = imagePath.substring(5);
				sb.append(imagePath).append(",");
			}
			po.setImage(sb.substring(0, sb.length() -1));
		}
		
		if(po.getSchoolId() != null){
			Float[] location = getLocationBySchollId(po.getSchoolId());
			if(location != null){
				po.setLatitude(location[0]);
				po.setLongitude(location[1]);
			}
		}

		ResultObject resultObject = new ResultObject(1);
		resultObject.addMsg("success");
		postService.addPost(po);

		return resultObject;
	}
	
	@RequestMapping("/post/addForWeb")
	public String addForWeb(Post po, @RequestParam("file")MultipartFile[] file, HttpServletRequest request) throws Exception {
		String separator = "/";
		String path = "/home/data";
//		String path = "/Users/liunaikun/Pictures";


		Date date = new Date();
		String dateString = CalendarUtil.getDateString(date, CalendarUtil.SHORT_DATE_FORMAT_NO_DASH);
		path = path + separator + dateString;
		File filePath = new File(path);
		// 判断文件夹是否存在,如果不存在则创建文件夹
		if (!filePath.exists()) {
			filePath.mkdir();
		}
		
		if(file != null && file.length > 0){
			StringBuilder sb =  new StringBuilder();
			for (int i = 0; i < file.length; i++) {
				if(file[i].getSize() <1){
					continue;
				}
				String uuid = UUID.randomUUID().toString();
				String fileName = uuid + ".jpg";
				FileUtils.copyInputStreamToFile(file[i].getInputStream(), new File(path, fileName));
				String imagePath = path + "/" + fileName;
				imagePath = imagePath.substring(5);
				sb.append(imagePath).append(",");
			}
			if (sb.length() > 0) {
				po.setImage(sb.substring(0, sb.length() -1));
			}
		}
		
		if(po.getSchoolId() != null){
			Float[] location = getLocationBySchollId(po.getSchoolId());
			if(location != null){
				po.setLatitude(location[0]);
				po.setLongitude(location[1]);
			}
		}

		postService.addPost(po);
		return "index";
	}


	@ResponseBody
	@RequestMapping("/post/addNoPic")
	public ResultObject addNoPic(Post po) throws Exception {

		if(po.getSchoolId() != null){
			Float[] location = getLocationBySchollId(po.getSchoolId());
			if(location != null){
				po.setLatitude(location[0]);
				po.setLongitude(location[1]);
			}
		}
		
		ResultObject resultObject = new ResultObject(1);
		resultObject.addMsg("success");
		postService.addPost(po);

		return resultObject;
	}
	
	@ResponseBody
	@RequestMapping("/post/get")
	public ResultObject get(Integer id, Integer uid) {
		ResultObject resultObject = new ResultObject(1);
		resultObject.addMsg("success");
		Post post = postService.getPostById(id,uid);
		processPost(post);
		resultObject.setData(post);
		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/post/like")
	public ResultObject likePost(Post post) {
		ResultObject resultObject = new ResultObject(1);
		resultObject.addMsg("success");
		int[] result = postService.updateLikeById(post);
		resultObject.setResult(result[0]);
		post.setIsLike(result[1] + "");
		resultObject.setData(post);
		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/post/unlike")
	public ResultObject unlikePost(Post post) {
		ResultObject resultObject = new ResultObject(1);
		resultObject.addMsg("success");
		int result[] = postService.updateUnlikeById(post);
		resultObject.setResult(result[0]);
		post.setIsLike(result[1] + "");
		resultObject.setData(post);
		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/post/listNewByLocation")
	public ResultObject listNewByLocation(Post post, Integer pageNum) {
		ResultObject resultObject = new ResultObject(1);
		int[] page = this.page(pageNum);
		post.setStartPage(page[0]);
		post.setEndPage(page[1]);
		List<Post> list = postService.listNewByLocation(post);
		processPostList(list);
		resultObject.addMsg("success");
		resultObject.setData(list);
		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/post/listHotByLocation")
	public ResultObject listHotByLocation(Post post, Integer pageNum) {
		ResultObject resultObject = new ResultObject(1);
		int[] page = this.page(pageNum);
		post.setStartPage(page[0]);
		post.setEndPage(page[1]);
		List<Post> list = postService.listHotByLocation(post);
		processPostList(list);
		resultObject.addMsg("success");
		resultObject.setData(list);
		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/post/listNewBySchool")
	public ResultObject listNewBySchool(Post post, Integer pageNum) {
		ResultObject resultObject = new ResultObject(1);
		int[] page = this.page(pageNum);
		post.setStartPage(page[0]);
		post.setEndPage(page[1]);
		List<Post> list = postService.listNewBySchool(post);
		processPostList(list);
		resultObject.addMsg("success");
		resultObject.setData(list);
		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/post/listHotBySchool")
	public ResultObject listHotBySchool(Post post, Integer pageNum) {
		ResultObject resultObject = new ResultObject(1);
		int[] page = this.page(pageNum);
		post.setStartPage(page[0]);
		post.setEndPage(page[1]);
		List<Post> list = postService.listHotBySchool(post);
		processPostList(list);
		resultObject.addMsg("success");
		resultObject.setData(list);
		return resultObject;
	}
	
	@ResponseBody
	@RequestMapping("/post/listByActivity")
	public ResultObject listByActivity(Post post, Integer pageNum) {
		ResultObject resultObject = new ResultObject(1);
		int[] page = this.page(pageNum);
		post.setStartPage(page[0]);
		post.setEndPage(page[1]);
		List<Post> list = postService.listByActivity(post);
		processPostList(list);
		resultObject.addMsg("success");
		resultObject.setData(list);
		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/post/listByUid")
	public ResultObject listByUid(Post post, Integer pageNum) {
		ResultObject resultObject = new ResultObject(1);
		this.setPageInfo(post, 5, pageNum);
		List<Post> list = postService.listByUid(post);
		processPostList(list);
		resultObject.addMsg("success");
		resultObject.setData(list);
		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/post/listByCommentAndUid")
	public ResultObject listByCommentAndUid(Post post, Integer pageNum) {
		ResultObject resultObject = new ResultObject(1);
		this.setPageInfo(post, 5, pageNum);
		List<Post> list = postService.listByCommentAndUid(post);
		processPostList(list);
		resultObject.addMsg("success");
		resultObject.setData(list);
		return resultObject;
	}
	
	@ResponseBody
	@RequestMapping("/post/listHotAll")
	public ResultObject listHotAll(Post post, Integer pageNum) {
		ResultObject resultObject = new ResultObject(1);
		int[] page = this.page(pageNum);
		post.setStartPage(page[0]);
		post.setEndPage(page[1]);
		List<Post> list = postService.listHotAll(post);
		processPostList(list);
		resultObject.addMsg("success");
		resultObject.setData(list);
		return resultObject;
	}
	
	@ResponseBody
	@RequestMapping("/post/reportPost")
	public ResultObject reportPost(AuditPost auditPost){
		ResultObject resultObject = new ResultObject(1);
		postService.reportPost(auditPost);
		resultObject.addMsg("success");
		return resultObject;
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
	
	
	private Float[] getLocationBySchollId(Integer schoolId){
		School school = schoolService.getById(schoolId);
		
		if (school != null){
			Float[] location = new Float[2];
			location[0] = school.getLatitude();
			location[1] = school.getLongitude();
			return location;
		}else{
			return null;
		}
	}
	
	
}

