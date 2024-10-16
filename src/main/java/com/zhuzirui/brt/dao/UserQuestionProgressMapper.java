package com.zhuzirui.brt.dao;

import com.zhuzirui.brt.model.entity.UserQuestionProgress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户做题记录表，记录用户做题进度和答题信息 Mapper 接口
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Mapper
public interface UserQuestionProgressMapper extends BaseMapper<UserQuestionProgress> {

}
