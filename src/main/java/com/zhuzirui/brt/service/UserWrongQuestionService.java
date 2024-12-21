package com.zhuzirui.brt.service;

import com.zhuzirui.brt.model.dto.UserWrongQuestionDTO;
import com.zhuzirui.brt.model.entity.UserWrongQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 错题本表，记录用户的错题和复习状态 服务类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
public interface UserWrongQuestionService extends IService<UserWrongQuestion> {
    void addUserWrongQuestion(UserWrongQuestion userWrongQuestion) throws Exception;
    List<UserWrongQuestion> listUserWrongQuestions(UserWrongQuestionDTO userWrongQuestionDTO);
}
