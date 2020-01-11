package com.yfc.my.shop.web.api.service;

import com.yfc.my.shop.domain.TbUser;

public interface TbUserService {
    /**
     * 用户登录
     * @param tbUser
     * @return
     */
    TbUser login(TbUser tbUser);
}
