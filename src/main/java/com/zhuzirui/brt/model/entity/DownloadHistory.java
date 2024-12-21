package com.zhuzirui.brt.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 下载记录表，记录用户的文件下载历史
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Getter
@Setter
@TableName("download_history")
public class DownloadHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增下载记录ID
     */
    @TableId(value = "download_id", type = IdType.AUTO)
    private Integer downloadId;

    /**
     * 下载用户ID，关联user表
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 下载文件URL
     */
    @TableField("source_url")
    private String sourceUrl;

    /**
     * 下载时间
     */
    @TableField("download_time")
    private LocalDateTime downloadTime;

    /**
     * 下载记录是否删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;
}
