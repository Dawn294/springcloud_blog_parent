package com.czh.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.czh.blog.entity.Praise;

public interface PraiseService extends IService<Praise> {

    boolean praiseBlog(Long blogId);

    boolean praiseBlog2(Long blogId);

}
