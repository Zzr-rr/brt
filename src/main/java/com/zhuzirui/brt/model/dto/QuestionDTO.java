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
 * 题目表，存储题库中的各题目和答案信息
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Data
public class QuestionDTO {
    private Integer questionId;
    private Integer bankId;

    private String questionText;
    private String questionType;
    private String options;
    private String correctAnswer;
    private String difficulty;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
}
