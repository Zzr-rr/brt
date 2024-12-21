package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.model.entity.DownloadHistory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 下载记录表，记录用户的文件下载历史 前端控制器
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@RestController
@RequestMapping("/brt/downloadHistory")
public class DownloadHistoryController {

    //弃用,下载文件时会生成记录
//    @PostMapping("/create")
//    public Result<Boolean> create() {
//        return null;
//    }

    //暂时用不上
//    @PostMapping("/delete")
//    public Result<Boolean> delete() {
//        return null;
//    }

    //弃用，记录禁止修改
//    @PostMapping("/update")
//    public Result<Boolean> update() {
//        return null;
//    }

    //返回筛选结果
    @PostMapping("/list/{type}")
    public Result<List<DownloadHistory>> list(@PathVariable String type, HttpServletRequest request) {
        return null;
    }
}
