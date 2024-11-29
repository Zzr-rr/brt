package com.zhuzirui.brt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuzirui.brt.model.dto.DownloadHistoryDTO;
import com.zhuzirui.brt.model.entity.DownloadHistory;
import com.zhuzirui.brt.dao.DownloadHistoryMapper;
import com.zhuzirui.brt.model.entity.File;
import com.zhuzirui.brt.service.DownloadHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 下载记录表，记录用户的文件下载历史 服务实现类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Service
public class DownloadHistoryServiceImpl extends ServiceImpl<DownloadHistoryMapper, DownloadHistory> implements DownloadHistoryService {
    @Autowired
    private DownloadHistoryMapper downloadHistoryMapper;

    @Override
    public Integer saveDownloadHistory(DownloadHistory downloadHistory) throws Exception{
         int insert = downloadHistoryMapper.insert(downloadHistory);
         if (insert != 1) {
            throw new Exception("saved failed");
         }
         return downloadHistory.getDownloadId();
    }
}
