package com.zhuzirui.brt.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 题目表，存储题库中的各题目和答案信息
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Getter
@Setter
@TableName("question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增题目唯一ID
     */
    @TableId(value = "question_id", type = IdType.AUTO)
    private Integer questionId;

    /**
     * 题库ID，关联question_bank表
     */
    @TableField("bank_id")
    private Integer bankId;

    /**
     * 题目内容
     */
    @TableField("question_text")
    private String questionText;

    /**
     * 题目类型
     */
    @TableField("question_type")
    private String questionType;

    /**
     * 选择题的选项，以JSON格式存储
     */
    @TableField("options")
    private String options;

    /**
     * 正确答案，以JSON格式存储
     */
    @TableField("correct_answer")
    private String correctAnswer;

    /**
     * 题目难度
     */
    @TableField("difficulty")
    private String difficulty;

    /**
     * 题目创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 题目最近一次更新时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 题目是否删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;
}
