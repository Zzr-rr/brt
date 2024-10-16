package com.zhuzirui.brt.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 社区互动表，记录用户在社区的互动内容
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Getter
@Setter
@TableName("community_interaction")
public class CommunityInteractionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增互动ID
     */
    @TableId(value = "interaction_id", type = IdType.AUTO)
    private Integer interactionId;

    /**
     * 用户ID，关联user表
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 互动内容
     */
    @TableField("content")
    private String content;

    /**
     * 互动类型：帖子、评论、点赞
     */
    @TableField("type")
    private String type;

    /**
     * 目标ID（如帖子ID或评论ID）
     */
    @TableField("target_id")
    private Integer targetId;

    /**
     * 互动创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 互动最近一次更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 互动是否删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;
}
