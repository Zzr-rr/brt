package com.zhuzirui.brt.service.impl;

import com.zhuzirui.brt.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadServiceLocalImpl implements FileUploadService {

    //文件磁盘路径
    @Value("${files.upload.base-path}")
    private String fileUploadPath;

    //文件baseUrl
    @Value("${files.download.base-url}")
    private String downloadBaseUrl;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        //获取文件的类型
        String type = file.getContentType();

        //获取文件
        File uploadParentFile = new File(fileUploadPath);
        //判断文件目录是否存在
        if(!uploadParentFile.exists()) {
            //如果不存在就创建文件夹
            uploadParentFile.mkdirs();
        }
        //定义一个文件唯一标识码（UUID）
        String uuid = UUID.randomUUID().toString();

        File uploadFile = new File(fileUploadPath + uuid + "." + type);
        //将临时文件转存到指定磁盘位置
        file.transferTo(uploadFile);

        return downloadBaseUrl + uuid + "." + type;
    }
}
