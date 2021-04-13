package com.czh.admin.client.impl;

import com.czh.admin.client.EmailClient;
import com.czh.admin.entity.EmailEntity;
import com.czh.common.enums.StatusEnum;
import com.czh.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/13
 */
@Component
public class EmailClientFallBack implements EmailClient {

    Logger logger = LoggerFactory.getLogger(EmailClientFallBack.class);

    @Override
    public Result sendEmail(EmailEntity emailEntity) {
        logger.error("远程调用Email服务发生熔断");
        return new Result(StatusEnum.FAIL);
    }
}
