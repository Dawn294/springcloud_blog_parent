package com.czh.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czh.blog.dao.BlogDao;
import com.czh.blog.entity.Blog;
import com.czh.blog.service.BlogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/9
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements BlogService {

    @Resource
    private BlogDao blogDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(Long userId) {
        blogDao.deleteByUserId(userId);
    }

    @Override
    public int getBlogByUserId(Long userId) {
        return blogDao.getBlogByUserId(userId);
    }
}
