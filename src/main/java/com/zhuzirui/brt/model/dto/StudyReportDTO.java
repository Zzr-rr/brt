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
 * 学习报告表，存储用户的学习统计报告
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Data
public class StudyReportDTO {
    private Integer reportId;
    private Integer userId;
    private String reportData;
    private LocalDateTime generatedAt;
    private Boolean isDeleted;
}
