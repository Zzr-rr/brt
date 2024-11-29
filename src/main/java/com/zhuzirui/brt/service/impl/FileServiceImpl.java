package com.zhuzirui.brt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.entity.File;
import com.zhuzirui.brt.dao.FileMapper;
import com.zhuzirui.brt.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private FileMapper fileMapper;

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
        Integer fileId = file.getFileId();
        QueryWrapper<File> queryWrapper = new QueryWrapper<File>();
        queryWrapper.eq("file_id", fileId);
        fileMapper.delete(queryWrapper);
    }

    @Override
    public List<File> listFiles(Integer userId, FileDTO fileDTO) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<File>();
        queryWrapper.eq("user_id", userId); // 总是添加用户ID条件

        // 检查DTO中的属性是否非空，并添加到查询条件中
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
        if (fileDTO.getIsDeleted() != null) {
            queryWrapper.eq("is_deleted", fileDTO.getIsDeleted());
        }

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
    public boolean belongsToUser(Integer fileId, Integer userId) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<File>();
        queryWrapper.eq("file_id", fileId);
        File file = fileMapper.selectOne(queryWrapper);
        if (file == null) return false;
        return userId.equals(file.getUserId());
    }

    @Override
    public File getFileByUrlFileName(String urlFileName) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<File>();
        String fileUrl = downloadBaseUrl + urlFileName;
        queryWrapper.eq("file_url", fileUrl);
        return fileMapper.selectOne(queryWrapper);
    }
}
