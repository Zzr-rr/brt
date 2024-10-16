package com.zhuzirui.brt.dao;

import com.zhuzirui.brt.model.entity.DownloadHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 下载记录表，记录用户的文件下载历史 Mapper 接口
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Mapper
public interface DownloadHistoryMapper extends BaseMapper<DownloadHistory> {

}
