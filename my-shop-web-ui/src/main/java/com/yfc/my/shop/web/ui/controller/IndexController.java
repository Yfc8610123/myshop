package com.yfc.my.shop.web.ui.controller;

import com.yfc.my.shop.web.ui.api.ContentsApi;
import com.yfc.my.shop.web.ui.dto.TbContent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class IndexController {
    /**
     * 跳转首页
     * @param model
     * @return
     */
    @RequestMapping(value = {"","index"},method = RequestMethod.GET)
    public String index(Model model){
        //请求幻灯片
        requestContentsPPT(model);
        return "index";
    }

    /**
     * 请求幻灯片
     * @param model
     */
    private void requestContentsPPT(Model model){
        List<TbContent> contents = ContentsApi.ppt();
        model.addAttribute("ppt",contents);
    }
}
