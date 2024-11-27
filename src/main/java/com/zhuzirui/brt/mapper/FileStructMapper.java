package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FileStructMapper {
    FileStructMapper INSTANCE = Mappers.getMapper(FileStructMapper.class);

    FileDTO entityToDto(File user);

    File dtoToEntity(FileDTO dto);
}
