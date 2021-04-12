package com.czh.email.service.impl;

import com.czh.email.entity.EmailEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/9
 */
@Service
public class EmailServiceImpl {

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public void sendEmail(EmailEntity emailEntity) {
        SimpleMailMessage message = new SimpleMailMessage();
        BeanUtils.copyProperties(emailEntity, message);
        message.setFrom(emailFrom);
        mailSender.send(message);
    }
}
