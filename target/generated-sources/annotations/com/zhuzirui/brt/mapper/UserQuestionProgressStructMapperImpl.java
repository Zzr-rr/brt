package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.UserQuestionProgressDTO;
import com.zhuzirui.brt.model.entity.UserQuestionProgress;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-22T04:11:20+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class UserQuestionProgressStructMapperImpl implements UserQuestionProgressStructMapper {

    @Override
    public UserQuestionProgressDTO entityToDto(UserQuestionProgress userQuestionProgress) {
        if ( userQuestionProgress == null ) {
            return null;
        }

        UserQuestionProgressDTO userQuestionProgressDTO = new UserQuestionProgressDTO();

        userQuestionProgressDTO.setProgressId( userQuestionProgress.getProgressId() );
        userQuestionProgressDTO.setUserId( userQuestionProgress.getUserId() );
        userQuestionProgressDTO.setAttemptNumber( userQuestionProgress.getAttemptNumber() );
        userQuestionProgressDTO.setIsCorrect( userQuestionProgress.getIsCorrect() );
        userQuestionProgressDTO.setQuestionId( userQuestionProgress.getQuestionId() );
        userQuestionProgressDTO.setUserAnswer( userQuestionProgress.getUserAnswer() );
        userQuestionProgressDTO.setAttemptTime( userQuestionProgress.getAttemptTime() );
        userQuestionProgressDTO.setIsDeleted( userQuestionProgress.getIsDeleted() );

        return userQuestionProgressDTO;
    }

    @Override
    public UserQuestionProgress dtoToEntity(UserQuestionProgressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserQuestionProgress userQuestionProgress = new UserQuestionProgress();

        userQuestionProgress.setProgressId( dto.getProgressId() );
        userQuestionProgress.setUserId( dto.getUserId() );
        userQuestionProgress.setQuestionId( dto.getQuestionId() );
        userQuestionProgress.setAttemptNumber( dto.getAttemptNumber() );
        userQuestionProgress.setIsCorrect( dto.getIsCorrect() );
        userQuestionProgress.setAttemptTime( dto.getAttemptTime() );
        userQuestionProgress.setUserAnswer( dto.getUserAnswer() );
        userQuestionProgress.setIsDeleted( dto.getIsDeleted() );

        return userQuestionProgress;
    }
}
