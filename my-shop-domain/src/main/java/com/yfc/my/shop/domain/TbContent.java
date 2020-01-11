package com.yfc.my.shop.domain;

import com.yfc.my.shop.commoms.persistence.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 内容管理
 * <p>title:TbContent</p>
 * <p>description:</p>
 *
 * @author yfc
 * @version 1.0.0
 * @date 2019/12/31 15:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TbContent extends BaseEntity {
    @Length(min=1,max=20,message = "标题长度介于1-20个字符之间")
    private String title;
    @Length(min=1,max=20,message = "子标题长度介于1-20个字符之间")
    private String subTitle;
    @Length(min=1,max=50,message = "标题描述长度介于1-50个字符之间")
    private String titleDesc;
    private String url;
    private String pic;
    private String pic2;
    @Length(min=1,message = "内容不能为空")
    private String content;
    @NotNull(message = "父级目录不能为空")
    private TbContentCategory tbContentCategory;

}
