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
 * 下载记录表，记录用户的文件下载历史
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Data
public class DownloadHistoryDTO {
    private Integer downloadId;
    private Integer userId;
    private Integer fileId;
    private LocalDateTime downloadTime;
    private Boolean isDeleted;
}
