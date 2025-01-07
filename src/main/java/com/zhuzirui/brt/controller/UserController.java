package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.mapper.UserStructMapper;
import com.zhuzirui.brt.model.dto.UserDTO;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.service.UserService;
import com.zhuzirui.brt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static com.zhuzirui.brt.utils.JwtUtil.getJwtTokenFromCookie;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@RestController
@RequestMapping("/brt/user")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserStructMapper userStructMapper;
    @Autowired
    private JwtUtil jwtUtil;

    //需要管理员权限
    @PostMapping("/delete")
    public Result<Boolean> delete() {
        return null;
    }

    //修改用户信息
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        //鉴权
        String jwtToken = getJwtTokenFromCookie(request);// 调用方法从 Cookie 中获取 JWT Token
        Integer userId;
        // 检查 JWT Token 是否存在
        User user = null;
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            userId = jwtUtil.extractUserId(jwtToken);
            user = userService.getUserByUserId(userId);
            if (user == null) return Result.error(404, "User not found");
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "Invalid JWT token");
        }

        Long userIdLong = userId.longValue();
        if(!userIdLong.equals(userDTO.getUserId()))
            return Result.error(401, "Access denied");

        User user1 = userStructMapper.dtoToEntity(userDTO);
        user1.setPasswordHash(user.getPasswordHash());
        try {
            userService.updateById(user1);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error(401, "User update failed");
        }

        return Result.success(true);
    }

    //管理员权限，列出符合筛选条件的用户
    @PostMapping("/list")
    public Result<Boolean> list() {
        return null;
    }

    //返回用户信息
    @GetMapping("/info")
    public Result<User> info(HttpServletRequest request) {
        //鉴权
        String jwtToken = getJwtTokenFromCookie(request);// 调用方法从 Cookie 中获取 JWT Token
        Integer userId;
        // 检查 JWT Token 是否存在
        User user = null;
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            userId = jwtUtil.extractUserId(jwtToken);
            user = userService.getUserByUserId(userId);
            if (user == null) return Result.error(404, "User not found");
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "Invalid JWT token");
        }

        user.setPasswordHash("Not shown");
        return Result.success(user);
    }
}
