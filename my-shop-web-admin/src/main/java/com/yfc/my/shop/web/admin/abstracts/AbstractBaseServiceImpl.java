package com.yfc.my.shop.web.admin.abstracts;

import com.yfc.my.shop.commoms.dto.PageInfo;
import com.yfc.my.shop.commoms.persistence.BaseDao;
import com.yfc.my.shop.commoms.persistence.BaseEntity;
import com.yfc.my.shop.commoms.persistence.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Transactional(readOnly = true)
public abstract class AbstractBaseServiceImpl<T extends BaseEntity,D extends BaseDao<T>> implements BaseService<T> {
    @Autowired
    protected D dao;

    /**
     * 查询所有数据信息
     * @return
     */
    @Override
    public List<T> selectAll(){
       return dao.selectAll();
    }
    /**
     * 删除信息
     * @param id
     */
    @Override
    @Transactional
    public void delete(Long id){
        dao.delete(id);
    }
    /**
     * 根据id查找信息
     * @param id
     * @return
     */
    @Override
    public T getById(Long id){
        return dao.getById(id);
    }

    /**
     * 更新信息
     * @param entity
     */
    @Override
    @Transactional
    public void update(T entity){
        dao.update(entity);
    }
    /**
     * 批量删除
     * @param ids
     */
    @Override
    @Transactional
    public void deleteMulti(String[] ids){
        dao.deleteMulti(ids);
    }

    /**
     * 查询总记录数
     * @return
     */
    @Override
    public int count(T entity){
        return dao.count(entity);
    }

    /**
     * 分页查询
     * @param start
     * @param length
     * @param draw
     * @param entity
     * @return
     */
    @Override
    public PageInfo<T> page(int start, int length, int draw, T entity) {
        PageInfo<T> pageInfo= new PageInfo<>();
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        params.put("pageParams", entity);
        int count = count(entity);
        pageInfo.setDraw(draw);
        pageInfo.setRecordsTotal(count);
        pageInfo.setRecordsFiltered(count);
        pageInfo.setData(dao.page(params));
        return pageInfo;
    }
}
