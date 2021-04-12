package com.czh.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.czh.admin.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/9
 */
public interface UserDao extends BaseMapper<User> {

    User getByUsername(@Param("username") String username);

}
