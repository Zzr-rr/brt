package com.zhuzirui.brt.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * <p>
 * 文件上传服务
 * </p>
 *
 * @author 666xz666
 * @since 2024-11-28
 */
@Service
public interface FileUploadService {
    public String uploadFile(MultipartFile file) throws IOException;
}
