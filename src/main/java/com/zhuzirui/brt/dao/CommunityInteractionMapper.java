package com.zhuzirui.brt.dao;

import com.zhuzirui.brt.model.entity.CommunityInteraction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 社区互动表，记录用户在社区的互动内容 Mapper 接口
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Mapper
public interface CommunityInteractionMapper extends BaseMapper<CommunityInteraction> {

}
