package com.yfc.my.shop.web.admin.service.impl;

import com.yfc.my.shop.commoms.dto.BaseResult;
import com.yfc.my.shop.commoms.validator.BeanValidator;
import com.yfc.my.shop.domain.TbUser;
import com.yfc.my.shop.web.admin.abstracts.AbstractBaseServiceImpl;
import com.yfc.my.shop.web.admin.dao.TbUserDao;
import com.yfc.my.shop.web.admin.service.TbUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class TbUserServiceImpl extends AbstractBaseServiceImpl<TbUser,TbUserDao> implements TbUserService {
    private static final Logger logger = LoggerFactory.getLogger(TbUserServiceImpl.class);

    /**
     * 添加表用户
     *
     * @param tbUser
     * @return
     */
    @Override
    @Transactional
    public BaseResult save(TbUser tbUser) {
        String validator = BeanValidator.validator(tbUser);
        //验证不通过
        if(validator!=null){
            return BaseResult.fail(validator);
        }
        //验证通过
        else {
            tbUser.setUpdated(new Date());
            //新增用户
            if (tbUser.getId() == null) {
                tbUser.setCreated(new Date());
                //密码加密
                tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
               dao.insert(tbUser);
            }
            //编辑用户
            else {
                //密码加密
                tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
                update(tbUser);
            }
            return BaseResult.success("保存用户信息成功");
        }
    }

    @Override
    public TbUser login(String email, String password) {
        TbUser tbUser = dao.getByEmail(email);
        //用户存在
        if (tbUser != null) {
            //用户密码加密
            String md5Passeord = DigestUtils.md5DigestAsHex(password.getBytes());
            //与数据库密码匹配成功
            if (md5Passeord.equals(tbUser.getPassword())) {
                return tbUser;
            }
        }
        return null;
    }
}
