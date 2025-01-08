package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.QuestionBankDTO;
import com.zhuzirui.brt.model.entity.QuestionBank;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T23:09:46+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
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
        questionBankDTO.setUserId( questionBankStructMapper.getUserId() );
        questionBankDTO.setTitle( questionBankStructMapper.getTitle() );
        questionBankDTO.setDescription( questionBankStructMapper.getDescription() );
        questionBankDTO.setCoverUrl( questionBankStructMapper.getCoverUrl() );
        questionBankDTO.setKeywords( questionBankStructMapper.getKeywords() );
        questionBankDTO.setIsPublic( questionBankStructMapper.getIsPublic() );
        questionBankDTO.setIsDeleted( questionBankStructMapper.getIsDeleted() );
        questionBankDTO.setIsCompleted( questionBankStructMapper.getIsCompleted() );
        questionBankDTO.setCreatedAt( questionBankStructMapper.getCreatedAt() );
        questionBankDTO.setUpdatedAt( questionBankStructMapper.getUpdatedAt() );

        return questionBankDTO;
    }

    @Override
    public QuestionBank dtoToEntity(QuestionBankDTO dto) {
        if ( dto == null ) {
            return null;
        }

        QuestionBank questionBank = new QuestionBank();

        questionBank.setBankId( dto.getBankId() );
        questionBank.setUserId( dto.getUserId() );
        questionBank.setTitle( dto.getTitle() );
        questionBank.setDescription( dto.getDescription() );
        questionBank.setCoverUrl( dto.getCoverUrl() );
        questionBank.setIsPublic( dto.getIsPublic() );
        questionBank.setIsCompleted( dto.getIsCompleted() );
        questionBank.setKeywords( dto.getKeywords() );
        questionBank.setCreatedAt( dto.getCreatedAt() );
        questionBank.setUpdatedAt( dto.getUpdatedAt() );
        questionBank.setIsDeleted( dto.getIsDeleted() );

        return questionBank;
    }
}
