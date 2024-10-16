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
 * 文件管理表，存储用户上传文件的信息
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Data
public class FileDTO {
    private Integer fileId;
    private Integer userId;
    private String fileName;
    private String fileType;
    private String fileUrl;
    private LocalDateTime uploadTime;
    private Boolean isPublic;
    private String keywords;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
}
