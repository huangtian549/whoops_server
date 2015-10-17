package com.whoops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.whoops.dao.MsgMapper;
import com.whoops.po.Msg;
import com.whoops.service.IMsgService;

@Service
public class MsgServiceImpl implements IMsgService {

	@Autowired
	private MsgMapper msgDao;
	
	@Override
	public List<Msg> getMsgByUid(Msg msg) {
		
		List<Msg> list =  msgDao.selectByUid(msg);
		
		if (list != null) {
			for (Msg msg2 : list) {
				if (StringUtils.hasLength(msg2.getImage())) {
					String[] image = msg2.getImage().split(",");
					msg2.setImage(image[0]);
				}
			}
		}
		return list;
	}

}
