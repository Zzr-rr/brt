package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.dao.FileMapper;
import com.zhuzirui.brt.mapper.FileStructMapper;
import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.entity.File;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.service.FileDeleteService;
import com.zhuzirui.brt.service.FileService;
import com.zhuzirui.brt.service.FileUploadService;
import com.zhuzirui.brt.service.UserService;
import com.zhuzirui.brt.service.impl.FileDownloadServiceLocalImpl;
import com.zhuzirui.brt.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
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
    FileDownloadServiceLocalImpl fileDownloadService;

    @Autowired
    FileDeleteService fileDeleteService;

    @Autowired
    FileStructMapper fileStructMapper;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    //上传文件
    @PostMapping("/create")
    public Result<FileDTO> create(@Validated FileDTO fileDTO, @RequestPart("file") MultipartFile multipartFile, HttpServletRequest request) {
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
            e.printStackTrace();
            return Result.error(500, "Failed to save file.");
        }

        File file = fileStructMapper.dtoToEntity(fileDTO);

        boolean result = fileService.saveFile(file);

        fileDTO = fileStructMapper.entityToDto(file);
        if(result) return Result.success(fileDTO);
        else return Result.error(500, "Failed to save file");
    }



    //文件下载本地接口
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        java.io.File file = fileDownloadService.downloadFile(fileName);
        if (file == null || !file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(file.toURI());
        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        // 设置Content-Type，这里可以根据文件类型来设置
        String contentType = Files.probeContentType(file.toPath());
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        // 设置Content-Disposition，这将告诉浏览器这是一个文件下载响应
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    //根据fileId删除文件
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody FileDTO fileDTO, HttpServletRequest request) {
        File file = fileService.getById(fileDTO.getFileId());
        if(file == null) return Result.error(200, "File not found");

        //鉴权
        String jwtToken = getJwtTokenFromCookie(request);// 调用方法从 Cookie 中获取 JWT Token
        Integer userId;
        // 检查 JWT Token 是否存在
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            userId = jwtUtil.extractUserId(jwtToken);
            User user = userService.getUserByUserId(userId);
            if(user == null) return Result.error(401, "User ID cannot be determined from JWT");
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "User ID cannot be determined from JWT");
        }
        if(!userId.equals(file.getUserId())) {
            return Result.error(401, "Access denied");
        }

        //删除文件本地实现
        String fileUrl = file.getFileUrl();
        boolean result = false;
        try{
            result = fileDeleteService.deleteFile(fileUrl);
        }catch (IOException e){
            e.printStackTrace();
            return Result.error(500, "Failed to delete file");
        }
        if(result){
            //删除对应文件表行
            fileService.deleteFile(file);
            return Result.success(true);
        }
        else return Result.error(500, "Failed to delete file");
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody FileDTO fileDTO, HttpServletRequest request) {
        //鉴权
        String jwtToken = getJwtTokenFromCookie(request);// 调用方法从 Cookie 中获取 JWT Token
        Integer userId;
        // 检查 JWT Token 是否存在
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            userId = jwtUtil.extractUserId(jwtToken);
            User user = userService.getUserByUserId(userId);
            if(user == null) return Result.error(401, "User ID cannot be determined from JWT");
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "User ID cannot be determined from JWT");
        }

        //验证文件归属
        Integer fileId = fileDTO.getFileId();
        File file = fileService.getFileByFileId(fileId);
        if(file == null) return Result.error(404, "File not found");
        if(!file.getUserId().equals(userId)) return Result.error(401, "Access denied");

        if(fileDTO.getIsPublic()==null && fileDTO.getFileName()==null && fileDTO.getKeywords()==null)
            return Result.error(500, "Nothing to update");

        fileService.updateFile(fileDTO);
        return Result.success(true);
    }

    @PostMapping("/list")
    public Result<List<File>> list(@Validated FileDTO fileDTO,HttpServletRequest request) {
        //鉴权
        String jwtToken = getJwtTokenFromCookie(request);// 调用方法从 Cookie 中获取 JWT Token
        Integer userId;
        // 检查 JWT Token 是否存在
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            userId = jwtUtil.extractUserId(jwtToken);
            User user = userService.getUserByUserId(userId);
            if(user == null) return Result.error(401, "User ID cannot be determined from JWT");

        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "User ID cannot be determined from JWT");
        }

        return Result.success(fileService.listFiles(userId,fileDTO));
    }

    @PostMapping("/generate")
    public Result<Boolean> generate() {
        return null;
    }

}
