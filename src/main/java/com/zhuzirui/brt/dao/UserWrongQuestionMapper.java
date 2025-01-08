package com.zhuzirui.brt.dao;

import com.zhuzirui.brt.model.dto.WrongInfoDTO;
import com.zhuzirui.brt.model.entity.UserWrongQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 错题本表，记录用户的错题和复习状态 Mapper 接口
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Mapper
public interface UserWrongQuestionMapper extends BaseMapper<UserWrongQuestion> {

    @Select({
       "SELECT \n" +
       "    uwq.wrong_id, \n" +
       "    uwq.user_id, \n" +
       "    uwq.question_id, \n" +
       "    uwq.added_at, \n" +
       "    uwq.review_status, \n" +
       "    uwq.is_deleted,\n" +
       "    q.question_text, \n" +
       "    q.difficulty \n" +
       "FROM \n" +
       "    user_wrong_question uwq, question q \n" +
       "WHERE \n" +
       "uwq.is_deleted = 0 AND q.is_deleted = 0 AND uwq.user_id = #{user_id} AND uwq.question_id = q.question_id;"
    })
    public List<WrongInfoDTO> listWrongInfo(@Param("user_id") Integer userId);
}
