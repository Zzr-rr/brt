package com.zhuzirui.brt.service.impl;

import com.zhuzirui.brt.model.dto.QuestionDTO;
import com.zhuzirui.brt.service.QuestionExtractorService;

import com.zhuzirui.brt.utils.QuestionStructList;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.qianfan.QianfanChatModel;
import dev.langchain4j.service.AiServices;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;


@Service
public class QuestionExtractorServiceImpl implements QuestionExtractorService {
    @Value("${llm.qianfan.api-key}")
    private String qianfanApiKey;

    @Value("${llm.qianfan.api-secret}")
    private String qianfanApiSecret;

    @Value("${llm.qianfan.model-name}")
    private String qianfanModelName;

    private static final Logger logger = Logger.getLogger(QuestionExtractorServiceImpl.class.getName());

    @Override
    public List<QuestionDTO> extractQuestions(String text) throws Exception{

        interface  QuestionStructExtractor{
            QuestionStructList extractQuestionStructForm(String text);
        }

//openai
//        ChatLanguageModel chatModel = OpenAiChatModel.builder()
//            .apiKey(System.getenv("OPENAI_API_KEY"))
//            .modelName("gpt-4o-mini")
//            .responseFormat("json_schema")
//            .strictJsonSchema(true)
//            .logRequests(true)
//            .logResponses(true)
//            .build();

        QianfanChatModel qianfanChatModel = QianfanChatModel.builder()
                .apiKey(qianfanApiKey)
                .secretKey(qianfanApiSecret)
                .modelName(qianfanModelName)
                .responseFormat("json_schema")
                .logRequests(true)
                .logResponses(true)
                .build();

        QuestionStructExtractor questionStructsExtractor = AiServices.create(QuestionStructExtractor.class, qianfanChatModel);
        try {
            QuestionStructList questionStructList = questionStructsExtractor.extractQuestionStructForm(text);

            return questionStructList.toQuestionDTOList();
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
