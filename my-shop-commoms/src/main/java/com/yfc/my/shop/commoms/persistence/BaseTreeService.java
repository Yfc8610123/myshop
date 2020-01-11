package com.yfc.my.shop.commoms.persistence;

import com.yfc.my.shop.commoms.dto.BaseResult;

import java.util.List;

public interface BaseTreeService<T extends BaseEntity> {
    /**
     * 根据父节点ID查询所有子节点
     * @param paretId
     * @return
     */
    List<T> selectByPid(Long paretId);
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
    BaseResult save(T entity);

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
}
