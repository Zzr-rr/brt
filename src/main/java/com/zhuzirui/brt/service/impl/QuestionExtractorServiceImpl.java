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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


@Service
public class QuestionExtractorServiceImpl implements QuestionExtractorService {
    @Value("${llm.qianfan.api-key}")
    private String qianfanApiKey;

    @Value("${llm.qianfan.api-secret}")
    private String qianfanApiSecret;

    @Value("${llm.qianfan.model-name}")
    private String qianfanModelName;

    @Value("${llm.max-input-length}")
    private int maxInputLength;

    @Value("${llm.openai.base-url}")
    private String openaiBaseUrl;

    @Value("${llm.openai.secret-key}")
    private String openaiSecretKey;

    @Value("${llm.openai.model-name}")
    private String openaiModelName;

    @Value("${llm.choosed-model}")
    private String choosedModel;

    public static List<String> splitTextIntoChunks(String text, int chunkSize) {
        List<String> splitTextList = new ArrayList<>();
        for (int i = 0; i < text.length(); i += chunkSize) {
            // 确保不会超出字符串的边界
            int endIndex = Math.min(i + chunkSize, text.length());
            splitTextList.add(text.substring(i, endIndex));
        }
        return splitTextList;
    }

    private static final Logger logger = Logger.getLogger(QuestionExtractorServiceImpl.class.getName());

    private ChatLanguageModel getChatModel(String modelName){
        logger.info("Trying to get Chat Language Model: " + modelName + " ...");
        switch (modelName){
            case "qianfan":
                QianfanChatModel qianfanChatModel = QianfanChatModel.builder()
                    .apiKey(qianfanApiKey)
                    .secretKey(qianfanApiSecret)
                    .modelName(qianfanModelName)
                    .responseFormat("json_schema")
                    .logRequests(true)
                    .logResponses(true)
                    .build();
                return qianfanChatModel;
            case "openai":
                ChatLanguageModel chatModel = OpenAiChatModel.builder()
                    .baseUrl(openaiBaseUrl)
                    .apiKey(openaiSecretKey)
                    .modelName(openaiModelName)
                    .responseFormat("json_schema")
                    .strictJsonSchema(true)
                    .logRequests(true)
                    .logResponses(true)
                    .build();
                return chatModel;
            default:
                return null;
        }
    }

    @Override
    public List<QuestionDTO> extractQuestions(String text) throws Exception{

        interface  QuestionStructExtractor{
            QuestionStructList extractQuestionStructForm(String text);
        }

        ChatLanguageModel chatModel = getChatModel(choosedModel);
        if(chatModel == null) throw new Exception("Can't get chat model");

        QuestionStructExtractor questionStructsExtractor = AiServices.create(QuestionStructExtractor.class, chatModel);

        //切割text
        List<String> splitTextList = splitTextIntoChunks(text, maxInputLength);

        QuestionStructList questionStructList = new QuestionStructList();
        for(String str : splitTextList) {
            try {
                QuestionStructList questionStructList1 = questionStructsExtractor.extractQuestionStructForm(str);
                logger.info(questionStructList1.toString());
                questionStructList.addAll(questionStructList1);
            } catch (Exception e) {
                logger.warning(e.getMessage());
                continue;
            }
        }

        return questionStructList.toQuestionDTOList();

//        // 创建一个线程安全的QuestionStructList
//        QuestionStructList questionStructList = new QuestionStructList();
//
//        // 创建一个固定大小的线程池
//        int numberOfThreads = 2;
//        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
//
//        // 用于收集结果的列表
//        List<Future<QuestionStructList>> futures = new ArrayList<>();
//
//        for (String str : splitTextList) {
//            // 对于splitTextList中的每个字符串，提交一个任务到线程池
//            Future<QuestionStructList> future = executor.submit(() -> {
//                try {
//                    // 提取QuestionStruct并转换为QuestionDTOList
//                    return questionStructsExtractor.extractQuestionStructForm(str);
//                } catch (Exception e) {
//                    logger.warning(e.getMessage());
//                    return null; // 或者你可以选择返回一个空的QuestionDTOList
//                }
//            });
//            futures.add(future);
//        }
//
//        // 关闭ExecutorService，不再接受新任务
//        executor.shutdown();
//
//        // 等待所有任务完成
//        for (Future<QuestionStructList> future : futures) {
//            try {
//                // 获取每个任务的结果
//                QuestionStructList dtoList = future.get();
//                if (dtoList != null) {
//                    // 将结果添加到questionStructList中
//                    questionStructList.addAll(dtoList);
//                }
//            } catch (Exception e) {
//                logger.warning("Error getting result from future: " + e.getMessage());
//            }
//        }
//
//        // 等待线程池中的任务全部完成，再继续执行后面的代码
//        try {
//            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
//                executor.shutdownNow(); // 取消当前执行的任务
//            }
//        } catch (InterruptedException ex) {
//            executor.shutdownNow();
//            Thread.currentThread().interrupt(); // 重新设置中断状态
//        }
//
//        // 此时questionStructList包含了所有线程的结果
//        return questionStructList.toQuestionDTOList();
    }

}
