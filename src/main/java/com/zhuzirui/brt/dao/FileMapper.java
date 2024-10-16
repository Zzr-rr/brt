package com.zhuzirui.brt.dao;

import com.zhuzirui.brt.model.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文件管理表，存储用户上传文件的信息 Mapper 接口
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {

}
