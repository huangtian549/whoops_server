/**
 * 
 */
package com.whoops.web.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.whoops.po.Comment;
import com.whoops.po.Post;
import com.whoops.po.ResultObject;
import com.whoops.service.ICommentService;
import com.whoops.service.IPostService;
import com.whoops.service.ImanagerUserService;
import com.whoops.util.CalendarUtil;
import com.whoops.web.BaseController;

/**
 * @author tushen
 * @date Nov 5, 2011
 */
@Controller
public class MyHomeController extends BaseController {
	
	@Autowired
	private ImanagerUserService managerUserService;
	
	@Autowired
	private IPostService postService;
	
	@Autowired
	private ICommentService commentService;

	@RequestMapping(value="/home/home",method=RequestMethod.GET)
	public String home(){
		
		return "home";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		
		return "login";
	}
	
	@RequestMapping(value="/home/index",method=RequestMethod.GET)
	public String index(){
		
		return "index";
	}
	
	@RequestMapping(value="/loginSubmit",method=RequestMethod.POST)
	public String loginSubmit(String username, String password, ModelMap modelMap, HttpSession session){
		
		boolean isCorrect = managerUserService.loginValidate(username, password);
		if (isCorrect) {
			session.setAttribute("login_passport", "loginSuccess");
			return "redirect:/home/home";
		}else {
			modelMap.put("msg", "loginFail");
			return "login";
		}
		
		
	}
	
	
	@RequestMapping(value="/home/addPost",method=RequestMethod.GET)
	public String addPost(){
		
		return "addPost";
	}
	
	
	@RequestMapping("/home/uploadPic")
	@ResponseBody
    public String uploadHeadPic(@RequestParam("file")MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
            String picUrl =  this.upload(file, this.path,request);
            return picUrl;
    }
	
	@RequestMapping("/home/webPost")
	public String webPost(Post po) {
//		po.setImage(this.picUrl);
        postService.addPost(po);
        return "redirect:/home/addPost";
	}
	
	@RequestMapping("/home/goPostUpdate")
	public String goPostUpdate(Post post) {
        return "updatePost";
	}
	
	@RequestMapping(value="/home/updatePost",method=RequestMethod.GET)
	public String updatePost(){
		
		return "updatePost";
	}
	
	@RequestMapping("/home/postSearch")
	public String postSearch(String content, ModelMap modelMap) {
		String key = content;
		content = "%" + content + "%";
		List<Post> list = postService.listByContent(content);
		modelMap.put("list", list);
		modelMap.put("key", key);
        return "updatePost";
	}
	
	
	@RequestMapping("/home/postDelete")
	public String postDelete(Post post, String key) {
		postService.deletePost(post);
        return "redirect:/home/postSearch?content=" + key;
    }
	
	
	@RequestMapping("/home/goUpdatePostDetail")
	public String goUpdatePostDetail(Post post, String key, ModelMap modelMap) {
		Post post2 = postService.getPostById(post.getId(), null);
		modelMap.put("post", post2);
		modelMap.put("key", key);
        return "updatePostDetail";
	}
	
	
	@RequestMapping("/home/updateSubmitPost")
	public String updateSubmit(Post post, String key, ModelMap modelMap) {
		postService.updatePost(post);
		return "redirect:/home/postSearch?content=" + key;
	}
	
	@RequestMapping("/home/goCommentSearch")
	public String goCommentSearch(Comment comment) {
        return "commentSearch";
	}
	
	@RequestMapping("/home/commentSearch")
	public String commentSearch(String content, ModelMap modelMap) {
		String key = content;
		content = "%" + content + "%";
		List<Comment> list = commentService.selectCommentByContent(content);
		modelMap.put("list", list);
		modelMap.put("key", key);
        return "commentSearch";
	}
	
	@RequestMapping("/home/commentDelete")
	public String commentDelete(Comment comment,String key, ModelMap modelMap) {
		commentService.deleteComment(comment);
        return "redirect:/home/commentSearch?content=" +  key;
	}
	
	
	
	/**
     * 
     * <p class="detail">
     * 功能：文件上传
     * </p>
     * @author wangsheng
     * @date 2014年9月25日 
     * @param files
     * @param destDir
     * @throws Exception
     */
    public String uploads(MultipartFile[] files, String destDir,HttpServletRequest request) throws Exception {
        String path = request.getContextPath();
        String picUrl = "";
        try {
            fileNames = new String[files.length];
            StringBuilder sb =  new StringBuilder();
            Date date = new Date();
    		String dateString = CalendarUtil.getDateString(date, CalendarUtil.SHORT_DATE_FORMAT_NO_DASH);
            for (MultipartFile file : files) {
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                int length = getAllowSuffix().indexOf(suffix);
                if(length == -1){
                    throw new Exception("请上传允许格式的文件");
                }
                if(file.getSize() > getAllowSize()){
                    throw new Exception("您上传的文件大小已经超出范围");
                }
                File destFile = new File(destDir + dateString + "/");
                if(!destFile.exists()){
                    destFile.mkdirs();
                }
                String fileNameNew = getFileNameNew()+"."+suffix;//
                File f = new File(destFile.getAbsoluteFile()+"\\"+fileNameNew);
                file.transferTo(f);
                f.createNewFile();
                sb.append("/data/").append(dateString).append("/").append(fileNameNew).append(",");
            }
            if (sb.length() > 0) {
				picUrl = sb.substring(0, sb.length() -1);
			}
        } catch (Exception e) {
            throw e;
        }
        return picUrl;
    }
     
    /**
     * 
     * <p class="detail">
     * 功能：文件上传
     * </p>
     * @author wangsheng
     * @date 2014年9月25日 
     * @param files
     * @param destDir
     * @return 
     * @throws Exception
     */
    public String upload(MultipartFile file, String destDir,HttpServletRequest request) throws Exception {
        String path = request.getContextPath();
        String picUrl = "";
        try {
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                int length = getAllowSuffix().indexOf(suffix);
                if(length == -1){
                    throw new Exception("请上传允许格式的文件");
                }
                if(file.getSize() > getAllowSize()){
                    throw new Exception("您上传的文件大小已经超出范围");
                }
                 
//                String realPath = request.getSession().getServletContext().getRealPath("/");
                Date date = new Date();
        		String dateString = CalendarUtil.getDateString(date, CalendarUtil.SHORT_DATE_FORMAT_NO_DASH);
                File destFile = new File(destDir + dateString + "/");
                if(!destFile.exists()){
                    destFile.mkdirs();
                }
                String fileNameNew = getFileNameNew()+"."+suffix;
                File f = new File(destFile.getAbsoluteFile()+"/"+fileNameNew);
                file.transferTo(f);
                f.createNewFile();
               picUrl = "/data/" + dateString + "/" + fileNameNew;
        } catch (Exception e) {
            throw e;
        }
        return picUrl;
    }
        
        /**
         * 
         * <p class="detail">
         * 功能：重新命名文件
         * </p>
         * @author wangsheng
         * @date 2014年9月25日 
         * @return
         */
        private String getFileNameNew(){
        	String uuid = UUID.randomUUID().toString();
			return uuid;
        }
        
        
        private String allowSuffix = "jpg,png,gif,jpeg";
        String path = "/home/data/";
        private long allowSize = 2000000L;//允许文件大小
        private String fileName;
        private String[] fileNames;

		public String getAllowSuffix() {
			return allowSuffix;
		}

		public void setAllowSuffix(String allowSuffix) {
			this.allowSuffix = allowSuffix;
		}

		public long getAllowSize() {
			return allowSize;
		}

		public void setAllowSize(long allowSize) {
			this.allowSize = allowSize;
		}
        
        
}
