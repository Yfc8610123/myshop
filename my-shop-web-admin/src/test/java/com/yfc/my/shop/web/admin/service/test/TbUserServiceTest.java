package com.yfc.my.shop.web.admin.service.test;

import com.yfc.my.shop.domain.TbUser;
import com.yfc.my.shop.web.admin.service.TbUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml", "classpath:spring-context-durid.xml", "classpath:spring-context-mybatis.xml"})
public class TbUserServiceTest {
    @Autowired
    private TbUserService tbUserService;
    @Test
    public void testGetsAll(){
        List<TbUser> tbUsers = tbUserService.selectAll();
        for (TbUser tbUser : tbUsers) {
            System.out.println(tbUser.getUsername());
        }
    }
    @Test
    public void testInsert(){
        TbUser tbUser = new TbUser();
        tbUser.setEmail("yfc@qq.com");
        tbUser.setPhone("12312341234");
        tbUser.setUsername("yfc");
        tbUser.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        tbUserService.save(tbUser);
    }
    @Test
    public void testDelete(){
        tbUserService.delete(39L);
    }
    @Test
    public void testUpdate(){
        TbUser tbUser = tbUserService.getById(41L);
        tbUser.setUpdated(new Date());
        tbUser.setUsername("yfcyfc");
        tbUserService.update(tbUser);
    }

}
