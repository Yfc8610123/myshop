package com.yfc.my.shop.web.ui.controller;

import com.google.code.kaptcha.Constants;
import com.yfc.my.shop.commoms.dto.BaseResult;
import com.yfc.my.shop.commoms.utils.EmailSendUtils;
import com.yfc.my.shop.web.ui.api.UserApi;
import com.yfc.my.shop.web.ui.contant.SystemContant;
import com.yfc.my.shop.web.ui.dto.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 会员登录
 * <p>title:LoginController</p>
 * <p>description:</p>
 *
 *@author yfc
 *@version 1.0.0
 *@date 2020/1/7 22:50
*/
@Controller
public class LoginController {
    @Autowired
    private EmailSendUtils emailSendUtils;
    /**
     * 跳转到登录页
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(TbUser tbUser, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
        //检查验证码
        //验证失败
        if(!checkVerification(tbUser,request)){
            model.addAttribute("baseResult", BaseResult.fail("验证码输入错误，请重新输入！"));
            return "login";
        }
        TbUser user = UserApi.login(tbUser);
        //登录失败
        if (user == null) {
            model.addAttribute("baseResult", BaseResult.fail("用户名或密码错误，请重新登录！"));
            return "login";
        }
        //登录成功
        else {
            emailSendUtils.send("用户登录",String.format("用户【%s】 登录成功",user.getUsername()),"jaseeclark@gmail.com");
            request.getSession().setAttribute(SystemContant.SESSION_USER_KEY,user);
            return "redirect:/index";
        }
    }

    /**
     * 注销
     * @param request
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/index";
    }

    /**
     * 检查验证码
     * @param tbUser
     * @param request
     * @return
     */
    private boolean checkVerification(TbUser tbUser,HttpServletRequest request){
        String verification = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(StringUtils.equals(verification,tbUser.getRefirication())){
            return true;
        }
        return false;
    }
}
