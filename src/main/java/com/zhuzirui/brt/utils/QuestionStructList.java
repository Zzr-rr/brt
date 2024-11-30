package com.zhuzirui.brt.utils;

import com.google.gson.Gson;
import com.zhuzirui.brt.model.dto.QuestionDTO;
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

    //转换
    public QuestionDTO toQuestionDTO(){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionText(questionText);
        questionDTO.setQuestionType(questionType.toString());
        questionDTO.setDifficulty(difficulty.toString());

        String correctAnswer = "";
        if(questionType == QuestionType.SINGLE_CHOICE || questionType == QuestionType.MULTIPLE_CHOICE){
            //选择题

            questionDTO.setOptions(gson.toJson(options));
            //将正确答案标号作为正确答案
            for(int i=0; i<options.size(); i++) {
                Option option = options.get(i);
                if (option.isCorrect) {
                    if(correctAnswer != "") correctAnswer += ',';
                    correctAnswer += (char) ('A' + i);
                }
            }

            questionDTO.setCorrectAnswer(correctAnswer);
        }
        else if(questionType == QuestionType.FILL_IN_THE_BLANK){
            //填空题

            for(int i=0; i < blankAnswers.size(); i++) {
                if(correctAnswer != "") correctAnswer += ',';
                String blankAnswer = blankAnswers.get(i);
                String modifiedAnswer = blankAnswer.replace(',','，');
                correctAnswer += modifiedAnswer;
            }
        }
        else{
            //简答题

            questionDTO.setOptions("");//不需要
            questionDTO.setCorrectAnswer(this.correctAnswer);
        }

        questionDTO.setIsDeleted(false);
        questionDTO.setCreatedAt(LocalDateTime.now());
        questionDTO.setUpdatedAt(LocalDateTime.now());

        return questionDTO;
    }
}

@Data
public class QuestionStructList{
    private List<QuestionStruct> questionStructList;

    public List<QuestionDTO> toQuestionDTOList(){
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(QuestionStruct questionStruct : questionStructList){
            questionDTOList.add(questionStruct.toQuestionDTO());
        }
        return questionDTOList;
    }
}
