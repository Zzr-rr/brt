package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.ReqConstant;
import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.mapper.UserStructMapper;
import com.zhuzirui.brt.model.dto.UserDTO;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.service.UserService;
import com.zhuzirui.brt.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * 鉴权控制器，控制用户登陆注册登出等操作
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserStructMapper userStructMapper;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录模块，目前使用邮箱 + 密码登录
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserDTO> login(@RequestBody UserDTO userDTO, HttpServletResponse response) {
        String email = userDTO.getEmail();
        User user = userService.getByEmail(email);
        if (user == null) {
            return Result.error(ReqConstant.ERROR_CODE, "user not found");
        }
        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPasswordHash())) {
            return Result.error(ReqConstant.ERROR_CODE, "wrong password");
        }
        String jwtToken = jwtUtil.generateToken(user.getUserId());
        setJwtCookie(response, jwtToken);

        return Result.success(userStructMapper.entityToDto(user));
    }

    @PostMapping("/register")
    public Result<UserDTO> register(@RequestBody UserDTO userDTO, HttpServletResponse response) throws Exception {
        User user = userService.getByEmail(userDTO.getEmail());
        if (user != null) {
            return Result.error(ReqConstant.ERROR_CODE, "email already exist");
        }
        user = userStructMapper.dtoToEntity(userDTO);
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        Long userId = userService.saveUser(user);
        String jwtToken = jwtUtil.generateToken(userId);
        setJwtCookie(response, jwtToken);
        return Result.success(userStructMapper.entityToDto(user));
    }

    @GetMapping("/verify")
    public Result<Boolean> verify() {
        return Result.success(true);
    }

    @PostMapping("/logout")
    public Result<Boolean> logout(HttpServletResponse response) {
        setJwtCookie(response, null);
        return Result.success(true);
    }

    private void setJwtCookie(HttpServletResponse response, String jwtToken) {
        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60);
        cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);
    }

}
