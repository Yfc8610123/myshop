package com.yfc.my.shop.web.admin.service;

import com.yfc.my.shop.commoms.persistence.BaseService;
import com.yfc.my.shop.domain.TbUser;

public interface TbUserService extends BaseService<TbUser> {
    /**
     * 用户登录
     * @param email
     * @param password
     * @return
     */
    TbUser login(String email,String password);
}
