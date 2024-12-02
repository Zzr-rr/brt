package com.zhuzirui.brt.utils;

import com.google.gson.Gson;
import com.zhuzirui.brt.model.dto.QuestionDTO;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Description;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

enum Difficulty{
        EASY,
        MEDIUM,
        HARD
    }

enum QuestionType{
    SINGLE_CHOICE,
    MULTIPLE_CHOICE,
    FILL_IN_THE_BLANK,
    SHORT_ANSWER
}

@Data
class Option{
    String content;
    boolean isCorrect;
}

@Component
@Setter
@Getter
@Data
@Description("A question extracted from text.")
class QuestionStruct{
    private final static Logger log = Logger.getLogger(QuestionStruct.class.getName());

    private static final Gson gson = new Gson();

    @Description("The problem stems from this question.")
    private String questionText;

    @Description("type of the Question. Can only from 'SINGLE_CHOICE', 'MULTIPLE_CHOICE', 'FILL_IN_THE_BLANK', 'SHORT_ANSWER'")
    private QuestionType questionType;

    @Description("options' json format. Not empty only when questionType are 'SINGLE_CHOICE' or 'MULTIPLE_CHOICE', for example, \"[\"A.科技\", \"B.创新\", \"C.人工智能\", \"D.new tag\"] ")
    private List<Option> options;

    @Description("every answer to fill in the blanks. Not empty only when questionType are 'FILL_IN_THE_BLANK', DO NOT INCLUDE ','")
    private List<String> blankAnswers;

    @Description("the correct answer. Not empty only when questionType are 'SHORT_ANSWER'")
    private String correctAnswer;

    @Description("difficulty of the Question. Can only from 'EASY', 'MEDIUM', 'HARD'")
    private Difficulty difficulty;

    private List<String> getOptionList(){
        List<String> list = new ArrayList<>();
        for(Option option : options){
            list.add(option.content);
        }
        return list;
    }

    //转换
    public QuestionDTO toQuestionDTO() throws Exception{

        if(questionText == null || questionText.isEmpty()) throw new Exception("questionText is empty");

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionText(questionText);
        questionDTO.setQuestionType(questionType.toString());
        questionDTO.setDifficulty(difficulty.toString());


        if(questionType == QuestionType.SINGLE_CHOICE || questionType == QuestionType.MULTIPLE_CHOICE){
            //选择题

            if(options == null || options.isEmpty()) throw new NullPointerException("SINGLE_CHOICE / MULTIPLE_CHOICE: options is empty.");

            questionDTO.setOptions(gson.toJson(getOptionList()));

            questionDTO.setCorrectAnswer(gson.toJson(options));
        }
        else if(questionType == QuestionType.FILL_IN_THE_BLANK){
            //填空题

            String noUse = gson.toJson("not used");
            questionDTO.setOptions(noUse);//不需要

            if(blankAnswers == null || blankAnswers.isEmpty()) throw new NullPointerException("FILL_IN_THE_BLANK: blankAnswers is empty.");

            correctAnswer = gson.toJson(blankAnswers);
        }
        else{
            //简答题

            if(this.correctAnswer == null || this.correctAnswer.isEmpty()) throw new NullPointerException("SHORT_ANSWER: correctAnswer is empty.");
            String noUse = gson.toJson("not used");
            questionDTO.setOptions(noUse);//不需要
            questionDTO.setCorrectAnswer(gson.toJson(this.correctAnswer));
        }

        questionDTO.setIsDeleted(false);
//        questionDTO.setCreatedAt(LocalDateTime.now());
//        questionDTO.setUpdatedAt(LocalDateTime.now());

        return questionDTO;
    }
}

@Data
public class QuestionStructList{
    private List<QuestionStruct> questionStructList;

    public QuestionStructList(){
        questionStructList = new ArrayList<>();
    }

    private static final Logger logger = Logger.getLogger(QuestionStructList.class.getName());

    public List<QuestionDTO> toQuestionDTOList(){
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        logger.info("size of question struct list: " + questionStructList.size());

        for(QuestionStruct questionStruct : questionStructList){
            try {
                QuestionDTO questionDTO = questionStruct.toQuestionDTO();
                questionDTOList.add(questionDTO);
            }catch(Exception e){
                logger.info("Generating one question struct failed.\n"+e.getMessage());
                continue;
            }
        }

        logger.info("success:"+questionDTOList.size());
        return questionDTOList;
    }

    public void addAll(QuestionStructList questionStructList1){
        this.questionStructList.addAll(questionStructList1.questionStructList);
    }
}
