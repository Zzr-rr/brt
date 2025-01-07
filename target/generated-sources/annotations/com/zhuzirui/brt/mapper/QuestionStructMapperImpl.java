package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.QuestionDTO;
import com.zhuzirui.brt.model.entity.Question;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T00:00:28+0800",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class QuestionStructMapperImpl implements QuestionStructMapper {

    @Override
    public QuestionDTO entityToDto(Question questionBankStructMapper) {
        if ( questionBankStructMapper == null ) {
            return null;
        }

        QuestionDTO questionDTO = new QuestionDTO();

        questionDTO.setBankId( questionBankStructMapper.getBankId() );
        questionDTO.setCorrectAnswer( questionBankStructMapper.getCorrectAnswer() );
        questionDTO.setCreatedAt( questionBankStructMapper.getCreatedAt() );
        questionDTO.setDifficulty( questionBankStructMapper.getDifficulty() );
        questionDTO.setIsDeleted( questionBankStructMapper.getIsDeleted() );
        questionDTO.setOptions( questionBankStructMapper.getOptions() );
        questionDTO.setQuestionId( questionBankStructMapper.getQuestionId() );
        questionDTO.setQuestionText( questionBankStructMapper.getQuestionText() );
        questionDTO.setQuestionType( questionBankStructMapper.getQuestionType() );
        questionDTO.setUpdatedAt( questionBankStructMapper.getUpdatedAt() );

        return questionDTO;
    }

    @Override
    public Question dtoToEntity(QuestionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Question question = new Question();

        question.setBankId( dto.getBankId() );
        question.setCorrectAnswer( dto.getCorrectAnswer() );
        question.setCreatedAt( dto.getCreatedAt() );
        question.setDifficulty( dto.getDifficulty() );
        question.setIsDeleted( dto.getIsDeleted() );
        question.setOptions( dto.getOptions() );
        question.setQuestionId( dto.getQuestionId() );
        question.setQuestionText( dto.getQuestionText() );
        question.setQuestionType( dto.getQuestionType() );
        question.setUpdatedAt( dto.getUpdatedAt() );

        return question;
    }
}
