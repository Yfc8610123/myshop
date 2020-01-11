package com.yfc.my.shop.commoms.persistence;

import com.yfc.my.shop.commoms.dto.BaseResult;
import com.yfc.my.shop.commoms.dto.PageInfo;

import java.util.List;

/**
 * 所有业务层逻辑层的基类
 * <p>title:BaseService</p>
 * <p>description:</p>
 *
 *@author yfc
 *@version 1.0.0
 *@date 2020/1/2 22:42
*/
public interface BaseService<T extends BaseEntity> {
    /**
     * 查询所有
     * @return
     */
    List<T> selectAll();

    /**
     * 保存信息
     * @param entity
     * @return
     */
    BaseResult save(T entity);

    /**
     * 删除信息
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查找信息
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 更新信息
     * @param entity
     */
    void update(T entity);
    /**
     * 批量删除
     * @param ids
     */
    void deleteMulti(String[] ids);

    /**
     * 分页查询
     * @param start 开始的记录数
     * @param length 每页的记录数
     * @return
     */
    PageInfo<T> page(int start, int length , int draw, T entity);

    /**
     * 查询总记录数
     * @return
     */
    int count(T entity);
}
