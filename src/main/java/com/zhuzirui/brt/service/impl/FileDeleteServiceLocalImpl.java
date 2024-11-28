package com.zhuzirui.brt.service.impl;

import com.zhuzirui.brt.service.FileDeleteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileDeleteServiceLocalImpl implements FileDeleteService {
    //文件磁盘路径
    @Value("${files.upload.base-path}")
    private String fileUploadPath;

    @Override
    public boolean deleteFile(String fileUrl) throws IOException {

        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        String filePath = fileUploadPath + fileName;

        File file = new File(filePath);
        if(!file.exists()) return true;
        return file.delete();
    }
}
