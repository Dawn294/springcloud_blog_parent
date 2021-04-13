package com.czh.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.czh.blog.entity.Praise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auth admin
 * @date
 * @Description
 */
@Mapper
public interface PraiseDao extends BaseMapper<Praise> {

    List<Praise> getPraiseByUserIdBlogId(@Param("userId") Long userId, @Param("blogId") Long blogId);

}
