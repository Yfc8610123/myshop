package com.yfc.my.shop.web.api.dao;

import com.yfc.my.shop.domain.TbUser;
import org.springframework.stereotype.Repository;
/**
 * 会员管理
 * <p>title:TbUserDao</p>
 * <p>description:</p>
 *
 *@author yfc
 *@version 1.0.0
 *@date 2020/1/7 21:32
*/
@Repository
public interface TbUserDao {
    /**
     * 用户登录
     * @param tbUser
     * @return
     */
    TbUser login(TbUser tbUser);
}
