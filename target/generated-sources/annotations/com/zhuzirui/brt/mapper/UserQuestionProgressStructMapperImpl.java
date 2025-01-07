package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.UserQuestionProgressDTO;
import com.zhuzirui.brt.model.entity.UserQuestionProgress;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T00:00:28+0800",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class UserQuestionProgressStructMapperImpl implements UserQuestionProgressStructMapper {

    @Override
    public UserQuestionProgressDTO entityToDto(UserQuestionProgress userQuestionProgress) {
        if ( userQuestionProgress == null ) {
            return null;
        }

        UserQuestionProgressDTO userQuestionProgressDTO = new UserQuestionProgressDTO();

        userQuestionProgressDTO.setAttemptNumber( userQuestionProgress.getAttemptNumber() );
        userQuestionProgressDTO.setAttemptTime( userQuestionProgress.getAttemptTime() );
        userQuestionProgressDTO.setIsCorrect( userQuestionProgress.getIsCorrect() );
        userQuestionProgressDTO.setIsDeleted( userQuestionProgress.getIsDeleted() );
        userQuestionProgressDTO.setProgressId( userQuestionProgress.getProgressId() );
        userQuestionProgressDTO.setQuestionId( userQuestionProgress.getQuestionId() );
        userQuestionProgressDTO.setUserAnswer( userQuestionProgress.getUserAnswer() );
        userQuestionProgressDTO.setUserId( userQuestionProgress.getUserId() );

        return userQuestionProgressDTO;
    }

    @Override
    public UserQuestionProgress dtoToEntity(UserQuestionProgressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserQuestionProgress userQuestionProgress = new UserQuestionProgress();

        userQuestionProgress.setAttemptNumber( dto.getAttemptNumber() );
        userQuestionProgress.setAttemptTime( dto.getAttemptTime() );
        userQuestionProgress.setIsCorrect( dto.getIsCorrect() );
        userQuestionProgress.setIsDeleted( dto.getIsDeleted() );
        userQuestionProgress.setProgressId( dto.getProgressId() );
        userQuestionProgress.setQuestionId( dto.getQuestionId() );
        userQuestionProgress.setUserAnswer( dto.getUserAnswer() );
        userQuestionProgress.setUserId( dto.getUserId() );

        return userQuestionProgress;
    }
}
