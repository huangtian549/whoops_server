package com.whoops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoops.dao.FavorPostMapper;
import com.whoops.po.FavorPost;
import com.whoops.po.FavorPostExample;
import com.whoops.service.IFavorPostService;

@Service
public class FavorPostServiceImpl implements IFavorPostService {

	@Autowired
	private FavorPostMapper favorPostDao;
	
	@Override
	public void addFavorPost(FavorPost favorPost) {
		FavorPostExample example = new FavorPostExample();
		example.createCriteria().andUidEqualTo(favorPost.getUid())
		.andPostIdEqualTo(favorPost.getPostId());
		List<FavorPost> list = favorPostDao.selectByExample(example );
		if (list != null && list.size() > 0){
			for (FavorPost favorPost2 : list) {
				favorPostDao.deleteByPrimaryKey(favorPost2.getId());
			}
			return;
		}
		favorPostDao.insertSelective(favorPost);
		
	}

}
