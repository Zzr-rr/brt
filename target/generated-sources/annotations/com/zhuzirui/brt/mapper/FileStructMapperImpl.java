package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.entity.File;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T00:00:28+0800",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class FileStructMapperImpl implements FileStructMapper {

    @Override
    public FileDTO entityToDto(File user) {
        if ( user == null ) {
            return null;
        }

        FileDTO fileDTO = new FileDTO();

        fileDTO.setCreatedAt( user.getCreatedAt() );
        fileDTO.setFileId( user.getFileId() );
        fileDTO.setFileName( user.getFileName() );
        fileDTO.setFileType( user.getFileType() );
        fileDTO.setFileUrl( user.getFileUrl() );
        fileDTO.setIsDeleted( user.getIsDeleted() );
        fileDTO.setIsPublic( user.getIsPublic() );
        fileDTO.setKeywords( user.getKeywords() );
        fileDTO.setUpdatedAt( user.getUpdatedAt() );
        fileDTO.setUploadTime( user.getUploadTime() );
        fileDTO.setUserId( user.getUserId() );

        return fileDTO;
    }

    @Override
    public File dtoToEntity(FileDTO dto) {
        if ( dto == null ) {
            return null;
        }

        File file = new File();

        file.setCreatedAt( dto.getCreatedAt() );
        file.setFileId( dto.getFileId() );
        file.setFileName( dto.getFileName() );
        file.setFileType( dto.getFileType() );
        file.setFileUrl( dto.getFileUrl() );
        file.setIsDeleted( dto.getIsDeleted() );
        file.setIsPublic( dto.getIsPublic() );
        file.setKeywords( dto.getKeywords() );
        file.setUpdatedAt( dto.getUpdatedAt() );
        file.setUploadTime( dto.getUploadTime() );
        file.setUserId( dto.getUserId() );

        return file;
    }
}
