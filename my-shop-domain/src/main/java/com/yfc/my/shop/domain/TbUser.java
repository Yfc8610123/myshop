package com.yfc.my.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yfc.my.shop.commoms.persistence.BaseEntity;
import com.yfc.my.shop.commoms.utils.RegexConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
public class TbUser extends BaseEntity {
    @Length(min = 6,max =20,message = "姓名长度应在6-20位之间")
    private String username;
    @JsonIgnore
    @Length(min = 6,max = 30,message = "密码长度应在6-30位之间")
    private String password;
    @Pattern(regexp = RegexConstants.REGEX_MOBILE_EXACT,message = "手机格式不正确")
    private String phone;
    @Pattern(regexp = RegexConstants.REGEX_EMAIL,message = "邮箱格式不正确")
    private String email;


}
