package com.yfc.my.shop.web.api.dao;

import com.yfc.my.shop.domain.TbContent;

import java.util.List;

public interface TbContentDao {
    /**
     * 根据id查找
     */
    List<TbContent> selectByCategoryId(TbContent tbContent);
}
