package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 社区互动表，记录用户在社区的互动内容 前端控制器
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@RestController
@RequestMapping("/brt/communityInteraction")
public class CommunityInteractionController {
    @PostMapping("/create")
    public Result<Boolean> create() {
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
