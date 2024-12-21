package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.model.dto.UserWrongQuestionDTO;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.model.entity.UserWrongQuestion;
import com.zhuzirui.brt.service.UserService;
import com.zhuzirui.brt.service.UserWrongQuestionService;
import com.zhuzirui.brt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.zhuzirui.brt.utils.JwtUtil.getJwtTokenFromCookie;

/**
 * <p>
 * 错题本表，记录用户的错题和复习状态 前端控制器
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@RestController
@RequestMapping("/brt/userWrongQuestion")
public class UserWrongQuestionController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserWrongQuestionService userWrongQuestionService;

    @PostMapping("/create")
    public Result<Boolean> create() {
        return null;
    }
    @PostMapping("/delete")
    public Result<Boolean> delete() {
        return null;
    }
    @PostMapping("/update")
    public Result<Boolean> update() {
        return null;
    }
    @PostMapping("/list")
    public Result<List<UserWrongQuestion>> list(@RequestBody UserWrongQuestionDTO userWrongQuestionDTO, HttpServletRequest request) {
        //鉴权
        String jwtToken = getJwtTokenFromCookie(request);// 调用方法从 Cookie 中获取 JWT Token
        Integer userId;
        // 检查 JWT Token 是否存在
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            userId = jwtUtil.extractUserId(jwtToken);
            User user = userService.getUserByUserId(userId);
            if (user == null) return Result.error(404, "User not found");
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "Invalid JWT token");
        }

        userWrongQuestionDTO.setUserId(userId);
        List<UserWrongQuestion> userWrongQuestions = userWrongQuestionService.listUserWrongQuestions(userWrongQuestionDTO);
        return Result.success(userWrongQuestions);
    }
}
