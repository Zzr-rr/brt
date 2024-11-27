package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.mapper.FileStructMapper;
import com.zhuzirui.brt.model.dto.FileDTO;
import com.zhuzirui.brt.model.entity.File;
import com.zhuzirui.brt.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    FileStructMapper fileStructMapper;

    @PostMapping("/create")
    public Result<Boolean> create(FileDTO fileDTO) {
        File file = fileStructMapper.dtoToEntity(fileDTO);

        boolean result = fileService.saveFile(file);

        return Result.success(result);
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
