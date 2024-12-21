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
 * 社区互动表，记录用户在社区的互动内容
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Data
public class CommunityInteractionDTO implements Serializable {


    private Integer interactionId;

    private Integer userId;

    private String content;
    private String type;
    private Integer targetId;


    private Integer likesCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
}
