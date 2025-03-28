package com.zhuzirui.brt.service;

import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuzirui.brt.model.entity.Question;
import com.zhuzirui.brt.model.entity.QuestionBank;

import java.util.List;

/**
 * <p>
 * 文件管理表，存储用户上传文件的信息 服务类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
public interface FileService extends IService<File> {
    boolean saveFile(File file);
    void deleteFile(File file);
    List<File> listFiles(FileDTO fileDTO);
    File getFileByFileId(Integer fileId);
    File getFileByUrlFileName(String urlFileName);
    void updateFile(FileDTO fileDTO);
    java.io.File getFileContentByFileId(Integer fileId);
}
