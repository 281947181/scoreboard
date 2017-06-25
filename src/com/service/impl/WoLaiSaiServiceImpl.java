package com.service.impl;

import com.dao.BaseDao;
import com.service.WoLaiSaiService;

public class WoLaiSaiServiceImpl implements WoLaiSaiService {
	private BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
}
