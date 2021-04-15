package com.czh.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.czh.admin.entity.User;
import com.czh.common.response.Result;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/9
 */
public interface UserService extends IService<User> {

    boolean add(User user);

    boolean deleteById(Long userId);

    Map<String,Object> getUserById(Long userId);

    Map<String, Object> getUserById2(Long userId);

    Map<String, Object> getUserById4(Long userId);

    User login(String username,String password);
}
