package com.yfc.my.shop.web.api.web.v1;

import com.yfc.my.shop.commoms.dto.BaseResult;
import com.yfc.my.shop.domain.TbUser;
import com.yfc.my.shop.web.api.service.TbUserService;
import com.yfc.my.shop.web.api.web.dto.TbUserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员管理
 * <p>title:TbUserController</p>
 * <p>description:</p>
 *
 * @author yfc
 * @version 1.0.0
 * @date 2020/1/7 21:39
 */
@RestController
@RequestMapping(value = "${api.v1.path}/users")
public class TbUserController {

    @Autowired
    private TbUserService tbUserService;

    /**
     * 登录
     *
     * @param tbUser
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResult login(TbUser tbUser) {
        TbUser user = tbUserService.login(tbUser);
        if (user != null) {
            TbUserDTO dto=new TbUserDTO();
            BeanUtils.copyProperties(user,dto);
            return BaseResult.success("成功", dto);
        } else {
            return BaseResult.fail("登录失败");
        }
    }
}
