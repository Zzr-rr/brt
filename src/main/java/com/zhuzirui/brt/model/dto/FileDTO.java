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

    //允许修改
    private String fileName;
    private Boolean isPublic;
    private String keywords;

    private String fileType;
    private String fileUrl;

    private LocalDateTime updatedAt;
    private LocalDateTime uploadTime;
    private LocalDateTime createdAt;

    private Boolean isDeleted;

}
