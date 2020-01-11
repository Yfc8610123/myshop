package com.yfc.my.shop.web.admin.abstracts;

import com.yfc.my.shop.commoms.persistence.BaseEntity;
import com.yfc.my.shop.commoms.persistence.BaseTreeDao;
import com.yfc.my.shop.commoms.persistence.BaseTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional(readOnly = true)
public abstract class AbstractBaseTreeServiceImpl<T extends BaseEntity,D extends BaseTreeDao<T>> implements BaseTreeService<T> {

    @Autowired
    protected D dao;

    /**
     * 根据父节点ID查询所有子节点
     * @param paretId
     * @return
     */
    @Override
    public List<T> selectByPid(Long paretId){
        return dao.selectByPid(paretId);
    };
    /**
     * 查询所有数据信息
     * @return
     */
    @Override
    public List<T> selectAll(){
        return dao.selectAll();
    }

    /**
     * 根据id查找实体信息
     * @param id
     * @return
     */
    @Override
    public T getById(Long id){
        return dao.getById(id);
    }

    /**
     * 更新实体信息
     * @param entity
     */
    @Override
    @Transactional()
    public void update(T entity){
        dao.update(entity);
    }


    /**
     * a删除实体
     * @param id
     */
    @Override
    @Transactional()
    public void delete(Long id){
        dao.delete(id);
    }

}
