package com.zhuzirui.brt.service.impl;

import com.zhuzirui.brt.model.entity.UserQuestionProgress;
import com.zhuzirui.brt.dao.UserQuestionProgressMapper;
import com.zhuzirui.brt.service.UserQuestionProgressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
