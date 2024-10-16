package com.zhuzirui.brt.dao;

import com.zhuzirui.brt.model.entity.UserWrongQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 错题本表，记录用户的错题和复习状态 Mapper 接口
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Mapper
public interface UserWrongQuestionMapper extends BaseMapper<UserWrongQuestion> {

}
