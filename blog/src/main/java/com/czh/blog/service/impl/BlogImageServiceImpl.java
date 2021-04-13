package com.czh.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czh.blog.dao.BlogImageDao;
import com.czh.blog.entity.BlogImage;
import com.czh.blog.service.BlogImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/12
 */
@Service
public class BlogImageServiceImpl extends ServiceImpl<BlogImageDao, BlogImage> implements BlogImageService {

    @Resource
    private BlogImageDao blogImageDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addBlogImageBatch(List<BlogImage> list) {
        this.saveBatch(list);

        StringBuffer blogImageIds = new StringBuffer();
        for (BlogImage blogImage : list) {
            blogImageIds.append(blogImage.getId() + ",");
        }
        return blogImageIds.substring(0, blogImageIds.length() - 1);
    }
}
