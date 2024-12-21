package com.zhuzirui.brt.service.impl;

import com.zhuzirui.brt.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@Service
public class FileUploadServiceLocalImpl implements FileUploadService {

    //文件磁盘路径
    @Value("${files.upload.base-path}")
    private String fileUploadPath;

    //文件baseUrl
    @Value("${files.download.base-url}")
    private String fileDownloadBaseUrl;

    //图片磁盘路径
    @Value("${images.upload.base-path}")
    private String imageUploadPath;

    //图片baseUrl
    @Value("${images.download.base-url}")
    private String imageDownloadBaseUrl;

    @Override
    public String uploadFile(MultipartFile file,String type) throws IOException {
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        //获取文件的类型

        //获取文件
        File uploadParentFile = new File(fileUploadPath);
        //判断文件目录是否存在
        if(!uploadParentFile.exists()) {
            //如果不存在就创建文件夹
            uploadParentFile.mkdirs();
        }
        //定义一个文件唯一标识码（UUID）
        String uuid = UUID.randomUUID().toString();
        String basePath = (type.equals("image")? imageUploadPath : fileUploadPath);
        String baseUrl = (type.equals("image")? imageDownloadBaseUrl : fileDownloadBaseUrl);
        File uploadFile = new File(basePath + uuid + "_" + originalFilename);
        //将临时文件转存到指定磁盘位置
        file.transferTo(uploadFile);

        return baseUrl + uuid + "_" + originalFilename;
    }

    @Override
    public File downloadFile(String fileName, String type) {
        String basePath = (type.equals("image")? imageUploadPath : fileUploadPath);
        String filePath = basePath + fileName;
        return new File(filePath);
    }


    @Override
    public boolean deleteFile(String fileUrl) throws IOException {

        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        String filePath = fileUploadPath + fileName;

        File file = new File(filePath);
        if(!file.exists()) return true;
        return file.delete();
    }
}
