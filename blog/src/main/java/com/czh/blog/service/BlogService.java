package com.czh.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.czh.blog.entity.Blog;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/9
 */
public interface BlogService extends IService<Blog> {
    void deleteByUserId(Long userId);

    int getBlogByUserId(Long userId);
}
