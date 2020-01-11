package com.yfc.my.shop.web.ui.api;

import com.yfc.my.shop.commoms.utils.HttpClientUtils;
import com.yfc.my.shop.commoms.utils.MapperUtils;
import com.yfc.my.shop.web.ui.dto.TbUser;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员管理api
 * <p>title:UserApi</p>
 * <p>description:</p>
 *
 *@author yfc
 *@version 1.0.0
 *@date 2020/1/7 22:31
*/
public class UserApi {
    public static TbUser login(TbUser tbUser) throws Exception {
        List<BasicNameValuePair> list= new ArrayList<>();
        list.add(new BasicNameValuePair("username",tbUser.getUsername()));
        list.add(new BasicNameValuePair("password",tbUser.getPassword()));

        String json = HttpClientUtils.doPost(API.API_USER_LOGIN, list.toArray(new BasicNameValuePair[list.size()]));
        TbUser user = MapperUtils.json2pojoByTree(json,"data",TbUser.class);

        return user;
    }
}
