package com.zhuzirui.brt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuzirui.brt.mapper.UserQuestionProgressStructMapper;
import com.zhuzirui.brt.mapper.UserWrongQuestionStructMapper;
import com.zhuzirui.brt.model.dto.UserQuestionProgressDTO;
import com.zhuzirui.brt.model.dto.UserWrongQuestionDTO;
import com.zhuzirui.brt.model.entity.Question;
import com.zhuzirui.brt.model.entity.UserQuestionProgress;
import com.zhuzirui.brt.dao.UserQuestionProgressMapper;
import com.zhuzirui.brt.model.entity.UserWrongQuestion;
import com.zhuzirui.brt.service.QuestionService;
import com.zhuzirui.brt.service.UserQuestionProgressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuzirui.brt.service.UserWrongQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户做题记录表，记录用户做题进度和答题信息 服务实现类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Service
public class UserQuestionProgressServiceImpl extends ServiceImpl<UserQuestionProgressMapper, UserQuestionProgress> implements UserQuestionProgressService {
   @Autowired
   private QuestionService questionService;

   @Autowired
   private UserQuestionProgressStructMapper userQuestionProgressStructMapper;

   @Autowired
   private UserQuestionProgressMapper userQuestionProgressMapper;

   @Autowired
   private UserWrongQuestionService userWrongQuestionService;

   @Autowired
   private UserWrongQuestionStructMapper userWrongQuestionStructMapper;

    @Override
    public boolean addUserQuestionProgress(UserQuestionProgressDTO userQuestionProgressDTO) throws Exception {
        String userAnswer = userQuestionProgressDTO.getUserAnswer();
        Integer questionId = userQuestionProgressDTO.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new Exception("Question not found");
        }
        boolean isCorrect = questionService.isCorrect(question, userAnswer);
        userQuestionProgressDTO.setIsCorrect(isCorrect);
        UserQuestionProgress userQuestionProgress = userQuestionProgressStructMapper.dtoToEntity(userQuestionProgressDTO);

        QueryWrapper<UserQuestionProgress> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userQuestionProgress.getUserId());
        wrapper.eq("question_id", userQuestionProgress.getQuestionId());
        wrapper.last("LIMIT 1"); // 添加 LIMIT 1 到 SQL 语句中

        // 执行查询，检查是否存在记录
        UserQuestionProgress existingRecord = userQuestionProgressMapper.selectOne(wrapper);
        if(existingRecord == null){
            userQuestionProgressMapper.insert(userQuestionProgress);
        }else{
            userQuestionProgressMapper.update(userQuestionProgress,wrapper);
            UserQuestionProgress updateUserQuestionProgress = userQuestionProgressMapper.selectOne(wrapper);
            updateUserQuestionProgress.setAttemptNumber(updateUserQuestionProgress.getAttemptNumber()+1);
            userQuestionProgressMapper.updateById(updateUserQuestionProgress);
        }
        if(!userQuestionProgress.getIsCorrect()){
            // 记录错题
            UserWrongQuestionDTO userWrongQuestionDTO = new UserWrongQuestionDTO();
            userWrongQuestionDTO.setUserId(userQuestionProgress.getUserId());
            userWrongQuestionDTO.setQuestionId(userQuestionProgress.getQuestionId());
            UserWrongQuestion userWrongQuestion = userWrongQuestionStructMapper.dtoToEntity(userWrongQuestionDTO);
            userWrongQuestionService.addUserWrongQuestion(userWrongQuestion);
            return false;
        }

        return true;
    }

    @Override
    public List<UserQuestionProgress> listUserQuestionProgress(UserQuestionProgressDTO userQuestionProgressDTO) {
        QueryWrapper<UserQuestionProgress> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userQuestionProgressDTO.getUserId());
        wrapper.eq("is_deleted", false);
        List<UserQuestionProgress> userQuestionProgresses = userQuestionProgressMapper.selectList(wrapper);

        return userQuestionProgresses;
    }

    @Override
    public List<UserQuestionProgress> listByBankId(Integer bankId){
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("bank_id", bankId);
        List<Question> questions = questionService.list(wrapper);
        List<UserQuestionProgress> res = new ArrayList<>();
        for(Question question : questions){
            QueryWrapper<UserQuestionProgress> wp= new QueryWrapper<>();
            wp.eq("question_id", question.getQuestionId());
            UserQuestionProgress userQuestionProgress = userQuestionProgressMapper.selectOne(wp);
            res.add(userQuestionProgress);
        }
        return res;
    }
}
