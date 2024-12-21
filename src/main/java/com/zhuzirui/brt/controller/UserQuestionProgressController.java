package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.model.dto.UserQuestionProgressDTO;
import com.zhuzirui.brt.model.entity.Question;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.model.entity.UserQuestionProgress;
import com.zhuzirui.brt.service.QuestionService;
import com.zhuzirui.brt.service.UserQuestionProgressService;
import com.zhuzirui.brt.service.UserService;
import com.zhuzirui.brt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.zhuzirui.brt.utils.JwtUtil.getJwtTokenFromCookie;

/**
 * <p>
 * 用户做题记录表，记录用户做题进度和答题信息 前端控制器
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@RestController
@RequestMapping("/brt/userQuestionProgress")
public class UserQuestionProgressController {
    @Autowired
    private UserQuestionProgressService userQuestionProgressService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;



    @PostMapping("/create")
    public Result<List<UserQuestionProgressDTO>> create(@RequestBody List<UserQuestionProgressDTO> userQuestionProgressDTOList, HttpServletRequest request) {
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

        List<UserQuestionProgressDTO> result = new ArrayList<>();
        for(UserQuestionProgressDTO userQuestionProgressDTO : userQuestionProgressDTOList){
            userQuestionProgressDTO.setUserId(userId);
            try{
                boolean isCorrect = userQuestionProgressService.addUserQuestionProgress(userQuestionProgressDTO);
                userQuestionProgressDTO.setIsCorrect(isCorrect);
                result.add(userQuestionProgressDTO);
            }catch (Exception e){
                return Result.error(500,"add userQuestionProgress failed:"+e.getMessage());
            }
        }

        return Result.success(result);
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
    public Result<List<UserQuestionProgress>> list(@RequestBody UserQuestionProgressDTO userQuestionProgressDTO, HttpServletRequest request) {
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
        userQuestionProgressDTO.setUserId(userId);
        List<UserQuestionProgress> userQuestionProgressList = userQuestionProgressService.listUserQuestionProgress(userQuestionProgressDTO);

        return Result.success(userQuestionProgressList);
    }
}
