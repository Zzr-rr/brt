package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.UserWrongQuestionDTO;
import com.zhuzirui.brt.model.entity.UserWrongQuestion;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-01T13:23:57+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class UserWrongQuestionStructMapperImpl implements UserWrongQuestionStructMapper {

    @Override
    public UserWrongQuestionDTO entityToDto(UserWrongQuestion userWrongQuestionProgress) {
        if ( userWrongQuestionProgress == null ) {
            return null;
        }

        UserWrongQuestionDTO userWrongQuestionDTO = new UserWrongQuestionDTO();

        userWrongQuestionDTO.setWrongId( userWrongQuestionProgress.getWrongId() );
        userWrongQuestionDTO.setUserId( userWrongQuestionProgress.getUserId() );
        userWrongQuestionDTO.setQuestionId( userWrongQuestionProgress.getQuestionId() );
        userWrongQuestionDTO.setAddedAt( userWrongQuestionProgress.getAddedAt() );
        userWrongQuestionDTO.setReviewStatus( userWrongQuestionProgress.getReviewStatus() );
        userWrongQuestionDTO.setIsDeleted( userWrongQuestionProgress.getIsDeleted() );

        return userWrongQuestionDTO;
    }

    @Override
    public UserWrongQuestion dtoToEntity(UserWrongQuestionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserWrongQuestion userWrongQuestion = new UserWrongQuestion();

        userWrongQuestion.setWrongId( dto.getWrongId() );
        userWrongQuestion.setUserId( dto.getUserId() );
        userWrongQuestion.setQuestionId( dto.getQuestionId() );
        userWrongQuestion.setAddedAt( dto.getAddedAt() );
        userWrongQuestion.setReviewStatus( dto.getReviewStatus() );
        userWrongQuestion.setIsDeleted( dto.getIsDeleted() );

        return userWrongQuestion;
    }
}
