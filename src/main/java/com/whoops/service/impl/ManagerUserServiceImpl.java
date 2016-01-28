package com.whoops.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoops.dao.ManagerUserMapper;
import com.whoops.po.ManagerUserExample;
import com.whoops.service.ImanagerUserService;

@Service
public class ManagerUserServiceImpl implements ImanagerUserService {
	
	@Autowired
	private ManagerUserMapper managerUserDao;

	@Override
	public boolean loginValidate(String username, String password) {
		
		ManagerUserExample example = new ManagerUserExample();
		example.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password).andStatusEqualTo(1);
		
		int count = managerUserDao.countByExample(example);
		if (count > 0) {
			return true;
		}
		
		return false;
	}
	
	
}
