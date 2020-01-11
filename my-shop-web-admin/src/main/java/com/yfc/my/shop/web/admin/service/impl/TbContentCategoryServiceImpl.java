package com.yfc.my.shop.web.admin.service.impl;

import com.yfc.my.shop.commoms.dto.BaseResult;
import com.yfc.my.shop.commoms.validator.BeanValidator;
import com.yfc.my.shop.domain.TbContentCategory;
import com.yfc.my.shop.web.admin.abstracts.AbstractBaseTreeServiceImpl;
import com.yfc.my.shop.web.admin.dao.TbContentCategoryDao;
import com.yfc.my.shop.web.admin.service.TbContentCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TbContentCategoryServiceImpl extends AbstractBaseTreeServiceImpl<TbContentCategory,TbContentCategoryDao> implements TbContentCategoryService {
    @Override
    @Transactional()
    public BaseResult save(TbContentCategory entity) {
        String validator = BeanValidator.validator(entity);
        if(validator!=null){
            return BaseResult.fail(validator);
        }
        else {
            TbContentCategory parent = entity.getParent();
            //如果没有选择父级节点，则默认设为根目录
            if(parent==null||parent.getId()==null){
                //0代表根目录
                parent.setId(0L);
                //根目录一定是父节点
                entity.setIsParent(true);
            }
            entity.setUpdated(new Date());
            //新增
            if(entity.getId()==null){
                entity.setCreated(new Date());
                //判断当前节点有没有父节点
                if(parent.getId()!=0L){
                    TbContentCategory currentCategoryParent = getById(parent.getId());
                    if(currentCategoryParent!=null){
                        //为父节点设置isParent为true
                        currentCategoryParent.setIsParent(true);
                        update(currentCategoryParent);
                    }
                }
                entity.setIsParent(false);
                dao.insert(entity);
            }
            //修改
            else {
                update(entity);
            }
            return BaseResult.success("保存信息成功");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        TbContentCategory contentCategory = dao.getById(id);
        //如果这个对象是父类
        if(contentCategory.getIsParent()){
            //查找所有这个类的子类
            List<TbContentCategory> tbContentCategories = selectByPid(id);
            //删除子类
            for (TbContentCategory tbContentCategory : tbContentCategories) {
                dao.delete(tbContentCategory.getId());
            }
        }
        //删除这个对象
        dao.delete(id);
    }
}
