package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.mapper.DownloadHistoryStructMapper;
import com.zhuzirui.brt.model.dto.DownloadHistoryDTO;
import com.zhuzirui.brt.model.entity.DownloadHistory;
import com.zhuzirui.brt.model.entity.File;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.service.DownloadHistoryService;
import com.zhuzirui.brt.service.FileService;
import com.zhuzirui.brt.service.FileUploadService;
import com.zhuzirui.brt.service.UserService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

import static com.zhuzirui.brt.utils.JwtUtil.getJwtTokenFromCookie;

@RestController
@RequestMapping("/brt/source")
public class SourceController {

    @Value("${images.max-size}")
    private int maxImageSize;

    @Value("${images.allowed-extensions}")
    private List<String> allowedImageExtensions;

    @Value("#{'${files.allowed-extensions}'.split(',')}")
    private List<String> allowedExtensions;

    @Value("${files.max-size}")
    private int maxFileSize;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private DownloadHistoryService downloadHistoryService;

    @Autowired
    private DownloadHistoryStructMapper downloadHistoryStructMapper;

    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(SourceController.class);


    //上传文件内容/图片
    @PostMapping("/upload/{type}")
    public Result<String> uploadContent(@RequestParam MultipartFile file, @PathVariable String type, HttpServletRequest request) {
        if (file.isEmpty()) {
            return Result.error(400, "File is empty");
        }

        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        int fileSize = (int) file.getSize();
        if(type.equals("image")) {
            //下载图片判断
            if(!allowedImageExtensions.contains(fileExtension)) {
                return Result.error(400, "Image extension not supported");
            }
            if(fileSize > maxImageSize){
                return Result.error(400, "Image size exceeds max size");
            }
        }
        else if(type.equals("content")){
            //下载文件判断
            if(!allowedExtensions.contains(fileExtension)) {
                return Result.error(400, "Content extension not supported");
            }
            if(fileSize > maxFileSize){
                return Result.error(400, "Content size exceeds max size");
            }
        }
        else{
            return Result.error(400, "Parameter type error");
        }
        String url;
        try{
            url = fileUploadService.uploadFile(file,type);
        }catch (IOException e) {
            e.printStackTrace();
            return Result.error(500, "Failed to save file；"+e.getMessage());
        }

        return Result.success(url);
    }

    //文件下载本地接口
    @GetMapping("/download/{type}/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName,
                                             @PathVariable String type,
                                             HttpServletRequest request,
                                             HttpServletResponse response)  {
        log.info("download file: " + fileName);
        try{
            java.io.File file = fileUploadService.downloadFile(fileName, type);
            if (file == null || !file.exists()) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(file.toURI());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            //添加下载记录
            DownloadHistoryDTO downloadHistoryDTO = new DownloadHistoryDTO();
            downloadHistoryDTO.setSourceUrl("/brt/source/download/"+type+"/"+fileName);
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

//            downloadHistoryDTO.setIsDeleted(false);
//            downloadHistoryDTO.setDownloadTime(LocalDateTime.now());
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

}
