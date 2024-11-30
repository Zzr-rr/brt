package com.zhuzirui.brt.service;

import com.zhuzirui.brt.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 题目表，存储题库中的各题目和答案信息 服务类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
public interface QuestionService extends IService<Question> {
    Integer saveQuestion(Question question) throws Exception;
}
