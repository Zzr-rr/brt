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
 * 题库信息表，存储题库信息
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Data
public class QuestionBank {
    private Integer bankId;
    private Integer userId;

    private String title;
    private String description;
    private Boolean isPublic;
    private String keywords;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
}
