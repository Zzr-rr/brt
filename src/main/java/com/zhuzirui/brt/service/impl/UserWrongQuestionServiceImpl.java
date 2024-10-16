package com.zhuzirui.brt.service.impl;

import com.zhuzirui.brt.model.entity.UserWrongQuestion;
import com.zhuzirui.brt.dao.UserWrongQuestionMapper;
import com.zhuzirui.brt.service.UserWrongQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 错题本表，记录用户的错题和复习状态 服务实现类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Service
public class UserWrongQuestionServiceImpl extends ServiceImpl<UserWrongQuestionMapper, UserWrongQuestion> implements UserWrongQuestionService {

}
