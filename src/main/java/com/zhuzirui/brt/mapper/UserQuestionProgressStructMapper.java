package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.UserQuestionProgressDTO;
import com.zhuzirui.brt.model.entity.UserQuestionProgress;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserQuestionProgressStructMapper {
    UserQuestionProgressStructMapper INSTANCE = Mappers.getMapper(UserQuestionProgressStructMapper.class);

    UserQuestionProgressDTO entityToDto(UserQuestionProgress userQuestionProgress);

    UserQuestionProgress dtoToEntity(UserQuestionProgressDTO dto);
}
