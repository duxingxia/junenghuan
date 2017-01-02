package com.makeonline.dao;

import java.util.List;
import java.util.Map;

import com.makeonline.pagination.PaginationInfo;
import com.makeonline.pagination.PaginationList;

/**
 * 
 * @Title:ICommonCRUDDao.java
 * 
 * @Description:定义而来通用的增删改查方法
 * 
 * @version V1.0
 */

public interface ICommonCRUDDao<Entity> {
	/**
	 * insert
	 * 
	 * @param entity
	 * @return 返回实体包含生成主键
	 */
	public Entity addEntity(Entity entity);
	
	/**
	 * delete
	 * 
	 * @param entity
	 * @return 是否删除成功
	 */
	public boolean deleteEntity(Entity entity);
	
	/**
	   * update
	   * 
	   * @param entity
	   * @return 是否更新成功
	   */
	  public boolean updateEntity(Entity entity);

	  /**
	   * delete list
	   * 
	   * @param entityList
	   * @return 返回删除成功实体列表
	   */
	  public List<Entity> deleteEntityList(List<Entity> entityList);
	  
	  /**
	   * update list
	   * 
	   * @param entityList
	   * @return 返回更新成功实体列表
	   */
	  public List<Entity> updateEntityList(List<Entity> entityList);

	  /**
	   * select by condition
	   * 
	   * @param cond
	   * @param paginationInfo
	   * @return 分页实体列表
	   */
	  public PaginationList<Entity> findEntityListByCond(Map<String, Object> cond,
			  PaginationInfo paginationInfo);

	  /**
	   * select by id
	   * 
	   * @param clientVersion
	   * @return 实体
	   */
	  public Entity findEntityById(Entity entity);

}
