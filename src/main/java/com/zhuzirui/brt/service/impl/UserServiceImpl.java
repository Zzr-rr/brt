package com.zhuzirui.brt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.dao.UserMapper;
import com.zhuzirui.brt.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Long saveUser(User user) throws Exception {
        int insert = userMapper.insert(user);
        if (insert != 1) {
            throw new Exception("saved failed");
        }
        return user.getUserId();
    }

    @Override
    public User getUserByUserId(Integer userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User getByEmail(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return userMapper.selectOne(queryWrapper);
    }
}
