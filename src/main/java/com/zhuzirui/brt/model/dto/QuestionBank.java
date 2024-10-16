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
     * 题库是否公开
     */
    @TableField("is_public")
    private Boolean isPublic;

    /**
     * 题库的关键词，供搜索使用
     */
    @TableField("keywords")
    private String keywords;

    /**
     * 题库创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 题库最近一次更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 题库是否删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;
}
