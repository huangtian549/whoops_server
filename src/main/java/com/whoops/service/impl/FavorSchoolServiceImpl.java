package com.whoops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoops.dao.FavorSchoolMapper;
import com.whoops.po.FavorSchool;
import com.whoops.po.FavorSchoolExample;
import com.whoops.service.IFavorSchoolService;

@Service
public class FavorSchoolServiceImpl implements IFavorSchoolService {

	@Autowired
	private FavorSchoolMapper favorSchoolDao;

	@Override
	public void add(FavorSchool favorSchool) {
		FavorSchoolExample example = new FavorSchoolExample();
		example.createCriteria().andUidEqualTo(favorSchool.getUid()).andSchoolIdEqualTo(favorSchool.getSchoolId());

		List<FavorSchool> list = favorSchoolDao.selectByExample(example);
		if (list != null && list.size() > 0) {
			return;
		}
		favorSchoolDao.insertSelective(favorSchool);

	}

	@Override
	public List<FavorSchool> listFavorSchoolByUid(Integer uid) {

		return favorSchoolDao.selectFavorSchoolByUid(uid);
	}

	@Override
	public void delete(FavorSchool favorSchool) {
		FavorSchoolExample example = new FavorSchoolExample();
		example.createCriteria().andUidEqualTo(favorSchool.getUid()).andSchoolIdEqualTo(favorSchool.getSchoolId());

		favorSchoolDao.deleteByExample(example);

	}

}
