package com.czh.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.czh.blog.entity.Blog;
import org.apache.ibatis.annotations.Param;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/9
 */
public interface BlogDao extends BaseMapper<Blog> {

    void deleteByUserId(@Param("userId") Long userId);

    int getBlogByUserId(@Param("userId") Long userId);

}
