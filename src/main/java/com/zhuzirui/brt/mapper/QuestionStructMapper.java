package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.QuestionDTO;
import com.zhuzirui.brt.model.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionStructMapper {
    QuestionStructMapper INSTANCE = Mappers.getMapper(QuestionStructMapper.class);

    QuestionDTO entityToDto(Question questionBankStructMapper);

    Question dtoToEntity(QuestionDTO dto);
}
