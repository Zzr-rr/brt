package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.dao.DownloadHistoryMapper;
import com.zhuzirui.brt.mapper.DownloadHistoryStructMapper;
import com.zhuzirui.brt.mapper.FileStructMapper;
import com.zhuzirui.brt.model.dto.DownloadHistoryDTO;
import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.entity.DownloadHistory;
import com.zhuzirui.brt.model.entity.File;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.service.*;
import com.zhuzirui.brt.utils.JwtUtil;
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

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
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
    QuestionExtractorService questionExtractorService;

    @Autowired
    FileService fileService;

    @Autowired
    FileUploadService fileUploadService;


    @Autowired
    FileDeleteService fileDeleteService;

    @Autowired
    FileStructMapper fileStructMapper;

    @Autowired
    DownloadHistoryService downloadHistoryService;

    @Autowired
    DownloadHistoryMapper downloadHistoryMapper;

    @Autowired
    DownloadHistoryStructMapper downloadHistoryStructMapper;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Value("#{'${files.allowed-extensions}'.split(',')}")
    private List<String> allowedExtension;

    @Value("${files.max-size}")
    private int maxFileSize;

    //上传文件
    @PostMapping("/create")
    public Result<FileDTO> create(@Validated FileDTO fileDTO, @RequestPart("file") MultipartFile multipartFile, HttpServletRequest request) {
        // 调用方法从 Cookie 中获取 JWT Token
        String jwtToken = getJwtTokenFromCookie(request);
        // 检查 JWT Token 是否存在
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            Integer userId = jwtUtil.extractUserId(jwtToken);
            User user = userService.getById(userId);
            if(user == null) return Result.error(404, "User not found");

            // 将用户 ID 设置到 FileDTO 中
            fileDTO.setUserId(userId);
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "Invalid JWT token");
        }

        //判断文件是否符合要求
        //判断文件是否为空
        if (multipartFile.isEmpty()) {
                return Result.error(400, "File is empty");
        }
        //判断文件大小
        if (multipartFile.getSize() > maxFileSize) {
            return Result.error(400, "File size exceeds limit");
        }
        //判断文件类型
        String fileName = multipartFile.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!allowedExtension.contains(extension)) {
            return Result.error(400, "Invalid file type: ."+extension+" not allowed");
        }

        String fileUrl = null;
        // 保存文件到本地存储
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
    public ResponseEntity<Resource> download(@PathVariable String fileName,
                                             HttpServletRequest request,
                                             HttpServletResponse response)  {
        try{
            java.io.File file = fileUploadService.downloadFile(fileName);
            if (file == null || !file.exists()) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(file.toURI());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            //添加下载记录
            DownloadHistoryDTO downloadHistoryDTO = new DownloadHistoryDTO();

            //查找文件表
            File fileFoundByUrl = fileService.getFileByUrlFileName(fileName);
            if(fileFoundByUrl == null || fileFoundByUrl.getIsDeleted()) return ResponseEntity.notFound().build();
            downloadHistoryDTO.setFileId(fileFoundByUrl.getFileId());

            // 调用方法从 Cookie 中获取 JWT Token
            String jwtToken = getJwtTokenFromCookie(request);
            // 检查 JWT Token 是否存在
            if (jwtToken != null && !jwtToken.isEmpty()) {
                // 使用 JwtUtil 提取用户 ID
                Integer userId = jwtUtil.extractUserId(jwtToken);
                User user = userService.getById(userId);

                if(user == null) return ResponseEntity.badRequest().build();

                // 将用户 ID 设置到 downloadHistoryDTO 中
                downloadHistoryDTO.setUserId(userId);
            } else {
                // 如果没有 JWT Token，返回错误信息
                return ResponseEntity.badRequest().build();
            }

            downloadHistoryDTO.setIsDeleted(false);
            downloadHistoryDTO.setDownloadTime(LocalDateTime.now());
            DownloadHistory downloadHistory = downloadHistoryStructMapper.dtoToEntity(downloadHistoryDTO);

            try {
                downloadHistoryService.saveDownloadHistory(downloadHistory);
            }catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }

            // 设置Content-Type，这里可以根据文件类型来设置
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            // 设置Content-Disposition，这将告诉浏览器这是一个文件下载响应
            HttpHeaders headers = new HttpHeaders();
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }

    //根据fileId删除文件
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody FileDTO fileDTO, HttpServletRequest request) {
        File file = fileService.getById(fileDTO.getFileId());
        if(file == null) return Result.error(200, "File not found");

        //鉴权
        // 调用方法从 Cookie 中获取 JWT Token
        String jwtToken = getJwtTokenFromCookie(request);
        Integer userId;
        // 检查 JWT Token 是否存在
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            userId = jwtUtil.extractUserId(jwtToken);
            User user = userService.getUserByUserId(userId);
            if(user == null) return Result.error(404, "User not found");
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "Invalid JWT token");
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
            if(user == null) return Result.error(404, "User not found");
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "Invalid JWT token");
        }

        //验证文件归属
        Integer fileId = fileDTO.getFileId();
        File file = fileService.getFileByFileId(fileId);
        if(file == null) return Result.error(404, "File not found");
        if(!file.getUserId().equals(userId)) return Result.error(401, "Access denied");

        if(fileDTO.getIsPublic()==null && fileDTO.getFileName()==null && fileDTO.getKeywords()==null)
            return Result.error(200, "Nothing to update");

        fileService.updateFile(fileDTO);
        return Result.success(true);
    }


    //根据指定条件筛选
    @PostMapping("/list/{flag}")
    public Result<List<File>> list(@RequestBody FileDTO fileDTO,HttpServletRequest request,@PathVariable String flag) {
        //判断查询类型
        boolean isPublic;
        if(flag.equals("public")) isPublic = true;
        else if(flag.equals("personal")) isPublic = false;
        else return Result.error(401, "Invalid flag");

        //鉴权
        String jwtToken = getJwtTokenFromCookie(request);// 调用方法从 Cookie 中获取 JWT Token
        Integer userId;
        // 检查 JWT Token 是否存在
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            userId = jwtUtil.extractUserId(jwtToken);
            User user = userService.getUserByUserId(userId);
            if(user == null && !isPublic) return Result.error(404, "User not found");

            //个人查询
            if(user != null && !isPublic) fileDTO.setUserId(userId);

            //公共查询
            if(isPublic) fileDTO.setIsPublic(true);
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "Invalid JWT token");
        }

        return Result.success(fileService.listFiles(fileDTO));
    }

}
