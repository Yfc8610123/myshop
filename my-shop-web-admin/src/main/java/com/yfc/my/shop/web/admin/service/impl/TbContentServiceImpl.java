package com.yfc.my.shop.web.admin.service.impl;

import com.yfc.my.shop.commoms.dto.BaseResult;
import com.yfc.my.shop.commoms.validator.BeanValidator;
import com.yfc.my.shop.domain.TbContent;
import com.yfc.my.shop.web.admin.abstracts.AbstractBaseServiceImpl;
import com.yfc.my.shop.web.admin.dao.TbContentDao;
import com.yfc.my.shop.web.admin.service.TbContentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class TbContentServiceImpl extends AbstractBaseServiceImpl<TbContent,TbContentDao> implements TbContentService {

    @Override
    @Transactional
    public BaseResult save(TbContent entity) {
        String validator = BeanValidator.validator(entity);
        //未通过验证
        if(validator!=null){
            return BaseResult.fail(validator);
        }
        //通过验证
        else {
            entity.setUpdated(new Date());
            //新增
            if (entity.getId() == null) {
                entity.setCreated(new Date());
                dao.insert(entity);
            }
            //编辑用户
            else {
                update(entity);
            }
            return BaseResult.success("保存内容信息成功");
        }
    }


}
