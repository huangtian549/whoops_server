/**
 * 
 */
package com.whoops.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoops.po.ResultObject;
import com.whoops.po.User;
import com.whoops.service.IUserService;
import com.whoops.web.BaseController;

/**
 * 登陆方法
 * 
 * @author tushen
 * @date Nov 4, 2011
 */
@Controller
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;
	
	

	@RequestMapping(value = "/user/add")
	@ResponseBody
	public ResultObject add(User user) {
		ResultObject resultObject = new ResultObject(1);
		resultObject.addMsg("success");
		userService.addUser(user);
		resultObject.setData(user);
		return resultObject;
	}
}
