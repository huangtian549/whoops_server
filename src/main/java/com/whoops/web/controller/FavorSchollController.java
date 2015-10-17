package com.whoops.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoops.po.FavorSchool;
import com.whoops.po.ResultObject;
import com.whoops.service.IFavorSchoolService;
import com.whoops.web.BaseController;

@Controller
@RequestMapping
public class FavorSchollController extends BaseController {

	@Autowired
	private IFavorSchoolService favorSchoolService;

	@ResponseBody
	@RequestMapping("/favorSchool/add")
	public ResultObject add(FavorSchool favorSchool) {
		ResultObject resultObject = new ResultObject(1);
		favorSchoolService.add(favorSchool);
		resultObject.setData(favorSchool);
		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/favorSchool/delete")
	public ResultObject delete(FavorSchool favorSchool) {
		ResultObject resultObject = new ResultObject(1);
		favorSchoolService.delete(favorSchool);
		resultObject.setData(favorSchool);
		return resultObject;
	}

	@ResponseBody
	@RequestMapping("/favorSchool/listByUid")
	public ResultObject listByUid(FavorSchool favorSchool) {
		ResultObject resultObject = new ResultObject(1);
		List<FavorSchool> list = favorSchoolService.listFavorSchoolByUid(favorSchool.getUid());
		resultObject.setData(list);
		return resultObject;
	}
}
