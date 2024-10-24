package com.zhuzirui.brt.service.impl;

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
    public Long saveUser(User user) {
        int insert = userMapper.insert(user);
        if (insert > 0) {
            return user.getUserId();
        }
        return null;
    }
}
