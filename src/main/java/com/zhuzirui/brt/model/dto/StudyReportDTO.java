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
 * 学习报告表，存储用户的学习统计报告
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Getter
@Setter
@TableName("study_report")
public class StudyReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增报告唯一ID
     */
    @TableId(value = "report_id", type = IdType.AUTO)
    private Integer reportId;

    /**
     * 用户ID，关联user表
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 学习报告内容，以JSON格式存储
     */
    @TableField("report_data")
    private String reportData;

    /**
     * 报告生成时间
     */
    @TableField("generated_at")
    private LocalDateTime generatedAt;

    /**
     * 报告是否删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;
}
