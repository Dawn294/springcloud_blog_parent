package com.czh.admin.client;

import com.czh.admin.client.impl.BlogClientFallBack;
import com.czh.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/9
 */
@FeignClient(value = "blog",fallback = BlogClientFallBack.class)
public interface BlogClient {

    @RequestMapping(value = "/blog/blog/deleteByUserId", method = RequestMethod.POST)
    Result deleteByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/blog/blog/getBlogByUserId",method = RequestMethod.POST)
    Result getBlogByUserId(@RequestParam("userId") Long userId);

}
