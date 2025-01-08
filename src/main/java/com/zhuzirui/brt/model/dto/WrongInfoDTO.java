package com.zhuzirui.brt.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WrongInfoDTO {
    private Integer wrongId;
    private Integer userId;
    private Integer questionId;
    private LocalDateTime addedAt;
    private String reviewStatus;
    private Boolean isDeleted;

    private String questionText;
    private String difficulty;
}
