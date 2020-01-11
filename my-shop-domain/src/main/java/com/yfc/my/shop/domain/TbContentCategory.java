package com.yfc.my.shop.domain;

import com.yfc.my.shop.commoms.persistence.BaseTreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 分类管理
 * <p>title:TbContentCategory</p>
 * <p>description:</p>
 *
 *@author yfc
 *@version 1.0.0
 *@date 2019/12/31 2:14
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class TbContentCategory extends BaseTreeEntity {
    @Length(min = 1,max = 20,message = "分类名称必需在1到20位之间")
    private String name;
    private Integer status;
    @NotNull(message = "排序不能为空")
    private Integer sortOrder;
    private Boolean isParent;
    private TbContentCategory parent;

}
