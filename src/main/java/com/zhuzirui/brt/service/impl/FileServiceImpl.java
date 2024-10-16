package com.zhuzirui.brt.service.impl;

import com.zhuzirui.brt.model.entity.File;
import com.zhuzirui.brt.dao.FileMapper;
import com.zhuzirui.brt.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件管理表，存储用户上传文件的信息 服务实现类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

}
