package com.zhuzirui.brt.dao;

import com.zhuzirui.brt.model.dto.PostDTO;
import com.zhuzirui.brt.model.entity.CommunityInteraction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
      @Select("SELECT ci.interaction_id, ci.user_id, ci.content, ci.type, ci.target_id, ci.created_at, ci.updated_at, ci.is_deleted, ci.likes_count, " +
            "(SELECT COUNT(*) FROM community_interaction WHERE type = 'COMMENT' AND target_id = ci.interaction_id) AS commentCount " +
            "FROM community_interaction ci WHERE ci.type = 'POST'")
    public List<PostDTO> listPostInfo();
}
