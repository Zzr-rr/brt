package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.entity.File;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-21T23:53:12+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class FileStructMapperImpl implements FileStructMapper {

    @Override
    public FileDTO entityToDto(File user) {
        if ( user == null ) {
            return null;
        }

        FileDTO fileDTO = new FileDTO();

        fileDTO.setFileId( user.getFileId() );
        fileDTO.setUserId( user.getUserId() );
        fileDTO.setFileName( user.getFileName() );
        fileDTO.setIsPublic( user.getIsPublic() );
        fileDTO.setKeywords( user.getKeywords() );
        fileDTO.setFileType( user.getFileType() );
        fileDTO.setFileUrl( user.getFileUrl() );
        fileDTO.setUpdatedAt( user.getUpdatedAt() );
        fileDTO.setUploadTime( user.getUploadTime() );
        fileDTO.setCreatedAt( user.getCreatedAt() );
        fileDTO.setIsDeleted( user.getIsDeleted() );

        return fileDTO;
    }

    @Override
    public File dtoToEntity(FileDTO dto) {
        if ( dto == null ) {
            return null;
        }

        File file = new File();

        file.setFileId( dto.getFileId() );
        file.setUserId( dto.getUserId() );
        file.setFileName( dto.getFileName() );
        file.setFileType( dto.getFileType() );
        file.setFileUrl( dto.getFileUrl() );
        file.setUploadTime( dto.getUploadTime() );
        file.setIsPublic( dto.getIsPublic() );
        file.setKeywords( dto.getKeywords() );
        file.setCreatedAt( dto.getCreatedAt() );
        file.setUpdatedAt( dto.getUpdatedAt() );
        file.setIsDeleted( dto.getIsDeleted() );

        return file;
    }
}
