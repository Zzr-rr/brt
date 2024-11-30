package com.zhuzirui.brt.service;


import com.zhuzirui.brt.model.dto.QuestionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionExtractorService{
    List<QuestionDTO> extractQuestions(String text) throws Exception;
}
