package com.zhuzirui.brt.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户做题记录表，记录用户做题进度和答题信息
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Data
public class UserQuestionProgressDTO{
    private Integer progressId;

    private Integer userId;
    private Integer attemptNumber;

    private Boolean isCorrect;
    private Integer questionId;
    private String userAnswer;

    private LocalDateTime attemptTime;
    private Boolean isDeleted;
}
