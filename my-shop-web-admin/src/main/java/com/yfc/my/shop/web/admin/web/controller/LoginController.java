package com.yfc.my.shop.web.admin.web.controller;

import com.yfc.my.shop.commoms.contant.ContantUtils;
import com.yfc.my.shop.domain.TbUser;
import com.yfc.my.shop.web.admin.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private TbUserService tbUserService;

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping(value = {"", "login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 处理登录逻辑
     *
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String email, String password, HttpServletRequest request, HttpServletResponse response, Model model) {
        TbUser tbUser = tbUserService.login(email, password);
        //登录失败
        if (tbUser == null) {
            model.addAttribute("message", "用户名或密码错误，请重新输入");
            return "login";
        }
        //登录成功
        else {
            request.getSession().setAttribute(ContantUtils.SESSION_USER, tbUser);
            return "redirect:/main";
        }
    }

    /**
     * 注销
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().invalidate();
        return "login";
    }
}
