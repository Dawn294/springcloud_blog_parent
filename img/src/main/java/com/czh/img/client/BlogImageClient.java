package com.czh.img.client;

import com.czh.common.response.Result;
import com.czh.img.entity.BlogImage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/12
 */
@FeignClient("blog")
public interface BlogImageClient {

    @RequestMapping(value = "/blog/image/addBlogImage", method = RequestMethod.POST)
    Result addBlogImage(@RequestBody @Validated List<BlogImage> blogImageList);

}
