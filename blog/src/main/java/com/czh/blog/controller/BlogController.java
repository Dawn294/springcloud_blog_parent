package com.czh.blog.controller;

import com.czh.blog.entity.Blog;
import com.czh.blog.service.BlogService;
import com.czh.common.enums.StatusEnum;
import com.czh.common.response.Result;
import com.czh.common.utils.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/9
 */
@RestController
@RequestMapping("blog/blog")
@RefreshScope
public class BlogController {

    Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Resource
    private BlogService blogService;

    @RequestMapping(value = "deleteByUserId", method = RequestMethod.POST)
    public Result deleteByUserId(Long userId) {
        Result result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(userId)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            blogService.deleteByUserId(userId);
        } catch (Exception e) {
            logger.error("删除用户博客发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }

    @RequestMapping(value = "getBlogByUserId",method = RequestMethod.POST)
    public Result getBlogByUserId(Long userId){
        Result  result = new Result(StatusEnum.SUCCESS);
        if (StringUtils.isEmpty(userId)) {
            return new Result(StatusEnum.PARAM_EXCEPTION);
        }
        try {
            result.setData(blogService.getBlogByUserId(userId));
        } catch (Exception e) {
            logger.error("查询用户博客数量错误：{}",e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }

    @RequestMapping(value = "addBlog",method = RequestMethod.POST)
    public Result addBlog(@RequestBody @Validated Blog blog, BindingResult bindingResult){
        Result result = new Result(StatusEnum.SUCCESS);
        String res = ValidatorUtil.checkResult(bindingResult);
        if (!StringUtils.isEmpty(res)){
            return new Result(StatusEnum.PARAM_EXCEPTION,res);
        }

        return result;
    }

}
