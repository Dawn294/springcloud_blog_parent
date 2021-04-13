package com.czh.blog.controller;

import com.czh.blog.entity.BlogImage;
import com.czh.blog.service.BlogImageService;
import com.czh.common.enums.StatusEnum;
import com.czh.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/12
 */
@RestController
@RequestMapping("blog/image")
public class BlogImageController {

    Logger logger = LoggerFactory.getLogger(BlogImageController.class);

    @Resource
    private BlogImageService blogImageService;

    @RequestMapping(value = "addBlogImage",method = RequestMethod.POST)
    public Result addBlogImage(@RequestBody List<BlogImage> blogImageList){
        Result result = new Result(StatusEnum.SUCCESS);

        try {
            result.setData(blogImageService.addBlogImageBatch(blogImageList));
        } catch (Exception e) {
            logger.error("添加博客图片发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }
}
