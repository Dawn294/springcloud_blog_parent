package com.czh.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czh.admin.client.BlogClient;
import com.czh.admin.client.EmailClient;
import com.czh.admin.dao.UserDao;
import com.czh.admin.entity.EmailEntity;
import com.czh.admin.entity.User;
import com.czh.admin.service.UserService;
import com.czh.admin.utils.ShiroUtil;
import com.czh.common.constants.Constant;
import com.czh.common.constants.RedisConstant;
import com.czh.common.enums.StatusEnum;
import com.czh.common.response.Result;
import com.czh.common.utils.SnowflakeUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/9
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private EmailClient emailClient;

    @Resource
    private BlogClient blogClient;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(User user) {
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);

        newUser.setUserId(new SnowflakeUtil().nextId());

        String salt = RandomStringUtils.randomAlphanumeric(20);
        String password = StringUtils.isEmpty(user.getPassword()) ? Constant.defaultPassword : user.getPassword();
        newUser.setPassword(ShiroUtil.sha256(password, salt));
        newUser.setSalt(salt);

        newUser.setType(1);
        newUser.setStatus(1);
        newUser.setCreateTime(new Date());
        newUser.setImgUrl("");

        boolean b = this.save(newUser);

        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setTo(new String[]{user.getEmail()});
        emailEntity.setSubject("注册成功通知");
        String text = "恭喜【" + user.getName() + "】注册成功,您的密码是：" + password;
        emailEntity.setText(text);
        Result result = emailClient.sendEmail(emailEntity);
        if (!result.getCode().equals(StatusEnum.SUCCESS.getCode())) {
            throw new RuntimeException("邮件发送失败！");
        }

        return b;
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional(name = "seata_admin_blog", rollbackFor = Exception.class)
    public boolean deleteById(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setStatus(0);

        boolean b = this.updateById(user);

        Result result = blogClient.deleteByUserId(userId);
        if (!result.getCode().equals(StatusEnum.SUCCESS.getCode())) {
            throw new RuntimeException("删除用户博客失败！");
        }

        if (userId == 8) {
            throw new RuntimeException("分布式事务异常！");
        }

        return b;
    }

    @Override
    public Map<String, Object> getUserById(Long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", this.getById(userId));

        Result result = blogClient.getBlogByUserId(userId);
        if (!result.getCode().equals(StatusEnum.SUCCESS.getCode())) {
            throw new RuntimeException("查询用户博客数量失败！");
        }
        map.put("blogNum", result.getData());
        return map;
    }

    @Override
    public User login(String username, String password) {
        User loginUser = userDao.getByUsername(username);
        if (loginUser == null) {
            return null;
        }
        String salt = loginUser.getSalt();
        if (!(ShiroUtil.sha256(password, salt).equals(loginUser.getPassword()))) {
            return null;
        }
        return loginUser;
    }

    @Override
    public Map<String, Object> getUserById2(Long userId) {
        Map<String, Object> map = new HashMap<>();
        User user = null;
        String key = RedisConstant.STRING_USER + userId;
        ValueOperations valueOperations = redisTemplate.opsForValue();

        if (redisTemplate.hasKey(key)) {
            user = (User) valueOperations.get(key);
        }else{
            user = this.getById(userId);
            System.out.println("redis中不存在，在数据库中查询！");
            if (user != null) {
                valueOperations.set(key,user);
            }else{
                valueOperations.set(key,new User());
                redisTemplate.expire(key,60, TimeUnit.SECONDS);
            }
        }
        map.put("user",user);
        return map;
    }

    @Override
    public Map<String, Object> getUserById4(Long userId) {
        Map<String,Object> map = new HashMap<>();
        User user = null;
        String key = RedisConstant.STRING_USER+userId;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            user = (User) valueOperations.get(key);
        }else{
            String lockKey = RedisConstant.STRING_BLOCK_USER;
            String value = UUID.randomUUID().toString()+System.nanoTime();
            Boolean lock = valueOperations.setIfAbsent(lockKey, value);
            try {
                if (lock) {
                    user = this.getById(userId);
                    System.out.println("redis中不存在，在数据库中查询！");
                    if (user != null) {
                        valueOperations.set(key,user);
                    }else{
                        valueOperations.set(key,new User());
                        redisTemplate.expire(key,60, TimeUnit.SECONDS);
                    }
                }else{

                }
            } finally {
                if (value.equals(valueOperations.get(lockKey))) {
                    redisTemplate.delete(lockKey);
                }
            }
        }
        map.put("user",user);
        return map;
    }
}
