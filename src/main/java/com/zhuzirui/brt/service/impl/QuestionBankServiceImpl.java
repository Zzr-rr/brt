package com.zhuzirui.brt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuzirui.brt.model.dto.QuestionBankDTO;
import com.zhuzirui.brt.model.entity.File;
import com.zhuzirui.brt.model.entity.QuestionBank;
import com.zhuzirui.brt.dao.QuestionBankMapper;
import com.zhuzirui.brt.service.QuestionBankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 题库信息表，存储题库信息 服务实现类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements QuestionBankService {
    @Autowired
    private QuestionBankMapper questionBankMapper;

    private final static Logger logger = org.apache.logging.log4j.LogManager.getLogger(QuestionBankServiceImpl.class);
    
    @Override
    public Integer saveQuestionBank(QuestionBank questionBank) throws Exception {
        int insert = questionBankMapper.insert(questionBank);
        if (insert < 1) {
            throw new Exception("saved failed");
        }
        return questionBank.getBankId();
    }

    @Override
    public List<QuestionBank> listQuestionBanks(QuestionBankDTO questionBankDTO) throws Exception {
        logger.info("in listFiles:\n"+questionBankDTO.toString());

        QueryWrapper<QuestionBank> queryWrapper = new QueryWrapper<QuestionBank>();

        // 检查DTO中的属性是否非空，并添加到查询条件中
//        if(questionBankDTO.getUserId()!=null){
//            queryWrapper.eq("user_id", questionBankDTO.getUserId());
//        }
//        if (questionBankDTO.getBankId() != null) {
//            queryWrapper.eq("bank_id", questionBankDTO.getBankId());
//        }
//        if (questionBankDTO.getFileName() != null && !questionBankDTO.getFileName().isEmpty()) {
//            queryWrapper.like("file_name", questionBankDTO.getFileName());
//        }
//        if (questionBankDTO.getIsPublic() != null) {
//            queryWrapper.eq("is_public", questionBankDTO.getIsPublic());
//        }
//        if (questionBankDTO.getKeywords() != null && !questionBankDTO.getKeywords().isEmpty()) {
//            queryWrapper.like("keywords", questionBankDTO.getKeywords());
//        }
//        if (questionBankDTO.getUpdatedAt() != null) {
//            queryWrapper.le("updated_at", questionBankDTO.getUpdatedAt());
//        }
//        if (questionBankDTO.getFileType() != null && !questionBankDTO.getFileType().isEmpty()) {
//            queryWrapper.eq("file_type", questionBankDTO.getFileType());
//        }
//        if (questionBankDTO.getFileUrl() != null && !questionBankDTO.getFileUrl().isEmpty()) {
//            queryWrapper.eq("file_url", questionBankDTO.getFileUrl());
//        }
//        if (questionBankDTO.getUploadTime() != null) {
//            queryWrapper.ge("upload_time", questionBankDTO.getUploadTime());
//        }
//        if (questionBankDTO.getCreatedAt() != null) {
//            queryWrapper.ge("created_at", questionBankDTO.getCreatedAt());
//        }
        queryWrapper.eq("is_deleted", false);

        return questionBankMapper.selectList(queryWrapper);
    }
}
