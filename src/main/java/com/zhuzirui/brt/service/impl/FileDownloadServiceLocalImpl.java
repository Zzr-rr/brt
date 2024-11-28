package com.zhuzirui.brt.service.impl;

import com.zhuzirui.brt.service.FlieDownloadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileDownloadServiceLocalImpl implements FlieDownloadService {

    //文件磁盘路径
    @Value("${files.upload.base-path}")
    private String fileUploadPath;

    @Override
    public File downloadFile(String fileName) throws IOException {
        File result = null;
        String filePath = fileUploadPath + fileName;
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            return file;
        }
        // 如果文件不存在，可以抛出一个自定义异常或者返回null
        return null;
    }
}
