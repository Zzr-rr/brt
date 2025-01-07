package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.QuestionBankDTO;
import com.zhuzirui.brt.model.entity.QuestionBank;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T00:00:28+0800",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class QuestionBankStructMapperImpl implements QuestionBankStructMapper {

    @Override
    public QuestionBankDTO entityToDto(QuestionBank questionBankStructMapper) {
        if ( questionBankStructMapper == null ) {
            return null;
        }

        QuestionBankDTO questionBankDTO = new QuestionBankDTO();

        questionBankDTO.setBankId( questionBankStructMapper.getBankId() );
        questionBankDTO.setCoverUrl( questionBankStructMapper.getCoverUrl() );
        questionBankDTO.setCreatedAt( questionBankStructMapper.getCreatedAt() );
        questionBankDTO.setDescription( questionBankStructMapper.getDescription() );
        questionBankDTO.setIsDeleted( questionBankStructMapper.getIsDeleted() );
        questionBankDTO.setIsPublic( questionBankStructMapper.getIsPublic() );
        questionBankDTO.setKeywords( questionBankStructMapper.getKeywords() );
        questionBankDTO.setTitle( questionBankStructMapper.getTitle() );
        questionBankDTO.setUpdatedAt( questionBankStructMapper.getUpdatedAt() );
        questionBankDTO.setUserId( questionBankStructMapper.getUserId() );

        return questionBankDTO;
    }

    @Override
    public QuestionBank dtoToEntity(QuestionBankDTO dto) {
        if ( dto == null ) {
            return null;
        }

        QuestionBank questionBank = new QuestionBank();

        questionBank.setBankId( dto.getBankId() );
        questionBank.setCoverUrl( dto.getCoverUrl() );
        questionBank.setCreatedAt( dto.getCreatedAt() );
        questionBank.setDescription( dto.getDescription() );
        questionBank.setIsDeleted( dto.getIsDeleted() );
        questionBank.setIsPublic( dto.getIsPublic() );
        questionBank.setKeywords( dto.getKeywords() );
        questionBank.setTitle( dto.getTitle() );
        questionBank.setUpdatedAt( dto.getUpdatedAt() );
        questionBank.setUserId( dto.getUserId() );

        return questionBank;
    }
}
