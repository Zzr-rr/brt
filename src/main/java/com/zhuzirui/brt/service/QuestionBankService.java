package com.zhuzirui.brt.service;

import com.zhuzirui.brt.model.dto.QuestionBankDTO;
import com.zhuzirui.brt.model.entity.QuestionBank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 题库信息表，存储题库信息 服务类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
public interface QuestionBankService extends IService<QuestionBank> {
    Integer saveQuestionBank(QuestionBank questionBank) throws Exception;
    List<QuestionBank> listQuestionBanks(QuestionBankDTO questionBankDTO) throws Exception;
}
