package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.UserQuestionProgressDTO;
import com.zhuzirui.brt.model.dto.UserWrongQuestionDTO;
import com.zhuzirui.brt.model.entity.UserQuestionProgress;
import com.zhuzirui.brt.model.entity.UserWrongQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserWrongQuestionStructMapper {
    UserWrongQuestionStructMapper INSTANCE = Mappers.getMapper(UserWrongQuestionStructMapper.class);

    UserWrongQuestionDTO entityToDto(UserWrongQuestion userWrongQuestionProgress);

    UserWrongQuestion dtoToEntity(UserWrongQuestionDTO dto);
}
