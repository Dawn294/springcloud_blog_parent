package com.czh.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czh.blog.dao.PraiseDao;
import com.czh.blog.entity.Praise;
import com.czh.blog.service.PraiseService;
import com.czh.common.utils.SnowflakeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/13
 */
@Service
public class PraiseServiceImpl extends ServiceImpl<PraiseDao, Praise> implements PraiseService {

    @Resource
    private PraiseDao praiseDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean praiseBlog(Long blogId) {
        Long userId=1L;
        List<Praise> praiseList = praiseDao.getPraiseByUserIdBlogId(userId, blogId);
        if (praiseList != null && praiseList.size() > 0) {
            return false;
        }
        Praise praise = new Praise();
        praise.setId(new SnowflakeUtil().nextId());
        praise.setUserId(userId);
        praise.setBlogId(blogId);
        praise.setPraiseTime(new Date());
        boolean b = this.save(praise);
        return b;
    }
}
