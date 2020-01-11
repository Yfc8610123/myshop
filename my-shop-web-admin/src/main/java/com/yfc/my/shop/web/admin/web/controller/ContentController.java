package com.yfc.my.shop.web.admin.web.controller;

import com.yfc.my.shop.commoms.dto.BaseResult;
import com.yfc.my.shop.domain.TbContent;
import com.yfc.my.shop.web.admin.abstracts.AbstractBaseController;
import com.yfc.my.shop.web.admin.service.TbContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("content")
public class ContentController extends AbstractBaseController<TbContent,TbContentService> {
    /**
     * 跳转到内容列表页
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() {
        return "content_list";
    }

    @ModelAttribute
    public TbContent getTbContent(Long id) {
        TbContent tbContent = null;
        //有用户id,则从数据库获取数据
        if (id != null) {
            tbContent = service.getById(id);
        }
        //没有则，新给一个对象
        else {
            tbContent = new TbContent();
        }
        return tbContent;
    }

    /**
     * 跳转到表单页
     *
     * @return
     */
    @RequestMapping(value = "form", method = RequestMethod.GET)
    public String form() {
        return "content_form";
    }

    /**
     * 保存信息
     *
     * @param tbContent
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(TbContent tbContent, Model model, RedirectAttributes redirectAttributes) {
        BaseResult baseResult = service.save(tbContent);
        //保存成功
        if (baseResult.getStatus() == BaseResult.STATUS_SUCCESS) {
            redirectAttributes.addFlashAttribute("baseResult", baseResult);
            return "redirect:/content/list";
        }
        //保存失败，未通过验证
        else {
            model.addAttribute("baseResult", baseResult);
            return "content_form";
        }
    }

    /**
     * 批量删除内容
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseResult delete(String ids) {
        BaseResult baseResult = null;
        //参数不为空
        if (StringUtils.isNotBlank(ids)) {
            String[] idd = ids.split(",");
            service.deleteMulti(idd);
            baseResult = BaseResult.success("删除内容成功");
        }
        //参数为空
        else {
            baseResult = BaseResult.fail("删除内容失败");
        }
        return baseResult;
    }
    /**
     * 显示内容详情
     *
     * @param tbContent
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(TbContent tbContent) {
        return "content_detail";
    }
}
