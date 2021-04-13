package com.czh.admin.controller;

import com.czh.admin.entity.User;
import com.czh.admin.service.UserService;
import com.czh.common.enums.StatusEnum;
import com.czh.common.response.Result;
import com.czh.common.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/12
 */
@RestController
@RequestMapping("admin/login")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(String username, String password){
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            User loginUser = userService.login(username, password);
            if (loginUser == null) {
                return new Result(StatusEnum.LOGIN_EXCEPTION);
            }

            String token = JwtUtil.createJWT(loginUser.getUserId().toString(),loginUser.getEmail());

            Map map = new HashMap();
            map.put("token",token);

            result.setData(map);
        } catch (Exception e) {
            logger.error("登录错误：{}",e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }

}
