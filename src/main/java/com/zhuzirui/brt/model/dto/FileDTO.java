package com.zhuzirui.brt.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@Getter
@Setter
@TableName("file")
public class FileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增文件唯一ID
     */
    @TableId(value = "file_id", type = IdType.AUTO)
    private Integer fileId;

    /**
     * 上传用户ID，关联user表
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 文件存储的URL
     */
    @TableField("file_url")
    private String fileUrl;

    /**
     * 文件上传时间
     */
    @TableField("upload_time")
    private LocalDateTime uploadTime;

    /**
     * 文件是否公开
     */
    @TableField("is_public")
    private Boolean isPublic;

    /**
     * 文件关键词，供搜索使用
     */
    @TableField("keywords")
    private String keywords;

    /**
     * 文件创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 文件最近一次更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 文件是否删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;
}
