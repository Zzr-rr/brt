package com.zhuzirui.brt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuzirui.brt.model.entity.Question;
import com.zhuzirui.brt.dao.QuestionMapper;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Autowired
    private QuestionMapper questionMapper;

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
}
