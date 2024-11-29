package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.DownloadHistoryDTO;
import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.entity.DownloadHistory;
import com.zhuzirui.brt.model.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DownloadHistoryStructMapper {
    DownloadHistoryStructMapper INSTANCE = Mappers.getMapper(DownloadHistoryStructMapper.class);

    DownloadHistoryDTO entityToDto(DownloadHistory downloadHistory);

    DownloadHistory dtoToEntity(DownloadHistoryDTO dto);
}
