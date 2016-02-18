package com.whoops.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoops.po.Msg;
import com.whoops.po.ResultObject;
import com.whoops.service.IMsgService;
import com.whoops.web.BaseController;

@Controller
@RequestMapping
public class MsgController extends BaseController {
	@Autowired
	private IMsgService msgService;

	@ResponseBody
	@RequestMapping("/msg/getMsgByUId")
	public ResultObject getCommentByPostId(Msg msg, Integer pageNum) {
		ResultObject resultObject = new ResultObject(1);
		int[] page = this.page(pageNum);
		msg.setStartPage(page[0]);
		msg.setEndPage(page[1]);
		List<Msg> list = msgService.getMsgByUid(msg);
		if (list != null) {
			for (Msg msg2 : list) {
				try {
					String msgContent = msg2.getMsg();
					if (StringUtils.hasLength(msgContent)) {
						String arr[] = msgContent.split("\"");
						String content = arr[1];
						msg2.setContent(content);
						msg2.setMsg(arr[0]);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		}
		resultObject.setData(list);

		return resultObject;
	}
}
