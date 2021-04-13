package com.czh.admin.client;

import com.czh.admin.client.impl.EmailClientFallBack;
import com.czh.admin.entity.EmailEntity;
import com.czh.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/9
 */
@FeignClient(value = "email",fallback = EmailClientFallBack.class)
public interface EmailClient {

    @RequestMapping(value = "/email/email/sendEmail", method = RequestMethod.POST)
    Result sendEmail(@RequestBody EmailEntity emailEntity);

}
