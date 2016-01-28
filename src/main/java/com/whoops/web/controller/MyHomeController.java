/**
 * 
 */
package com.whoops.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whoops.service.ImanagerUserService;
import com.whoops.web.BaseController;

/**
 * @author tushen
 * @date Nov 5, 2011
 */
@Controller
public class MyHomeController extends BaseController {
	
	@Autowired
	private ImanagerUserService managerUserService;

	@RequestMapping(value="/home/home",method=RequestMethod.GET)
	public String home(){
		
		return "home";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		
		return "login";
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
}
