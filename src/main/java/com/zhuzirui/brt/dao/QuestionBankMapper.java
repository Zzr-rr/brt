package com.zhuzirui.brt.dao;

import com.zhuzirui.brt.model.entity.QuestionBank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 题库信息表，存储题库信息 Mapper 接口
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Mapper
public interface QuestionBankMapper extends BaseMapper<QuestionBank> {

}
