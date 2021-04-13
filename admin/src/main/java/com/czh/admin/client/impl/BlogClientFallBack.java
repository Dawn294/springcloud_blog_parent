package com.czh.admin.client.impl;

import com.czh.admin.client.BlogClient;
import com.czh.common.enums.StatusEnum;
import com.czh.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/13
 */
@Component
public class BlogClientFallBack implements BlogClient {

    Logger logger = LoggerFactory.getLogger(BlogClientFallBack.class);

    @Override
    public Result deleteByUserId(Long userId) {
        logger.error("远程调用Blog服务发生熔断");
        return new Result(StatusEnum.FAIL);
    }

    @Override
    public Result getBlogByUserId(Long userId) {
        logger.error("远程调用Blog服务发生熔断");
        return new Result(StatusEnum.FAIL);
    }
}
