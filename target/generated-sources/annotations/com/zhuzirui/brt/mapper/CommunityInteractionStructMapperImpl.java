package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.CommunityInteractionDTO;
import com.zhuzirui.brt.model.entity.CommunityInteraction;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T00:00:28+0800",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class CommunityInteractionStructMapperImpl implements CommunityInteractionStructMapper {

    @Override
    public CommunityInteractionDTO entityToDto(CommunityInteraction communityInteraction) {
        if ( communityInteraction == null ) {
            return null;
        }

        CommunityInteractionDTO communityInteractionDTO = new CommunityInteractionDTO();

        communityInteractionDTO.setContent( communityInteraction.getContent() );
        communityInteractionDTO.setCreatedAt( communityInteraction.getCreatedAt() );
        communityInteractionDTO.setInteractionId( communityInteraction.getInteractionId() );
        communityInteractionDTO.setIsDeleted( communityInteraction.getIsDeleted() );
        communityInteractionDTO.setLikesCount( communityInteraction.getLikesCount() );
        communityInteractionDTO.setTargetId( communityInteraction.getTargetId() );
        communityInteractionDTO.setType( communityInteraction.getType() );
        communityInteractionDTO.setUpdatedAt( communityInteraction.getUpdatedAt() );
        communityInteractionDTO.setUserId( communityInteraction.getUserId() );

        return communityInteractionDTO;
    }

    @Override
    public CommunityInteraction dtoToEntity(CommunityInteractionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CommunityInteraction communityInteraction = new CommunityInteraction();

        communityInteraction.setContent( dto.getContent() );
        communityInteraction.setCreatedAt( dto.getCreatedAt() );
        communityInteraction.setInteractionId( dto.getInteractionId() );
        communityInteraction.setIsDeleted( dto.getIsDeleted() );
        communityInteraction.setLikesCount( dto.getLikesCount() );
        communityInteraction.setTargetId( dto.getTargetId() );
        communityInteraction.setType( dto.getType() );
        communityInteraction.setUpdatedAt( dto.getUpdatedAt() );
        communityInteraction.setUserId( dto.getUserId() );

        return communityInteraction;
    }
}
