package com.czh.email.controller;

import com.czh.common.enums.StatusEnum;
import com.czh.common.response.Result;
import com.czh.common.utils.ValidatorUtil;
import com.czh.email.entity.EmailEntity;
import com.czh.email.service.impl.EmailServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/9
 */
@RestController
@RequestMapping("email/email")
public class EmailController {

    @Resource
    private EmailServiceImpl emailService;

    @RequestMapping(value = "sendEmail", method = RequestMethod.POST)
    public Result sendEmail(@RequestBody @Validated EmailEntity emailEntity, BindingResult bindingResult) {
        Result result = new Result(StatusEnum.SUCCESS);
        String res = ValidatorUtil.checkResult(bindingResult);
        if (!StringUtils.isEmpty(res)) {
            return new Result(StatusEnum.PARAM_EXCEPTION, res);
        }
        try {
            emailService.sendEmail(emailEntity);
        } catch (Exception e) {
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }
}
