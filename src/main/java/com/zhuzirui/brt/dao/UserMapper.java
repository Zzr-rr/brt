package com.zhuzirui.brt.dao;

import com.zhuzirui.brt.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
