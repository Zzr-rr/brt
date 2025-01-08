package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.DownloadHistoryDTO;
import com.zhuzirui.brt.model.entity.DownloadHistory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T22:54:20+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class DownloadHistoryStructMapperImpl implements DownloadHistoryStructMapper {

    @Override
    public DownloadHistoryDTO entityToDto(DownloadHistory downloadHistory) {
        if ( downloadHistory == null ) {
            return null;
        }

        DownloadHistoryDTO downloadHistoryDTO = new DownloadHistoryDTO();

        downloadHistoryDTO.setDownloadId( downloadHistory.getDownloadId() );
        downloadHistoryDTO.setUserId( downloadHistory.getUserId() );
        downloadHistoryDTO.setSourceUrl( downloadHistory.getSourceUrl() );
        downloadHistoryDTO.setDownloadTime( downloadHistory.getDownloadTime() );
        downloadHistoryDTO.setIsDeleted( downloadHistory.getIsDeleted() );

        return downloadHistoryDTO;
    }

    @Override
    public DownloadHistory dtoToEntity(DownloadHistoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DownloadHistory downloadHistory = new DownloadHistory();

        downloadHistory.setDownloadId( dto.getDownloadId() );
        downloadHistory.setUserId( dto.getUserId() );
        downloadHistory.setSourceUrl( dto.getSourceUrl() );
        downloadHistory.setDownloadTime( dto.getDownloadTime() );
        downloadHistory.setIsDeleted( dto.getIsDeleted() );

        return downloadHistory;
    }
}
