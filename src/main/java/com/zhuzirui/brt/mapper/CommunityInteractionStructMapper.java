package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.CommunityInteractionDTO;
import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.entity.CommunityInteraction;
import com.zhuzirui.brt.model.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommunityInteractionStructMapper {
    CommunityInteractionStructMapper INSTANCE = Mappers.getMapper(CommunityInteractionStructMapper.class);

    CommunityInteractionDTO entityToDto(CommunityInteraction communityInteraction);

    CommunityInteraction dtoToEntity(CommunityInteractionDTO dto);
}
