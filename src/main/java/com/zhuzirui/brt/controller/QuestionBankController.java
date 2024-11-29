package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 题库信息表，存储题库信息 前端控制器
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@RestController
@RequestMapping("/brt/questionBank")
public class QuestionBankController {
    //根据指定文件id列表生成题库
    @PostMapping("/create")
    public Result<Boolean> create(@RequestBody List<Integer> fileId, HttpServletRequest httpServletRequest) {


        return null;
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
}
