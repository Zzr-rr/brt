package com.zhuzirui.brt.dao;

import com.zhuzirui.brt.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 题目表，存储题库中的各题目和答案信息 Mapper 接口
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}
