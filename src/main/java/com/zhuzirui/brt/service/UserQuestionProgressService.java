package com.zhuzirui.brt.service;

import com.zhuzirui.brt.model.dto.UserQuestionProgressDTO;
import com.zhuzirui.brt.model.entity.UserQuestionProgress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户做题记录表，记录用户做题进度和答题信息 服务类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
public interface UserQuestionProgressService extends IService<UserQuestionProgress> {
    boolean addUserQuestionProgress(UserQuestionProgressDTO userQuestionProgressDTO) throws Exception;
    List<UserQuestionProgress> listUserQuestionProgress(UserQuestionProgressDTO userQuestionProgressDTO) ;
    List<UserQuestionProgress> listByBankId(Integer bankId);
}
