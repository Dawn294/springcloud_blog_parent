package com.czh.admin.controller;

import com.czh.admin.entity.User;
import com.czh.admin.service.UserService;
import com.czh.common.enums.StatusEnum;
import com.czh.common.response.Result;
import com.czh.common.utils.ValidatorUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
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
@RequestMapping("/admin/user")
@RefreshScope
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

//    @Value("${test.name}")
//    private String name;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Result add(@RequestBody @Validated User user, BindingResult bindingResult) {
//        System.out.println("配置文件中的test.name = " + name);
        Result result = new Result(StatusEnum.SUCCESS);
        String res = ValidatorUtil.checkResult(bindingResult);
        if (!StringUtils.isEmpty(res)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            if (!userService.add(user)) {
                result = new Result(StatusEnum.FAIL);
            }
        } catch (Exception e) {
            logger.error("添加用户发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }


    @RequestMapping(value = "deleteById", method = RequestMethod.POST)
    public Result deleteById(Long userId) {
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(userId)) {
            result = new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            if (!userService.deleteById(userId)) {
                result = new Result(StatusEnum.FAIL);
            }
        } catch (Exception e) {
            logger.error("删除用户发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }

    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public Result getById(Long userId) {
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(userId)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            result.setData(userService.getUserById(userId));
        } catch (Exception e) {
            logger.error("获取用户详情失败：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }

    @ApiOperation("获取用户详情2")
    @RequestMapping(value = "getById2", method = RequestMethod.POST)
    public Result getById2(Long userId){
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(userId)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            result.setData(userService.getUserById2(userId));
        } catch (Exception e) {
            logger.error("获取用户详情2发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }

        return result;
    }

    @ApiOperation("获取用户详情4")
    @RequestMapping(value = "getById4", method = RequestMethod.POST)
    public Result getById4(Long userId) {
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(userId)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            result.setData(userService.getUserById4(userId));
        } catch (Exception e) {
            logger.error("获取用户详情4发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }
}
