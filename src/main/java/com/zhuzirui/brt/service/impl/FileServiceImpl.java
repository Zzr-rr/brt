package com.zhuzirui.brt.service.impl;

import ch.qos.logback.core.util.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.entity.File;
import com.zhuzirui.brt.dao.FileMapper;
import com.zhuzirui.brt.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuzirui.brt.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

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

    private static final Logger logger = Logger.getLogger(FileServiceImpl.class.getName());

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileUploadService fileUploadService;

    //文件baseUrl
    @Value("${files.download.base-url}")
    private String downloadBaseUrl;

    @Override
    public boolean saveFile(File file) {
        int result = this.getBaseMapper().insert(file);
        if (result < 1) {
            return false;
        }
        return true;
    }

    @Override
    public void deleteFile(File file) {
        file.setIsDeleted(true);
        file.setUpdatedAt(LocalDateTime.now());
        this.getBaseMapper().updateById(file);
    }

    @Override
    public List<File> listFiles(FileDTO fileDTO) {
        logger.info("in listFiles:\n"+fileDTO.toString());

        QueryWrapper<File> queryWrapper = new QueryWrapper<File>();

        // 检查DTO中的属性是否非空，并添加到查询条件中
        if(fileDTO.getUserId()!=null){
            queryWrapper.eq("user_id", fileDTO.getUserId());
        }
        if (fileDTO.getFileId() != null) {
            queryWrapper.eq("file_id", fileDTO.getFileId());
        }
        if (fileDTO.getFileName() != null && !fileDTO.getFileName().isEmpty()) {
            queryWrapper.like("file_name", fileDTO.getFileName());
        }
        if (fileDTO.getIsPublic() != null) {
            queryWrapper.eq("is_public", fileDTO.getIsPublic());
        }
        if (fileDTO.getKeywords() != null && !fileDTO.getKeywords().isEmpty()) {
            queryWrapper.like("keywords", fileDTO.getKeywords());
        }
        if (fileDTO.getUpdatedAt() != null) {
            queryWrapper.le("updated_at", fileDTO.getUpdatedAt());
        }
        if (fileDTO.getFileType() != null && !fileDTO.getFileType().isEmpty()) {
            queryWrapper.eq("file_type", fileDTO.getFileType());
        }
        if (fileDTO.getFileUrl() != null && !fileDTO.getFileUrl().isEmpty()) {
            queryWrapper.eq("file_url", fileDTO.getFileUrl());
        }
        if (fileDTO.getUploadTime() != null) {
            queryWrapper.ge("upload_time", fileDTO.getUploadTime());
        }
        if (fileDTO.getCreatedAt() != null) {
            queryWrapper.ge("created_at", fileDTO.getCreatedAt());
        }
        queryWrapper.eq("is_deleted", false);

        return fileMapper.selectList(queryWrapper);
    }

    @Override
    public File getFileByFileId(Integer fileId) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<File>();
        queryWrapper.eq("file_id", fileId);
        return fileMapper.selectOne(queryWrapper);
    }


    //可修改文件的fileName，isPublic，keywords
    @Override
    public void updateFile(FileDTO fileDTO) {
        Integer fileId = fileDTO.getFileId();
        QueryWrapper<File> queryWrapper = new QueryWrapper<File>();
        queryWrapper.eq("file_id", fileId);
        File file = fileMapper.selectOne(queryWrapper);
        if (file != null) {
            file.setUpdatedAt(LocalDateTime.now());

            // 更新文件信息
            if (fileDTO.getFileName() != null && !fileDTO.getFileName().equals("")) {
                file.setFileName(fileDTO.getFileName());
            }
            if (fileDTO.getIsPublic() != null) {
                file.setIsPublic(fileDTO.getIsPublic());
            }
            if (fileDTO.getKeywords() != null) {
                file.setKeywords(fileDTO.getKeywords());
            }

            fileMapper.updateById(file);
        }

    }

    @Override
    public File getFileByUrlFileName(String urlFileName) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<File>();
        String fileUrl = downloadBaseUrl + urlFileName;
        queryWrapper.eq("file_url", fileUrl);
        return fileMapper.selectOne(queryWrapper);
    }

    @Override
    public java.io.File getFileContentByFileId(Integer fileId) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<File>();
        queryWrapper.eq("file_id", fileId);
        File file = fileMapper.selectOne(queryWrapper);
        String fileUrl = file.getFileUrl();
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        try {
            return fileUploadService.downloadFile(fileName, "content");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
