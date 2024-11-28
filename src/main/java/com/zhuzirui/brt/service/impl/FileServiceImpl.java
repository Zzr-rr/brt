package com.zhuzirui.brt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.entity.File;
import com.zhuzirui.brt.dao.FileMapper;
import com.zhuzirui.brt.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<File> listFiles(Integer userId,FileDTO fileDTO) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<File>();
        queryWrapper.eq("user_id", userId);
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
}
