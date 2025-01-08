package com.zhuzirui.brt.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 题库信息表，存储题库信息
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Getter
@Setter
@TableName("question_bank")
public class QuestionBank implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增题库唯一ID
     */
    @TableId(value = "bank_id", type = IdType.AUTO)
    private Integer bankId;

    /**
     * 上传用户ID，关联user表
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 题库标题
     */
    @TableField("title")
    private String title;

    /**
     * 题库描述
     */
    @TableField("description")
    private String description;

    /**
     * 题库封面图url
     */
    @TableField("cover_url")
    private String coverUrl;


    /**
     * 题库是否公开
     */
    @TableField("is_public")
    private Boolean isPublic;

    /**
     * 题库是否已经完成
     */
    @TableField(value = "is_completed")
    private Boolean isCompleted; // 是否生成完成


    /**
     * 题库的关键词，供搜索使用
     */
    @TableField("keywords")
    private String keywords;

    /**
     * 题库创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;


    /**
     * 题库最近一次更新时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 题库是否删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;
}
