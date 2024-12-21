package com.zhuzirui.brt.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.dao.QuestionMapper;
import com.zhuzirui.brt.mapper.QuestionBankStructMapper;
import com.zhuzirui.brt.mapper.QuestionStructMapper;
import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.dto.QuestionBankDTO;
import com.zhuzirui.brt.model.dto.QuestionDTO;
import com.zhuzirui.brt.model.entity.File;
import com.zhuzirui.brt.model.entity.Question;
import com.zhuzirui.brt.model.entity.QuestionBank;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.service.*;
import com.zhuzirui.brt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import kotlin.Pair;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.zhuzirui.brt.utils.JwtUtil.getJwtTokenFromCookie;

/**
 * <p>
 * 题库信息表，存储题库信息 前端控制器
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */




@RestController
@RequestMapping("/brt/questionBank")
public class QuestionBankController {

    @Autowired
    private QuestionBankService questionBankService;

    @Autowired
    private QuestionBankStructMapper questionBankStructMapper;

    @Autowired
    private QuestionExtractorService questionExtractorService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private FileService fileService;

    @Autowired
    private DocumentToTextProcessingService documentToTextProcessingService;

    @Autowired
    JwtUtil jwtUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    UserService userService;

    private static final Logger logger = Logger.getLogger(QuestionBankController.class.getName());
    @Autowired
    private QuestionStructMapper questionStructMapper;

    private static final Gson gson = new Gson();

    @Data
    @Getter
    @Setter
    public static class CreateBodyParser {
        List<Integer> fileIdList;
        private String title;
        private String description;
        private String keywords;
        private String coverUrl;

        private String text;

        QuestionBankDTO getQuestionBankDTO() {
            QuestionBankDTO questionBankDTO = new QuestionBankDTO();
            questionBankDTO.setTitle(title);
            questionBankDTO.setDescription(description);
            questionBankDTO.setKeywords(keywords);
            questionBankDTO.setCoverUrl(coverUrl);
            return questionBankDTO;
        }
    }


    //根据指定文件id列表生成题库
    @PostMapping("/create")
    public Result<Pair<QuestionBank, List<Question>>> create(@RequestBody CreateBodyParser body,
                                                             HttpServletRequest request) {

        List<Integer> fileIdList = body.getFileIdList();
        QuestionBankDTO questionBankDTO = body.getQuestionBankDTO();
        logger.info(questionBankDTO.getCoverUrl());

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

        //检查所需信息
        if (fileIdList == null || fileIdList.isEmpty()) return Result.error(500, "need at least one file");
        if (questionBankDTO == null) return Result.error(500, "need more information");
        if (questionBankDTO.getTitle() == null || questionBankDTO.getTitle().isEmpty())
            return Result.error(500, "need title");
        if (questionBankDTO.getDescription() == null || questionBankDTO.getDescription().isEmpty())
            return Result.error(500, "need description");
        if (questionBankDTO.getCoverUrl() == null || questionBankDTO.getCoverUrl().isEmpty())
            return Result.error(500, "need cover url");
        if (questionBankDTO.getKeywords() == null || questionBankDTO.getKeywords().equals("[]"))
            return Result.error(500, "need keywords");


        //从文件中抽取文本
        StringBuilder text = new StringBuilder();
        for (Integer fileId : fileIdList) {
            File file = fileService.getById(fileId);
            if (file == null) return Result.error(404, "File not found. fileId: " + fileId);
            if (file.getIsDeleted()) return Result.error(400, "File is deleted. fileId: " + fileId);
            if (!file.getIsPublic() && !userId.equals(file.getUserId()))
                return Result.error(401, "Access denied. fileId: " + fileId);

            //获取文件内容
            java.io.File fileContent = fileService.getFileContentByFileId(fileId);
            //转文本
            String fileText = documentToTextProcessingService.convertToText(fileContent);
            text.append(fileText).append("\n");
        }
        logger.info("Knowledge text: \n" + text);

        //保存题库信息
        questionBankDTO.setIsDeleted(false);
        questionBankDTO.setIsPublic(false);
        questionBankDTO.setUserId(userId);
        QuestionBank questionBank = questionBankStructMapper.dtoToEntity(questionBankDTO);
        Integer questionBankId;
        try {
            questionBankId = questionBankService.saveQuestionBank(questionBank);
        } catch (Exception e) {
            logger.warning("Save questionBank failed.\n" + e.getMessage());
            return Result.error(500, e.getMessage());
        }

        //从文件中获取题库
        logger.info("Generating question bank: " + questionBankId + " ...");
        List<QuestionDTO> questionDTOList = null;
        try {
            //利用大模型结构化输出从文本中抽取问题集
            questionDTOList = questionExtractorService.extractQuestions(text.toString());

            if (questionDTOList == null || questionDTOList.isEmpty())
                return Result.error(500, "Generate questions failed, please try again");
        } catch (Exception e) {
            logger.warning("Failed to generate question bank: \n" + e.getMessage());
            //回滚
            logger.info("No questions were saved. removing questionBank: " + questionBankId);
            questionBankService.removeById(questionBank);
            logger.info("Removed questionBank: " + questionBankId);
            return Result.error(500, e.getMessage());
        }

        //保存问题
        logger.info("Saving questions...");
        List<Question> questionSavedList = new ArrayList<>();
        for (QuestionDTO questionDTO : questionDTOList) {
            questionDTO.setBankId(questionBankId);
            questionBankDTO.setIsPublic(false);
            questionBankDTO.setIsDeleted(false);

            Question question = questionStructMapper.dtoToEntity(questionDTO);
            try {
                questionService.save(question);
                questionSavedList.add(question);
            } catch (Exception e) {
                continue;
            }
        }
        //检查是否生成问题
        logger.info("Saved: " + questionSavedList.size());
        if (questionSavedList.isEmpty()) {
            //回滚
            logger.info("No questions were saved. removing questionBank: " + questionBankId);
            questionBankService.removeById(questionBank);
            logger.info("Removed questionBank: " + questionBankId);
            return Result.error(500, "No questions saved");
        }

        return Result.success(new Pair<QuestionBank, List<Question>>(questionBank, questionSavedList));
    }

    //删除指定id题库及其包含的问题
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody QuestionBankDTO questionBankDTO, HttpServletRequest request) {
        if (questionBankDTO == null || questionBankDTO.getBankId() == null)
            return Result.error(500, "questionBankId is needed");
        Integer questionBankId = questionBankDTO.getBankId();

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

        QuestionBank questionBank = questionBankService.getById(questionBankId);
        if (questionBank == null) return Result.error(404, "QuestionBank not found");
        if (questionBank.getUserId() != userId) return Result.error(401, "Access denied");

        //先删除对应的问题
        try {
            questionService.removeByBankId(questionBankId);
        } catch (Exception e) {
            return Result.error(500, e.getMessage());
        }
        questionBankService.removeById(questionBank);
        return Result.success(true);
    }

    //修改创建者修改题库描述信息（标题，描述，关键字），不允许修改题目内容
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody QuestionBankDTO questionBankDTO, HttpServletRequest request) {
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

        QuestionBank questionBank = questionBankService.getById(questionBankDTO.getBankId());
        if (questionBank == null) return Result.error(404, "QuestionBank not found");
        if (questionBank.getUserId() != userId) return Result.error(401, "Access denied");

        if(questionBankDTO.getTitle() !=  null && !questionBankDTO.getTitle().trim().isEmpty())
            questionBank.setTitle(questionBankDTO.getTitle().trim());
        if(questionBankDTO.getDescription() !=  null && !questionBankDTO.getDescription().trim().isEmpty())
            questionBank.setDescription(questionBankDTO.getDescription().trim());
        if(questionBankDTO.getCoverUrl() !=  null && !questionBankDTO.getCoverUrl().trim().isEmpty())
            questionBank.setCoverUrl(questionBankDTO.getCoverUrl().trim());
        if(questionBankDTO.getKeywords() !=  null && !questionBankDTO.getKeywords().isEmpty())
            questionBank.setKeywords(questionBankDTO.getKeywords().trim());

        //写入数据库
        boolean res;
        try{
            res = questionBankService.updateById(questionBank);
        }catch(Exception e){
            return Result.error(500, e.getMessage());
        }

        return Result.success(true);
    }

        //根据指定条件筛选
    @PostMapping("/list/{flag}")
    public Result<List<QuestionBank>> list(@RequestBody QuestionBankDTO questionBankDTO, HttpServletRequest request, @PathVariable String flag) {
        //判断查询类型
        boolean isPublic;
        if (flag.equals("public")) isPublic = true;
        else if (flag.equals("personal")) isPublic = false;
        else return Result.error(401, "Invalid flag");

        //鉴权
        String jwtToken = getJwtTokenFromCookie(request);// 调用方法从 Cookie 中获取 JWT Token
        Integer userId;
        // 检查 JWT Token 是否存在
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            userId = jwtUtil.extractUserId(jwtToken);
            User user = userService.getUserByUserId(userId);
            if (user == null && !isPublic) return Result.error(404, "User not found");

            //个人查询
            if (user != null && !isPublic) questionBankDTO.setUserId(userId);

            //公共查询
            if (isPublic) questionBankDTO.setIsPublic(true);
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "Invalid JWT token");
        }

        List<QuestionBank> questionBanks;
        try {
            questionBanks = questionBankService.listQuestionBanks(questionBankDTO);
        } catch (Exception e) {
            return Result.error(500, e.getMessage());
        }
        return Result.success(questionBanks);
    }

}
