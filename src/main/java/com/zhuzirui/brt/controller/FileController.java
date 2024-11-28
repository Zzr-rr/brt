package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.dao.FileMapper;
import com.zhuzirui.brt.mapper.FileStructMapper;
import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.entity.File;
import com.zhuzirui.brt.service.FileService;
import com.zhuzirui.brt.service.FileUploadService;
import com.zhuzirui.brt.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.zhuzirui.brt.utils.JwtUtil.getJwtTokenFromCookie;

/**
 * <p>
 * 文件管理表，存储用户上传文件的信息 前端控制器
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@RestController
@RequestMapping("/brt/file")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    FileStructMapper fileStructMapper;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/create")
    public Result<FileDTO> create(@RequestBody FileDTO fileDTO, @RequestPart("file") MultipartFile multipartFile, HttpServletRequest request) {
        // 调用方法从 Cookie 中获取 JWT Token
        String jwtToken = getJwtTokenFromCookie(request);

        // 检查 JWT Token 是否存在
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            Integer userId = jwtUtil.extractUserId(jwtToken);
            // 将用户 ID 设置到 FileDTO 中
            fileDTO.setUserId(userId);
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "User ID cannot be determined from JWT");
        }

        String fileUrl = null;
        // 保存文件到本地存储
        if (multipartFile.isEmpty()) {
            return Result.error(400, "File is empty");
        }
        try {
            //保存并返回url
            fileUrl = fileUploadService.uploadFile(multipartFile);
            fileDTO.setFileUrl(fileUrl); // 设置文件URL

            //设置时间
            fileDTO.setCreatedAt(LocalDateTime.now());
            fileDTO.setUpdatedAt(LocalDateTime.now());
            fileDTO.setUploadTime(LocalDateTime.now());

            //设置属性
            fileDTO.setIsPublic(false);
            fileDTO.setIsDeleted(false);
        } catch (IOException e) {
            return Result.error(500, "Failed to save file");
        }

        File file = fileStructMapper.dtoToEntity(fileDTO);

        boolean result = fileService.saveFile(file);

        if(result) return Result.success(fileDTO);
        else return Result.error(500, "Failed to save file");
    }


    @PostMapping("/delete")
    public Result<Boolean> delete() {
        return null;
    }
    @PostMapping("/update")
    public Result<Boolean> update() {
        return null;
    }
    @PostMapping("/list")
    public Result<Boolean> list() {
        return null;
    }
    @PostMapping("/generate")
    public Result<Boolean> generate() {
        return null;
    }
}
