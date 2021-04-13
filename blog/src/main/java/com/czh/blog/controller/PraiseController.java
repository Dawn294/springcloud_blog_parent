package com.czh.blog.controller;

import com.czh.blog.service.PraiseService;
import com.czh.common.enums.StatusEnum;
import com.czh.common.response.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/13
 */
@RestController
@RequestMapping("blog/praise")
public class PraiseController {

    @Resource
    private PraiseService praiseService;

    @RequestMapping(value = "praiseBlog",method = RequestMethod.POST)
    public Result praise(Long blogId){
        Result result = new Result(StatusEnum.SUCCESS);
        if (!praiseService.praiseBlog(blogId)){
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }
}
