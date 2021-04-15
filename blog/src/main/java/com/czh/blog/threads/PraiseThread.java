package com.czh.blog.threads;

import com.czh.blog.service.PraiseService;

import java.util.concurrent.CountDownLatch;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/13
 */
public class PraiseThread implements Runnable {

    private CountDownLatch countDownLatch;

    private PraiseService praiseService;

    private Long blogId;

    public PraiseThread() {
    }

    public PraiseThread(CountDownLatch countDownLatch, PraiseService praiseService, Long blogId) {
        this.countDownLatch = countDownLatch;
        this.praiseService = praiseService;
        this.blogId = blogId;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            praiseService.praiseBlog2(blogId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
