package com.yfc.my.shop.web.admin.web.controller;

import com.yfc.my.shop.commoms.dto.BaseResult;
import com.yfc.my.shop.domain.TbContentCategory;
import com.yfc.my.shop.web.admin.abstracts.AbstractBaseTreeController;
import com.yfc.my.shop.web.admin.service.TbContentCategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容分类管理
 * <p>title:ContentCategoryController</p>
 * <p>description:</p>
 *
 * @author yfc
 * @version 1.0.0
 * @date 2019/12/31 2:20
 */
@Controller
@RequestMapping("content/category")
public class ContentCategoryController extends AbstractBaseTreeController<TbContentCategory,TbContentCategoryService> {

    @Override
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        List<TbContentCategory> targetList = new ArrayList<>();
        List<TbContentCategory> sourceList = service.selectAll();
        //排序
        sortList(sourceList, targetList, 0L);
        model.addAttribute("tbContentCategories", targetList);
        return "content_category_list";
    }
    @ModelAttribute
    public TbContentCategory getTbContentCategory(Long id) {
        TbContentCategory tbContentCategory = null;
        //有用户id,则从数据库获取数据
        if (id != null) {
            tbContentCategory = service.getById(id);
        }
        //没有则，新给一个对象
        else {
            tbContentCategory = new TbContentCategory();
        }
        return tbContentCategory;
    }
    @Override
    @RequestMapping(value = "form",method = RequestMethod.GET)
    public String form(TbContentCategory tbContentCategory){
        return "content_category_form";
    }

    /**
     * 保存信息
     * @param tbContentCategory
     * @return
     */
    @Override
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public String save(TbContentCategory tbContentCategory, Model model, RedirectAttributes redirectAttributes){
        BaseResult baseResult = service.save(tbContentCategory);
        if(baseResult.getStatus()==200){
            redirectAttributes.addFlashAttribute("baseResult",baseResult);
            return "redirect:/content/category/list";
        }
        else {
            model.addAttribute("baseResult",baseResult);
            return form(tbContentCategory);
        }
    }
    /**
     * 树形结构
     *
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "tree/data", method = RequestMethod.POST)
    public List<TbContentCategory> treeData(Long id) {
        if (id == null) {
            id = 0L;
        }
        return service.selectByPid(id);
    }
    @ResponseBody
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public BaseResult delete(String ids) {
        BaseResult baseResult = null;
        //参数不为空
        if (StringUtils.isNotBlank(ids)) {
            Long id = Long.parseLong(ids);
            service.delete(id);
            baseResult = BaseResult.success("删除信息成功");
        }
        //参数为空
        else {
            baseResult = BaseResult.fail("删除信息失败");
        }
        return baseResult;
    }

}
