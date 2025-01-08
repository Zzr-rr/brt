package com.zhuzirui.brt.model.dto;

import com.zhuzirui.brt.model.entity.CommunityInteraction;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Integer interactionId;
    private Integer userId;
    private String content;
    private String type;
    private Integer targetId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer isDeleted;
    private Integer likesCount;
    private Integer commentCount; // 评论数
}
