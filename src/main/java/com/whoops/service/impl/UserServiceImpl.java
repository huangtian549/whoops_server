/**
 * 
 */
package com.whoops.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoops.dao.BaseDao;
import com.whoops.dao.UserMapper;
import com.whoops.po.User;
import com.whoops.service.IUserService;

/**
 * @author tushen
 * @date Nov 4, 2011
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private BaseDao baseDao;

	@Autowired
	private UserMapper userDao;
	static String addUser = "insert into user(username) values(?);";
	
	public int addUser(User user) {
		return userDao.insert(user);

	}


}
