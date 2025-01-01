package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.QuestionDTO;
import com.zhuzirui.brt.model.entity.Question;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-01T13:23:57+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class QuestionStructMapperImpl implements QuestionStructMapper {

    @Override
    public QuestionDTO entityToDto(Question questionBankStructMapper) {
        if ( questionBankStructMapper == null ) {
            return null;
        }

        QuestionDTO questionDTO = new QuestionDTO();

        questionDTO.setQuestionId( questionBankStructMapper.getQuestionId() );
        questionDTO.setBankId( questionBankStructMapper.getBankId() );
        questionDTO.setQuestionText( questionBankStructMapper.getQuestionText() );
        questionDTO.setQuestionType( questionBankStructMapper.getQuestionType() );
        questionDTO.setOptions( questionBankStructMapper.getOptions() );
        questionDTO.setCorrectAnswer( questionBankStructMapper.getCorrectAnswer() );
        questionDTO.setDifficulty( questionBankStructMapper.getDifficulty() );
        questionDTO.setCreatedAt( questionBankStructMapper.getCreatedAt() );
        questionDTO.setUpdatedAt( questionBankStructMapper.getUpdatedAt() );
        questionDTO.setIsDeleted( questionBankStructMapper.getIsDeleted() );

        return questionDTO;
    }

    @Override
    public Question dtoToEntity(QuestionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Question question = new Question();

        question.setQuestionId( dto.getQuestionId() );
        question.setBankId( dto.getBankId() );
        question.setQuestionText( dto.getQuestionText() );
        question.setQuestionType( dto.getQuestionType() );
        question.setOptions( dto.getOptions() );
        question.setCorrectAnswer( dto.getCorrectAnswer() );
        question.setDifficulty( dto.getDifficulty() );
        question.setCreatedAt( dto.getCreatedAt() );
        question.setUpdatedAt( dto.getUpdatedAt() );
        question.setIsDeleted( dto.getIsDeleted() );

        return question;
    }
}
