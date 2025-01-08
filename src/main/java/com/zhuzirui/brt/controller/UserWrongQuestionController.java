package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.dao.UserWrongQuestionMapper;
import com.zhuzirui.brt.model.dto.UserWrongQuestionDTO;
import com.zhuzirui.brt.model.dto.WrongInfoDTO;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.model.entity.UserWrongQuestion;
import com.zhuzirui.brt.service.UserService;
import com.zhuzirui.brt.service.UserWrongQuestionService;
import com.zhuzirui.brt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    static enum ReviewStatus {
        NOT_REVIEWED, REVIEWED, MASTERED
    }

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserWrongQuestionService userWrongQuestionService;

    @Autowired
    private UserWrongQuestionMapper userWrongQuestionMapper;

    @PostMapping("/create")
    public Result<Boolean> create() {
        return null;
    }
    @PostMapping("/delete")
    public Result<Boolean> delete() {
        return null;
    }
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody UserWrongQuestionDTO userWrongQuestionDTO, HttpServletRequest request) {
        Integer wrongId = userWrongQuestionDTO.getWrongId();
        String reviewStatus = userWrongQuestionDTO.getReviewStatus();

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

        UserWrongQuestion userWrongQuestion = userWrongQuestionService.getById(wrongId);
        if(userWrongQuestion == null) return Result.error(404, "User wrong question not found");
        if(userWrongQuestion.getUserId() != userId) return Result.error(403, "Forbidden");

        try {
             ReviewStatus reviewStatusTrans = ReviewStatus.valueOf(reviewStatus);
        } catch (IllegalArgumentException e) {
             return Result.error(401, "Invalid review status");
        }

        userWrongQuestion.setReviewStatus(reviewStatus);
        userWrongQuestionService.updateById(userWrongQuestion);
        return Result.success(true);
    }

//    @PostMapping("/list")
//    public Result<List<UserWrongQuestion>> list(@RequestBody UserWrongQuestionDTO userWrongQuestionDTO, HttpServletRequest request) {
//        //鉴权
//        String jwtToken = getJwtTokenFromCookie(request);// 调用方法从 Cookie 中获取 JWT Token
//        Integer userId;
//        // 检查 JWT Token 是否存在
//        if (jwtToken != null && !jwtToken.isEmpty()) {
//            // 使用 JwtUtil 提取用户 ID
//            userId = jwtUtil.extractUserId(jwtToken);
//            User user = userService.getUserByUserId(userId);
//            if (user == null) return Result.error(404, "User not found");
//        } else {
//            // 如果没有 JWT Token，返回错误信息
//            return Result.error(401, "Invalid JWT token");
//        }
//
//        userWrongQuestionDTO.setUserId(userId);
//        List<UserWrongQuestion> userWrongQuestions = userWrongQuestionService.listUserWrongQuestions(userWrongQuestionDTO);
//        return Result.success(userWrongQuestions);
//    }

    @GetMapping("/list/wronginfo")
    public Result<List<WrongInfoDTO>> listWrongInfo(HttpServletRequest request) {
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

        List<WrongInfoDTO> wrongInfoDTOList = null;
        try {
            wrongInfoDTOList = userWrongQuestionMapper.listWrongInfo(userId);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error(403, "Forbidden");
        }
        return Result.success(wrongInfoDTOList);
    }


}
