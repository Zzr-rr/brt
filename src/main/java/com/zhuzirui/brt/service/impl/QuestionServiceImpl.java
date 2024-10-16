package com.zhuzirui.brt.service.impl;

import com.zhuzirui.brt.model.entity.Question;
import com.zhuzirui.brt.dao.QuestionMapper;
import com.zhuzirui.brt.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
