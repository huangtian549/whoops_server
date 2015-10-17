package com.whoops.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoops.po.ResultObject;
import com.whoops.po.School;
import com.whoops.service.ISchoolService;
import com.whoops.web.BaseController;

@Controller
@RequestMapping
public class SchoolController extends BaseController {

	@Autowired
	private ISchoolService schoolService;

	@ResponseBody
	@RequestMapping("/school/getAll")
	public ResultObject getAll() {
		ResultObject resultObject = new ResultObject(1);
		resultObject.addMsg("success");
		List<School> list = schoolService.getAllSchoolList();
		resultObject.setData(list);
		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/school/listSchoolByLocation")
	public ResultObject listSchoolByLocation(School school) {
		ResultObject resultObject = new ResultObject(1);
		resultObject.addMsg("success");
		List<School> list = schoolService.getSchoolByLocation(school);
		resultObject.setData(list);
		return resultObject;
	}
}
