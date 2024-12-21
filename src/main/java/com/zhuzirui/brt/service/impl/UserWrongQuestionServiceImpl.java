package com.zhuzirui.brt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuzirui.brt.mapper.UserWrongQuestionStructMapper;
import com.zhuzirui.brt.model.dto.UserWrongQuestionDTO;
import com.zhuzirui.brt.model.entity.UserWrongQuestion;
import com.zhuzirui.brt.dao.UserWrongQuestionMapper;
import com.zhuzirui.brt.service.UserWrongQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private UserWrongQuestionMapper userWrongQuestionMapper;

    @Override
    public void addUserWrongQuestion(UserWrongQuestion userWrongQuestion) throws Exception {
        QueryWrapper<UserWrongQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userWrongQuestion.getUserId());
        queryWrapper.eq("question_id", userWrongQuestion.getQuestionId());
        queryWrapper.last("LIMIT 1");
        UserWrongQuestion userWrongQuestionQuery = userWrongQuestionMapper.selectOne(queryWrapper);
        if(userWrongQuestionQuery == null){
            int insert = userWrongQuestionMapper.insert(userWrongQuestion);
            if (insert!= 1) {
                throw new Exception("Add wrong question failed");
            }
        }else{
            UserWrongQuestion userWrongQuestion1 = userWrongQuestionMapper.selectOne(queryWrapper);
            userWrongQuestion1.setReviewStatus("NOT_REVIEWED");
            userWrongQuestionMapper.updateById(userWrongQuestion1);
        }
    }

    @Override
    public List<UserWrongQuestion> listUserWrongQuestions(UserWrongQuestionDTO userWrongQuestionDTO) {
        QueryWrapper<UserWrongQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userWrongQuestionDTO.getUserId());
        queryWrapper.eq("is_deleted", false);
        return userWrongQuestionMapper.selectList(queryWrapper);
    }
}
