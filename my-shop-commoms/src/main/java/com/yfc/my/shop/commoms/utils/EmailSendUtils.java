package com.yfc.my.shop.commoms.utils;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 邮件发送工具类
 * <p>title:EmailSendUtils</p>
 * <p>description:</p>
 *
 *@author yfc
 *@version 1.0.0
 *@date 2020/1/8 18:57
*/
public class EmailSendUtils {
    @Autowired
    private Email email;
    public void send(String subject,String msg,String... to) throws EmailException {
        email.setSubject(subject);
        email.setMsg(msg);
        //收件人
        email.addTo(to);
        email.send();
    }
}
