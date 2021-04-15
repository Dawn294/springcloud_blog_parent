package com.czh.es.service;

import com.czh.es.entity.EsBlog;

import java.util.List;

public interface EsBlogService {

    public List<EsBlog> listBlog(String content,Integer page);

    public void add(EsBlog esBlog);

    public List<EsBlog> listBlog2(String content,Integer page);

}
