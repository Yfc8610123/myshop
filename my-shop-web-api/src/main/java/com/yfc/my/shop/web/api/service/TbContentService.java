package com.yfc.my.shop.web.api.service;

import com.yfc.my.shop.domain.TbContent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbContentService {
    /**
     * 根据categoryId查找内容列表
     */
    List<TbContent> selectByCategoryId(Long categoryId);
}
