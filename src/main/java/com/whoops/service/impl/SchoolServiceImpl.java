package com.whoops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoops.dao.SchoolMapper;
import com.whoops.po.School;
import com.whoops.po.SchoolExample;
import com.whoops.service.ISchoolService;

@Service
public class SchoolServiceImpl implements ISchoolService {

	@Autowired
	private SchoolMapper schoolDao;

	@Override
	public List<School> getAllSchoolList() {
		SchoolExample example = new SchoolExample();
		return schoolDao.selectByExample(example);
	}

	@Override
	public List<School> getSchoolByLocation(School school) {
		return schoolDao.listSchoolByLocation(school);
	}

	@Override
	public School getById(Integer id) {
		return schoolDao.selectByPrimaryKey(id);
	}

}
