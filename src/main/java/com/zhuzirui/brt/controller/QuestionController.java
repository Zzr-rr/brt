package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.model.dto.QuestionBankDTO;
import com.zhuzirui.brt.model.dto.QuestionDTO;
import com.zhuzirui.brt.model.entity.Question;
import com.zhuzirui.brt.model.entity.QuestionBank;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.service.QuestionBankService;
import com.zhuzirui.brt.service.QuestionService;
import com.zhuzirui.brt.service.UserService;
import com.zhuzirui.brt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zhuzirui.brt.utils.JwtUtil.getJwtTokenFromCookie;

/**
 * <p>
 * 题目表，存储题库中的各题目和答案信息 前端控制器
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@RestController
@RequestMapping("/brt/question")
public class QuestionController {

    @Autowired
    private QuestionBankService questionBankService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    private final static Logger loggger = org.apache.logging.log4j.LogManager.getLogger(QuestionController.class);


    //不允许，只能生成题库时生成题目
//    @PostMapping("/create")
//    public Result<Boolean> create() {
//        return null;
//    }

    //不允许，只能删除题库所属的题目
//    @PostMapping("/delete")
//    public Result<Boolean> delete() {
//        return null;
//    }

    //暂时不允许
//    @PostMapping("/update")
//    public Result<Boolean> update() {
//        return null;
//    }

    //根据bankId返回其所有题库
    @PostMapping("/list")
    public Result<List<Question>> list(@RequestBody QuestionBankDTO questionBankDTO, HttpServletRequest request) {
        QuestionBank questionBank = questionBankService.getById(questionBankDTO.getBankId());
        if (questionBank == null) return Result.error(404, "QuestionBank not found");

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
        if(!questionBank.getIsPublic()&&questionBank.getUserId()!=userId)
            return Result.error(401, "Access denied");

        List<Question> questionList;
        try{
            questionList = questionService.listByBankId(questionBankDTO.getBankId());
        }catch (Exception e){
            loggger.error(e.getMessage());
            return Result.error(500, e.getMessage());
        }

        return Result.success(questionList);
    }

    @GetMapping("/get")
    public Result<Question> get(@RequestParam Integer questionId, HttpServletRequest request) {
        Question question = questionService.getById(questionId);
        if(question == null) return Result.error(404, "Question not found");
        return Result.success(question);
    }
}
