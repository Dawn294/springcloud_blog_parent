package com.czh.es.dao;

import com.czh.es.entity.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/15
 */
@Repository
public interface EsBlogDao extends ElasticsearchRepository<EsBlog, Long> {
}
