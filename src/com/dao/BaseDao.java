package com.dao;

import java.util.List;

public interface BaseDao {
	public void save(Object value);
	public void delete(Object value);
	public void update(Object value);
	
	public <T> List<T> queryBySql(String hql);
	public <T> List<T> queryBySql(String hql, Object value);
	public <T> List<T> queryBySql(String hql, Object[] values);
	
	public <T> T queryTotalCount(String hql);
	public <T> T queryTotalCount(String hql, Object value);
	public <T> T queryTotalCount(String hql, Object[] values);
	
	public <T> T querySum(String hql);
	public <T> T querySum(String hql, Object value);
	public <T> T querySum(String hql, Object[] values);
	
	public <T> List<T> queryBySqlLimit(String hql, int startNum, int limit);
	public <T> List<T> queryBySqlLimit(String hql, Object value, int startNum, int limit);
	public <T> List<T> queryBySqlLimit(String hql, Object[] values, int startNum, int limit);
}
