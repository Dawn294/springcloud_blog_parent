package com.czh.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czh.blog.dao.PraiseDao;
import com.czh.blog.entity.Praise;
import com.czh.blog.service.PraiseService;
import com.czh.common.constants.RedisConstant;
import com.czh.common.utils.SnowflakeUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/13
 */
@Service
public class PraiseServiceImpl extends ServiceImpl<PraiseDao, Praise> implements PraiseService {

    @Resource
    private PraiseDao praiseDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean praiseBlog(Long blogId) {
        Long userId = 1L;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean praiseBlog2(Long blogId) {
        Long userId = 8L;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String key = RedisConstant.STRING_BLOCK + userId;
        String value = UUID.randomUUID().toString() + System.nanoTime();
        //setIfAbsent方法，就是当key不存在的时候，创建key和value，并且该方法可以设置键的过期时间,返回true
        Boolean redisB = valueOperations.setIfAbsent(key, value);
        try {
            if (redisB) {
                System.out.println("获取到锁");
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
            }else{
                System.out.println("没有获取到锁");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (value.equals(valueOperations.get(key))) {
                redisTemplate.delete(key);
            }
        }
        return true;
    }
}
