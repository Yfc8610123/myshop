package com.yfc.my.shop.web.admin.web.controller;

import com.yfc.my.shop.commoms.dto.BaseResult;
import com.yfc.my.shop.domain.TbUser;
import com.yfc.my.shop.web.admin.abstracts.AbstractBaseController;
import com.yfc.my.shop.web.admin.service.TbUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 用户管理
 * <p>title:UserController</p>
 * <p>description:</p>
 *
 * @author yfc
 * @version 1.0.0
 * @date 2019/12/27 14:02
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends AbstractBaseController<TbUser,TbUserService> {

    /**
     * 跳转到用户列表页
     *
     * @return
     */
    @Override
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() {
        return "user_list";
    }

    @ModelAttribute
    public TbUser getTbUser(Long id) {
        TbUser tbUser = null;
        //有用户id,则从数据库获取数据
        if (id != null) {
            tbUser = service.getById(id);
        }
        //没有则，新给一个对象
        else {
            tbUser = new TbUser();
        }
        return tbUser;
    }

    /**
     * 跳转到用户表单页
     *
     * @return
     */
    @Override
    @RequestMapping(value = "form", method = RequestMethod.GET)
    public String form() {
        return "user_form";
    }

    /**
     * 保存用户信息
     *
     * @param tbUser
     * @return
     */
    @Override
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(TbUser tbUser, Model model, RedirectAttributes redirectAttributes) {
        BaseResult baseResult = service.save(tbUser);
        //保存成功
        if (baseResult.getStatus() == BaseResult.STATUS_SUCCESS) {
            redirectAttributes.addFlashAttribute("baseResult", baseResult);
            return "redirect:/user/list";
        }
        //保存失败，未通过验证
        else {
            model.addAttribute("baseResult", baseResult);
            return "user_form";
        }
    }

    /**
     * 批量删除用户
     *
     * @param ids
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseResult delete(String ids) {
        BaseResult baseResult = null;
        //参数不为空
        if (StringUtils.isNotBlank(ids)) {
            String[] idd = ids.split(",");
            service.deleteMulti(idd);
            baseResult = BaseResult.success("删除用户成功");
        }
        //参数为空
        else {
            baseResult = BaseResult.fail("删除用户失败");
        }
        return baseResult;
    }
    /**
     * 显示用户详情
     *
     * @param tbUser
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(TbUser tbUser) {
        return "user_detail";
    }
}
