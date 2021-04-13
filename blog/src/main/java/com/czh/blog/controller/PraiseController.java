package com.czh.blog.controller;

import com.czh.blog.service.PraiseService;
import com.czh.blog.threads.PraiseThread;
import com.czh.common.enums.StatusEnum;
import com.czh.common.response.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

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

    @RequestMapping(value = "praiseBlog2",method = RequestMethod.POST)
    public Result praise2(Long blogId){
        Result result = new Result(StatusEnum.SUCCESS);

        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 1000; i++) {
            PraiseThread praiseThread = new PraiseThread(countDownLatch,praiseService,blogId);
            Thread thread = new Thread(praiseThread);
            thread.start();
        }
        countDownLatch.countDown();
        return result;
    }
}
