package com.czh.img.controller;

import com.czh.common.response.Result;
import com.czh.img.service.ImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/12
 */
@RestController
@RequestMapping("/img/img")
public class ImgController {

    private Logger logger = LoggerFactory.getLogger(ImgController.class);

    @Resource
    private ImgService imgService;

    /*
     * @description 上传博客图片
     */
    @RequestMapping(value = "uploadBlogPic", method = RequestMethod.POST)
    public Result uploadBlogPic(MultipartHttpServletRequest request) {
        return imgService.uploadBlogPic(request);
    }
}