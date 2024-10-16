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
 * 错题本表，记录用户的错题和复习状态
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Getter
@Setter
@TableName("user_wrong_question")
public class UserWrongQuestionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增错题记录ID
     */
    @TableId(value = "wrong_id", type = IdType.AUTO)
    private Integer wrongId;

    /**
     * 用户ID，关联user表
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 题目ID，关联question表
     */
    @TableField("question_id")
    private Integer questionId;

    /**
     * 错题添加时间
     */
    @TableField("added_at")
    private LocalDateTime addedAt;

    /**
     * 复习状态
     */
    @TableField("review_status")
    private String reviewStatus;

    /**
     * 错题记录是否删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;
}
