package com.zhuzirui.brt.model.dto;

import lombok.Data;

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
public class QuestionBankDTO {
    private Integer bankId;
    private Integer userId;

    private String title;
    private String description;
    private String coverUrl;
    private String keywords;

    private Boolean isPublic;
    private Boolean isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
