package com.czh.es.controller;

import com.czh.common.enums.StatusEnum;
import com.czh.common.response.Result;
import com.czh.common.utils.ValidatorUtil;
import com.czh.es.entity.EsBlog;
import com.czh.es.service.EsBlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/15
 */
@RestController
@RequestMapping("es/blog")
public class EsBlogController {

    Logger logger = LoggerFactory.getLogger(EsBlogController.class);

    @Resource
    private EsBlogService esBlogService;

    @RequestMapping(value = "addBlog", method = RequestMethod.POST)
    public Result addBlog(@RequestBody @Validated EsBlog esBlog, BindingResult bindingResult) {
        Result result = new Result(StatusEnum.SUCCESS);
        String res = ValidatorUtil.checkResult(bindingResult);
        if (!StringUtils.isEmpty(res)) {
            return new Result(StatusEnum.PARAM_EXCEPTION, res);
        }
        try {
            esBlogService.add(esBlog);
        } catch (Exception e) {
            logger.error("添加博客发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }

    @RequestMapping(value = "listBlog", method = RequestMethod.GET)
    public Result listBlog(String content, Integer page) {
        page = page == null ? 0 : page;
        Result result = new Result(StatusEnum.SUCCESS);
        try {
            result.setData(esBlogService.listBlog2(content,page));
        } catch (Exception e) {
            logger.error("查询博客发生错误：{}", e);
            result = new Result(StatusEnum.FAIL);
        }
        return result;
    }
}
