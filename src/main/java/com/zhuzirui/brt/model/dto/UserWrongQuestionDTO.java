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
 * 错题本表，记录用户的错题和复习状态
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Data
public class UserWrongQuestionDTO {
    private Integer wrongId;
    private Integer userId;
    private Integer questionId;
    private LocalDateTime addedAt;
    private String reviewStatus;
    private Boolean isDeleted;
}
