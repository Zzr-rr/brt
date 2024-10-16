package com.zhuzirui.brt.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户做题记录表，记录用户做题进度和答题信息
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Getter
@Setter
@TableName("user_question_progress")
public class UserQuestionProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增进度记录ID
     */
    @TableId(value = "progress_id", type = IdType.AUTO)
    private Integer progressId;

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
     * 第几次尝试
     */
    @TableField("attempt_number")
    private Integer attemptNumber;

    /**
     * 用户答题是否正确
     */
    @TableField("is_correct")
    private Boolean isCorrect;

    /**
     * 答题时间
     */
    @TableField("attempt_time")
    private LocalDateTime attemptTime;

    /**
     * 用户的答案，以JSON格式存储
     */
    @TableField("user_answer")
    private String userAnswer;

    /**
     * 进度记录是否删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;
}
