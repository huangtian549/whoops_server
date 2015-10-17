/**
 * 
 */
package com.whoops.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.whoops.po.BasePo;
import com.whoops.util.Constant;

/**
 * @author tushen
 * @date Nov 5, 2011
 */
@Controller
public class BaseController {

	@InitBinder
	protected void ininBinder(WebDataBinder binder){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf,true));
	}

	protected int[] page(Integer pageNum, Integer pageSize) {
		if (pageNum == null) {
			pageNum = 1;
		}
		if (pageSize == null) {
			pageSize = Constant.pageSize;
		}
		int startNum = (pageNum - 1) * pageSize;
		int endNum = pageNum * pageSize;

		return new int[] { startNum, endNum };
	}

	protected int[] page(Integer pageNum) {
		return page(pageNum, null);
	}

	protected void setPageInfo(BasePo po, Integer pageSize, Integer pageNum) {
		if (pageNum == null) {
			pageNum = 1;
		}
		if (pageSize == null) {
			pageSize = Constant.pageSize;
		}
		int startNum = (pageNum - 1) * pageSize;
		int endNum = pageNum * pageSize;

		po.setStartPage(startNum);
		po.setEndPage(endNum);
	}
}
