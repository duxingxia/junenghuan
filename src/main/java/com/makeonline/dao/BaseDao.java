package com.makeonline.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.makeonline.pagination.PaginationInfo;
import com.makeonline.pagination.PaginationList;


/**
 * 
 * @Title:BaseDao.java
 * 
 * @Description:DAO基类，实现了通用的增删改查及分页查询方法，并注入了MyBatis的sqlSessionFactory
 * 
 * @author wenke.wang
 * @version V1.0
 */
public abstract class BaseDao<Entity> extends SqlSessionDaoSupport implements
		ICommonCRUDDao<Entity> {
	int a;
	private Class<Entity> entityClass = null;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		this.entityClass = null;
		Class<?> c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			this.entityClass = (Class<Entity>) parameterizedType[0];
		}
	}

	public Class<Entity> getEntityClass() {
		return entityClass;
	}

	@Autowired
	public void setMySqlSessionFactory(
			@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		this.setSqlSessionFactory(sqlSessionFactory);
	}

	@SuppressWarnings("unchecked")
	public PaginationList<Entity> selectPaginationList(String statement,
			Object parameter, PaginationInfo paginationInfo) {
		PaginationList<Entity> paginationList = new PaginationList<Entity>();

		if (parameter == null) {
			throw new RuntimeException("parameter can not be null");
		}
		if (parameter instanceof Map<?, ?>) {
			((Map) parameter).put("paginationInfo", paginationInfo);
		}
		List<Entity> result = this.getSqlSession().selectList(statement, parameter);

		paginationList.addAll(result);
		if (paginationInfo == null) {
			paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPage(1);
			paginationInfo.setRecordPerPage(result.size());
			paginationInfo.setTotalPage(1);
			paginationInfo.setTotalRecord(result.size());
		}
		paginationList.setPaginationInfo(paginationInfo);

		return paginationList;
	}

	@Override
	public boolean deleteEntity(Entity entity) {
		int result = this
				.getSqlSession()
				.delete(this.getEntityClass().getSimpleName() + ".deleteEntity",
						entity);
		return result == 1;
	}

	@Override
	public List<Entity> deleteEntityList(List<Entity> entityList) {
		List<Entity> result = new ArrayList<Entity>();
		for (Entity entity : entityList) {
			int operationResult = this.getSqlSession().delete(
					this.getEntityClass().getSimpleName() + ".deleteEntity",
					entity);
			if (operationResult == 1) {
				result.add(entity);
			}
		}
		return result;
	}

	public Entity addEntity(Entity entity) {
		this.getSqlSession().insert(
				this.getEntityClass().getSimpleName() + ".addEntity", entity);
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entity findEntityById(Entity entity) {
		return (Entity) this.getSqlSession().selectOne(
				this.getEntityClass().getSimpleName() + ".findEntityById",
				entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationList<Entity> findEntityListByCond(
			Map<String, Object> cond, PaginationInfo paginationInfo) {
		return this.selectPaginationList(this.getEntityClass().getSimpleName()
				+ ".findEntityListByCond", cond, paginationInfo);
	}

	public List<Entity> findListByCond(Map<String, Object> cond,
			PaginationInfo paginationInfo) {
		List<Entity> list = new ArrayList<Entity>();
		String statement = this.getEntityClass().getSimpleName()
				+ ".findEntityListByCond";
		if (cond == null) {
			throw new RuntimeException("parameter can not be null");
		}
		list = this.getSqlSession().selectList(statement, cond);
		return list;
	}

	@Override
	public boolean updateEntity(Entity entity) {
		int result = this
				.getSqlSession()
				.update(this.getEntityClass().getSimpleName() + ".updateEntity",
						entity);
		return result == 1;
	}

	@Override
	public List<Entity> updateEntityList(List<Entity> entityList) {
		List<Entity> result = new ArrayList<Entity>();
		for (Entity entity : entityList) {
			int operationResult = this.getSqlSession().update(
					this.getEntityClass().getSimpleName() + ".updateEntity",
					entity);
			if (operationResult == 1) {
				result.add(entity);
			}
		}
		return result;
	}

}
