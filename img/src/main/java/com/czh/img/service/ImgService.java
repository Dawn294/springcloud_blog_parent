package com.czh.img.service;

import com.czh.common.response.Result;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/12
 */
public interface ImgService {
    Result uploadBlogPic(MultipartHttpServletRequest request);
}
