package com.yfc.my.shop.commoms.persistence;

import java.util.List;
import java.util.Map;
/**
 * 所有dao层的基类
 * <p>title:BaseDao</p>
 * <p>description:</p>
 *
 *@author yfc
 *@version 1.0.0
 *@date 2020/1/2 22:42
*/
public interface BaseDao<T extends BaseEntity> {
    /**
     * 查询所有数据信息
     * @return
     */
    List<T> selectAll();

    /**
     * 添加实体
     * @param entity
     * @return
     */
    void insert(T entity);

    /**
     * a删除实体
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查找实体信息
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 更新实体信息
     * @param entity
     */
    void update(T entity);

    /**
     * 批量删除实体
     * @param ids
     */
    void deleteMulti(String[] ids);

    /**
     * 分页查询（包含了搜索查询）
     * @param  params 需要两个参数 ，start/记录开始的位置 length/每页记录数
     * @return
     */
    List<T> page(Map<String,Object> params);

    /**
     * 查询总记录数
     * @return
     */
    int count(T entity);
}
