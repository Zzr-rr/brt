package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.model.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@RestController
@RequestMapping("/user")
public class UserController {
    //弃用，用/auth/register注册
//    @PostMapping("/create")
//    public Result<Boolean> create() {
//        return null;
//    }

    //需要管理员权限
    @PostMapping("/delete")
    public Result<Boolean> delete() {
        return null;
    }

    //修改用户信息
    @PostMapping("/update")
    public Result<Boolean> update() {
        return null;
    }

    //管理员权限，列出符合筛选条件的用户
    @PostMapping("/list")
    public Result<Boolean> list() {
        return null;
    }
}
