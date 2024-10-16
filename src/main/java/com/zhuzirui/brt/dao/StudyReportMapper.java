package com.zhuzirui.brt.dao;

import com.zhuzirui.brt.model.entity.StudyReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学习报告表，存储用户的学习统计报告 Mapper 接口
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Mapper
public interface StudyReportMapper extends BaseMapper<StudyReport> {

}
