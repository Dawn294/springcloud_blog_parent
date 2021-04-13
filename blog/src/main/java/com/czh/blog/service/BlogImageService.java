package com.czh.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.czh.blog.entity.BlogImage;

import java.util.List;

/**
 * @auth admin
 * @date
 * @Description
 */
public interface BlogImageService extends IService<BlogImage> {

    String addBlogImageBatch(List<BlogImage> list);
}
