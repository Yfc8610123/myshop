package com.yfc.my.shop.web.api.web.v1;

import com.yfc.my.shop.commoms.dto.BaseResult;
import com.yfc.my.shop.domain.TbContent;
import com.yfc.my.shop.web.api.service.TbContentService;
import com.yfc.my.shop.web.api.web.dto.TbContentDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "${api.v1.path}/contents")
public class TbContentController {

    @Autowired
    private TbContentService tbContentService;

    @ModelAttribute
    public TbContent getTbContent(Long id){
        TbContent tbContent = null;
        if(tbContent==null){
            tbContent = new TbContent();
        }
        return tbContent;
    }
    @RequestMapping(value = "{category_id}",method = RequestMethod.GET)
    public BaseResult findById(@PathVariable(value = "category_id") Long categoryId){
        List<TbContent> tbContents = tbContentService.selectByCategoryId(categoryId);
        List<TbContentDto> tbContentDtos = new ArrayList<>();
        if(tbContents!=null&&tbContents.size()>0){
            for (TbContent tbContent : tbContents) {
                TbContentDto tbContentDto =new TbContentDto();
                BeanUtils.copyProperties(tbContent,tbContentDto);
                tbContentDtos.add(tbContentDto);
            }
        }
        return BaseResult.success("成功",tbContentDtos);
    }

}
