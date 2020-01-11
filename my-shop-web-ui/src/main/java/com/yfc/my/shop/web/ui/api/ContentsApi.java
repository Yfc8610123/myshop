package com.yfc.my.shop.web.ui.api;

import com.yfc.my.shop.commoms.utils.HttpClientUtils;
import com.yfc.my.shop.commoms.utils.MapperUtils;
import com.yfc.my.shop.web.ui.dto.TbContent;

import java.util.List;

/**
 * 内容管理接口
 * <p>title:ContentsApi</p>
 * <p>description:</p>
 *
 *@author yfc
 *@version 1.0.0
 *@date 2020/1/7 18:27
*/
public class ContentsApi {
    public static List<TbContent> ppt(){
        String result = HttpClientUtils.doGet(API.API_CONTENT+89);
        List<TbContent> list=null;
        try {
          list = MapperUtils.json2listByTree(result,"data",TbContent.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
