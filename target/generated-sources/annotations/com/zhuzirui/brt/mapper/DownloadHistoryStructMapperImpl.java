package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.DownloadHistoryDTO;
import com.zhuzirui.brt.model.entity.DownloadHistory;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T00:00:28+0800",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
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
        downloadHistoryDTO.setDownloadTime( downloadHistory.getDownloadTime() );
        downloadHistoryDTO.setIsDeleted( downloadHistory.getIsDeleted() );
        downloadHistoryDTO.setSourceUrl( downloadHistory.getSourceUrl() );
        downloadHistoryDTO.setUserId( downloadHistory.getUserId() );

        return downloadHistoryDTO;
    }

    @Override
    public DownloadHistory dtoToEntity(DownloadHistoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DownloadHistory downloadHistory = new DownloadHistory();

        downloadHistory.setDownloadId( dto.getDownloadId() );
        downloadHistory.setDownloadTime( dto.getDownloadTime() );
        downloadHistory.setIsDeleted( dto.getIsDeleted() );
        downloadHistory.setSourceUrl( dto.getSourceUrl() );
        downloadHistory.setUserId( dto.getUserId() );

        return downloadHistory;
    }
}
