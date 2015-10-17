package com.whoops.service;

import java.util.List;

import com.whoops.po.FavorSchool;

public interface IFavorSchoolService {

	void add(FavorSchool favorSchool);

	void delete(FavorSchool favorSchool);

	List<FavorSchool> listFavorSchoolByUid(Integer uid);
}
