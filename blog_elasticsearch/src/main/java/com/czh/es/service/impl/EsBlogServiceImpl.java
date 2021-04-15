package com.czh.es.service.impl;

import com.czh.common.enums.EsDeleteEnum;
import com.czh.common.enums.EsTypeEnum;
import com.czh.common.utils.SnowflakeUtil;
import com.czh.es.config.HighlightResultMapper;
import com.czh.es.dao.EsBlogDao;
import com.czh.es.entity.EsBlog;
import com.czh.es.service.EsBlogService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/15
 */
@Service
public class EsBlogServiceImpl implements EsBlogService {

    @Resource
    private EsBlogDao esBlogDao;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<EsBlog> listBlog(String content, Integer page) {
        List<EsBlog> esBlogList = new ArrayList<>();


        // Spring提供的一个查询条件构建器，帮助构建json格式的请求体
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //创建Java查询 Api
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        //创建filter，使用filter将所有条件串联起来
        List<QueryBuilder> filter = builder.filter();

        if (StringUtils.isNotBlank(content)) {
            filter.add(QueryBuilders.termQuery("content", content));
        }

        nativeSearchQueryBuilder.withQuery(builder);
        //分页查询（SpringDataJpa是从0开始的）
        nativeSearchQueryBuilder.withPageable(PageRequest.of(page, 2));
        //排序
        nativeSearchQueryBuilder.withSort(new FieldSortBuilder("createTime").order(SortOrder.DESC));

        Page<EsBlog> pageResult = this.esBlogDao.search(nativeSearchQueryBuilder.build());
        for (EsBlog esBlog : pageResult) {
            esBlogList.add(esBlog);
        }

        return esBlogList;
    }

    @Override
    public void add(EsBlog esBlog) {
        esBlog.setId(new SnowflakeUtil().nextId());
        esBlog.setUserId(1L);
        esBlog.setCreateTime(new Date());
        esBlog.setType(EsTypeEnum.OWN.getCode());
        esBlog.setIsActive(EsTypeEnum.OWN.getCode());
        esBlog.setIsDelete(EsDeleteEnum.NOT_DELETE.getCode());
        esBlogDao.save(esBlog);
    }

    @Override
    public List<EsBlog> listBlog2(String content, Integer page) {
        List<EsBlog> esBlogList = new ArrayList<>();

        String preTags = "<span style=color:'red'>";
        String postTags = "</span>";
        HighlightBuilder.Field fieldContent = new HighlightBuilder.Field("content").preTags(preTags).postTags(postTags);
        HighlightBuilder.Field[] fields = new HighlightBuilder.Field[1];
        fields[0] = fieldContent;

        SearchQuery searchQuery = null;
        if (!StringUtils.isEmpty(content)) {
            searchQuery = new NativeSearchQueryBuilder()
                    .withPageable(PageRequest.of(page, 2))
                    .withQuery(QueryBuilders.termQuery("content", content))
                    //添加高亮字段
                    .withHighlightFields(fields)
                    .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                    .build();
        } else {
            searchQuery = new NativeSearchQueryBuilder()
                    .withPageable(PageRequest.of(page, 2))
                    .withQuery(QueryBuilders.matchAllQuery())
                    .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                    .build();
        }
        Page<EsBlog> pageResult = elasticsearchTemplate.queryForPage(searchQuery, EsBlog.class, new HighlightResultMapper());
        for (EsBlog esBlog : pageResult) {
            esBlogList.add(esBlog);
        }
        return esBlogList;
    }
}
