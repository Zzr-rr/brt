package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.model.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鉴权控制器，控制用户登陆注册登出等操作
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public Result<UserDTO> login(@RequestBody UserDTO userDTO) {
        return null;
    }

    @PostMapping("/register")
    public Result<UserDTO> register(@RequestBody UserDTO userDTO) {
        return null;
    }

}
