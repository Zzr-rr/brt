package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.UserWrongQuestionDTO;
import com.zhuzirui.brt.model.entity.UserWrongQuestion;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T00:00:28+0800",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class UserWrongQuestionStructMapperImpl implements UserWrongQuestionStructMapper {

    @Override
    public UserWrongQuestionDTO entityToDto(UserWrongQuestion userWrongQuestionProgress) {
        if ( userWrongQuestionProgress == null ) {
            return null;
        }

        UserWrongQuestionDTO userWrongQuestionDTO = new UserWrongQuestionDTO();

        userWrongQuestionDTO.setAddedAt( userWrongQuestionProgress.getAddedAt() );
        userWrongQuestionDTO.setIsDeleted( userWrongQuestionProgress.getIsDeleted() );
        userWrongQuestionDTO.setQuestionId( userWrongQuestionProgress.getQuestionId() );
        userWrongQuestionDTO.setReviewStatus( userWrongQuestionProgress.getReviewStatus() );
        userWrongQuestionDTO.setUserId( userWrongQuestionProgress.getUserId() );
        userWrongQuestionDTO.setWrongId( userWrongQuestionProgress.getWrongId() );

        return userWrongQuestionDTO;
    }

    @Override
    public UserWrongQuestion dtoToEntity(UserWrongQuestionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserWrongQuestion userWrongQuestion = new UserWrongQuestion();

        userWrongQuestion.setAddedAt( dto.getAddedAt() );
        userWrongQuestion.setIsDeleted( dto.getIsDeleted() );
        userWrongQuestion.setQuestionId( dto.getQuestionId() );
        userWrongQuestion.setReviewStatus( dto.getReviewStatus() );
        userWrongQuestion.setUserId( dto.getUserId() );
        userWrongQuestion.setWrongId( dto.getWrongId() );

        return userWrongQuestion;
    }
}
