package com.yfc.my.shop.web.admin.dao;

import com.yfc.my.shop.commoms.persistence.BaseDao;
import com.yfc.my.shop.domain.TbUser;
import org.springframework.stereotype.Repository;

@Repository
public interface TbUserDao extends BaseDao<TbUser> {
    /**
     * 根据邮箱查询用户信息
     * @param email
     * @return
     */
    TbUser getByEmail(String email);

}
