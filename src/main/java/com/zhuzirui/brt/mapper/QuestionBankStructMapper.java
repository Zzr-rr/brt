package com.zhuzirui.brt.mapper;
import com.zhuzirui.brt.model.dto.QuestionBankDTO;
import com.zhuzirui.brt.model.entity.QuestionBank;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionBankStructMapper {
    QuestionBankStructMapper INSTANCE = Mappers.getMapper(QuestionBankStructMapper.class);

    QuestionBankDTO entityToDto(QuestionBank questionBankStructMapper);

    QuestionBank dtoToEntity(QuestionBankDTO dto);
}
