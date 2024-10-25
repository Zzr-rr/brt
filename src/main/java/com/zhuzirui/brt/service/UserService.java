package com.zhuzirui.brt.service;

import com.zhuzirui.brt.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
public interface UserService extends IService<User> {
    Long saveUser(User user) throws Exception;

    User getByEmail(String email);
}
