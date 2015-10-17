package com.whoops.service;

import java.util.List;

import com.whoops.po.Msg;

public interface IMsgService {
	
	List<Msg> getMsgByUid(Msg msg);

}
