package com.zhuzirui.brt.service.impl;

import com.zhuzirui.brt.model.entity.QuestionBank;
import com.zhuzirui.brt.dao.QuestionBankMapper;
import com.zhuzirui.brt.service.QuestionBankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题库信息表，存储题库信息 服务实现类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements QuestionBankService {
    @Autowired
    private QuestionBankMapper questionBankMapper;

    @Override
    public Integer saveQuestionBank(QuestionBank questionBank) throws Exception {
        int insert = questionBankMapper.insert(questionBank);
        if (insert < 1) {
            throw new Exception("saved failed");
        }
        return questionBank.getBankId();
    }
}
