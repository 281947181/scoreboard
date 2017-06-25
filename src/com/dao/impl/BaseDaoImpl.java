package com.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.BaseDao;

public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {

	@Override
	public void save(Object value) {
		this.getHibernateTemplate().save(value);
	}
	public void delete(Object value) {
		this.getHibernateTemplate().delete(value);
	}
	public void update(Object value) {
		this.getHibernateTemplate().update(value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> queryBySql(String hql, Object[] value) {
		return this.getHibernateTemplate().find(hql,value);
	}
	@SuppressWarnings("unchecked")
	public <T> List<T> queryBySql(String hql) {
		return this.getHibernateTemplate().find(hql);
	}
	@SuppressWarnings("unchecked")
	public <T> List<T> queryBySql(String hql, Object value) {
		return this.getHibernateTemplate().find(hql,value);
	}

	@SuppressWarnings("unchecked")
	public <T> T queryTotalCount(String hql) {
		return (T)(this.getHibernateTemplate().find(hql).get(0));
	}

	@SuppressWarnings("unchecked")
	public <T> T queryTotalCount(String hql, Object value) {
		return (T)(this.getHibernateTemplate().find(hql, value).get(0));
	}

	@SuppressWarnings("unchecked")
	public <T> T queryTotalCount(String hql, Object[] values) {
		return (T)(this.getHibernateTemplate().find(hql, values).get(0));
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> queryBySqlLimit(String hql, int startNum, int limitNum) {
		final String hqlString = new String(hql);
		final int start = startNum;
		final int limit = limitNum;
		return (List<T>)this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException,
							SQLException {
						Query query = session.createQuery(hqlString);
	                    query.setFirstResult(start);
	                    query.setMaxResults(limit);
	                    return query.list(); 
					}
				});
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryBySqlLimit(String hql, Object value, int startNum,
			int limitNum) {
		final String hqlString = new String(hql);
		final int start = startNum;
		final int limit = limitNum;
		final Object valueObject = value;
		return (List<T>)this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException,
							SQLException {
						Query query = session.createQuery(hqlString);
						query.setParameter(0, valueObject);
	                    query.setFirstResult(start);
	                    query.setMaxResults(limit);
	                    return query.list(); 
					}
				});
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryBySqlLimit(String hql, Object[] values,
			int startNum, int limitNum) {
		final String hqlString = new String(hql);
		final int start = startNum;
		final int limit = limitNum;
		final Object[] valueObjects = values;
		return (List<T>)this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException,
							SQLException {
						Query query = session.createQuery(hqlString);
						for (int i = 0; i < valueObjects.length; i++) {
							query.setParameter(i, valueObjects[i]);
						}
	                    query.setFirstResult(start);
	                    query.setMaxResults(limit);
	                    return query.list(); 
					}
				});
	}

	
	
	@SuppressWarnings("unchecked")
	public <T> T querySum(String hql) {
		return (T)(this.getHibernateTemplate().find(hql).get(0));
	}
	@SuppressWarnings("unchecked")
	public <T> T querySum(String hql, Object value) {
		return (T)(this.getHibernateTemplate().find(hql, value).get(0));
	}
	@SuppressWarnings("unchecked")
	public <T> T querySum(String hql, Object[] values) {
		return (T)(this.getHibernateTemplate().find(hql, values).get(0));
	}

}
