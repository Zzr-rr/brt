package com.zhuzirui.brt.dao;

import com.zhuzirui.brt.model.dto.BankInfoDTO;
import com.zhuzirui.brt.model.entity.QuestionBank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 题库信息表，存储题库信息 Mapper 接口
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Mapper
public interface QuestionBankMapper extends BaseMapper<QuestionBank> {
    @Select("SELECT COUNT(*) FROM question WHERE bank_id = #{bank_id} AND is_deleted = 0")
    Integer getQuestionNums(@Param("bank_id")Integer bankId);

    @Select("SELECT COUNT(DISTINCT q.question_id) " +
        "FROM user_question_progress uqp " +
        "JOIN question q ON uqp.question_id = q.question_id " +
        "WHERE q.bank_id = #{bank_id} " +
        "AND uqp.user_id = #{user_id} " +
        "AND uqp.is_correct = 1 " +
        "AND uqp.is_deleted = 0 " +
        "AND q.is_deleted = 0 " )
    Integer getCorrectNums(@Param("bank_id")Integer bankId, @Param("user_id")Integer userId);

    @Select({"SELECT \n" +
            "    qb.bank_id, \n" +
            "    qb.title AS bank_name, \n" +
            "    COUNT(DISTINCT q.question_id) AS total_num,\n" +
            "    COUNT(DISTINCT CASE WHEN uqp.is_correct = 1 THEN q.question_id END) AS correct_num,\n" +
            "    qb.keywords\n" +
            "FROM \n" +
            "    question q\n" +
            "LEFT JOIN \n" +
            "    user_question_progress uqp ON q.question_id = uqp.question_id\n" +
            "LEFT JOIN \n" +
            "    question_bank qb ON q.bank_id = qb.bank_id\n" +
            "WHERE \n" +
            "    q.is_deleted = 0 AND uqp.is_deleted = 0 AND uqp.user_id = #{user_id}\n" +
            "GROUP BY \n" +
            "    qb.bank_id"})
        List<BankInfoDTO> getBankInfoByUserId(@Param("user_id") Integer userId);

}
