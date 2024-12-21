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
import org.apache.logging.log4j.Logger;
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
    private List<String> allowedExtensions;

    @Value("${files.max-size}")
    private int maxFileSize;

    //上传文件
    @PostMapping("/create")
    public Result<FileDTO> create(@RequestBody FileDTO fileDTO,HttpServletRequest request) {
        System.out.println(fileDTO);

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

        if(fileDTO.getFileName() == null || fileDTO.getFileName().isEmpty())
            return Result.error(401, "Invalid file name");
        if(fileDTO.getFileType() == null || fileDTO.getFileType().isEmpty())
            return Result.error(401, "Invalid file type");
        if(fileDTO.getKeywords() == null || fileDTO.getKeywords().isEmpty())
            return Result.error(401, "Invalid keywords");
        if(fileDTO.getFileUrl() == null || fileDTO.getFileUrl().isEmpty())
            return Result.error(401, "Invalid file url");
        fileDTO.setIsDeleted(false);
        fileDTO.setIsDeleted(false);

        File file = fileStructMapper.dtoToEntity(fileDTO);

        boolean result = fileService.saveFile(file);

        fileDTO = fileStructMapper.entityToDto(file);
        if(result) return Result.success(fileDTO);
        else return Result.error(500, "Failed to save file");
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
            result = fileUploadService.deleteFile(fileUrl);
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
