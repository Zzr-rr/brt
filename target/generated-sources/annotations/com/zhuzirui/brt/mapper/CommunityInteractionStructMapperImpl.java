package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.CommunityInteractionDTO;
import com.zhuzirui.brt.model.entity.CommunityInteraction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-22T05:51:02+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class CommunityInteractionStructMapperImpl implements CommunityInteractionStructMapper {

    @Override
    public CommunityInteractionDTO entityToDto(CommunityInteraction communityInteraction) {
        if ( communityInteraction == null ) {
            return null;
        }

        CommunityInteractionDTO communityInteractionDTO = new CommunityInteractionDTO();

        communityInteractionDTO.setInteractionId( communityInteraction.getInteractionId() );
        communityInteractionDTO.setUserId( communityInteraction.getUserId() );
        communityInteractionDTO.setContent( communityInteraction.getContent() );
        communityInteractionDTO.setType( communityInteraction.getType() );
        communityInteractionDTO.setTargetId( communityInteraction.getTargetId() );
        communityInteractionDTO.setLikesCount( communityInteraction.getLikesCount() );
        communityInteractionDTO.setCreatedAt( communityInteraction.getCreatedAt() );
        communityInteractionDTO.setUpdatedAt( communityInteraction.getUpdatedAt() );
        communityInteractionDTO.setIsDeleted( communityInteraction.getIsDeleted() );

        return communityInteractionDTO;
    }

    @Override
    public CommunityInteraction dtoToEntity(CommunityInteractionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CommunityInteraction communityInteraction = new CommunityInteraction();

        communityInteraction.setInteractionId( dto.getInteractionId() );
        communityInteraction.setUserId( dto.getUserId() );
        communityInteraction.setContent( dto.getContent() );
        communityInteraction.setType( dto.getType() );
        communityInteraction.setTargetId( dto.getTargetId() );
        communityInteraction.setCreatedAt( dto.getCreatedAt() );
        communityInteraction.setLikesCount( dto.getLikesCount() );
        communityInteraction.setUpdatedAt( dto.getUpdatedAt() );
        communityInteraction.setIsDeleted( dto.getIsDeleted() );

        return communityInteraction;
    }
}
