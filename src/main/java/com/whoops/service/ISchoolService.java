package com.whoops.service;

import java.util.List;

import com.whoops.po.School;

public interface ISchoolService {

	List<School> getAllSchoolList();

	List<School> getSchoolByLocation(School school);
	
	School getById(Integer id);

}
