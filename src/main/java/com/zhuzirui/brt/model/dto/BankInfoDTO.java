package com.zhuzirui.brt.model.dto;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhuzirui.brt.dao.QuestionBankMapper;
import com.zhuzirui.brt.model.entity.QuestionBank;
import lombok.Data;
import org.apache.ibatis.session.ResultHandler;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
public class BankInfoDTO {
    private Integer bankId;
    private String bankName;
    private Integer totalNum;
    private Integer correctNum;
    private String keywords;
}
