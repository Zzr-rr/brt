package com.zhuzirui.brt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.zhuzirui.brt.model.entity.Question;
import com.zhuzirui.brt.dao.QuestionMapper;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 题目表，存储题库中的各题目和答案信息 服务实现类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    enum QuestionType{
        SINGLE_CHOICE,
        MULTIPLE_CHOICE,
        FILL_IN_THE_BLANK,
        SHORT_ANSWER
    }

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private Gson gson;

    @Override
    public void removeByBankId(Integer bankId) throws Exception {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bank_id", bankId);
        Question question = new Question();
        question.setIsDeleted(true);
        questionMapper.update(question, queryWrapper);
    }

    @Override
    public List<Question> listByBankId(Integer bankId) throws Exception {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bank_id", bankId);
        queryWrapper.eq("is_deleted", false);

        return questionMapper.selectList(queryWrapper);
    }

    @Override
    public boolean isCorrect(Question question, String userAnswer){
        try {
            if (question.getQuestionType().equals(QuestionType.SINGLE_CHOICE.toString())
                    || question.getQuestionType().equals(QuestionType.MULTIPLE_CHOICE.toString())) {
                // 将字符串转换为JSONArray
                JSONArray userAnswers = new JSONArray(userAnswer);
                JSONArray correctAnswers = new JSONArray(question.getCorrectAnswer());

                   // 遍历用户的答案
                for (int i = 0; i < userAnswers.length(); i++) {
                    JSONObject userAnswerItem = userAnswers.getJSONObject(i);
                    JSONObject correctAnswerItem = correctAnswers.getJSONObject(i);

                    // 比较isCorrect的值
                    if (userAnswerItem.getBoolean("isCorrect") != correctAnswerItem.getBoolean("isCorrect")) {
                        return false; // 如果有任何一个答案不正确，返回false
                    }
                }

                // 如果所有答案都正确，返回true
                return true;
            } else {
                return question.getCorrectAnswer().equals(userAnswer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
