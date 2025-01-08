package com.zhuzirui.brt.controller;

import com.zhuzirui.brt.common.Result;
import com.zhuzirui.brt.dao.CommunityInteractionMapper;
import com.zhuzirui.brt.mapper.CommunityInteractionStructMapper;
import com.zhuzirui.brt.model.dto.CommunityInteractionDTO;
import com.zhuzirui.brt.model.dto.PostDTO;
import com.zhuzirui.brt.model.entity.CommunityInteraction;
import com.zhuzirui.brt.model.entity.User;
import com.zhuzirui.brt.service.CommunityInteractionService;
import com.zhuzirui.brt.service.UserService;
import com.zhuzirui.brt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zhuzirui.brt.utils.JwtUtil.getJwtTokenFromCookie;

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

     static enum Type {
        COMMENT,
        LIKE,
        POST
    }

    @Autowired
    private CommunityInteractionService communityInteractionService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private CommunityInteractionStructMapper communityInteractionStructMapper;

    @Autowired
    private CommunityInteractionMapper communityInteractionMapper;


    @PostMapping("/post")
    public Result<Boolean> create(@RequestBody CommunityInteractionDTO communityInteractionDTO, HttpServletRequest request) {
        //鉴权
        String jwtToken = getJwtTokenFromCookie(request);// 调用方法从 Cookie 中获取 JWT Token
        Integer userId;
        // 检查 JWT Token 是否存在
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            userId = jwtUtil.extractUserId(jwtToken);
            User user = userService.getUserByUserId(userId);
            if (user == null) return Result.error(404, "User not found");
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "Invalid JWT token");
        }
        if(communityInteractionDTO.getContent() == null || communityInteractionDTO.getContent().isEmpty())
            return Result.error(401, "Content is empty");
        communityInteractionDTO.setUserId(userId);
        communityInteractionDTO.setType(Type.POST.toString());

        CommunityInteraction communityInteraction = communityInteractionStructMapper.dtoToEntity(communityInteractionDTO);
        communityInteractionService.save(communityInteraction);

        return Result.success(true);
    }

    @PostMapping("/like")
    public Result<Boolean> like(@RequestBody CommunityInteractionDTO communityInteractionDTO, HttpServletRequest request) {
        //鉴权
        String jwtToken = getJwtTokenFromCookie(request);// 调用方法从 Cookie 中获取 JWT Token
        Integer userId;
        // 检查 JWT Token 是否存在
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            userId = jwtUtil.extractUserId(jwtToken);
            User user = userService.getUserByUserId(userId);
            if (user == null) return Result.error(404, "User not found");
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "Invalid JWT token");
        }
        communityInteractionDTO.setUserId(userId);
        communityInteractionDTO.setType(Type.LIKE.toString());
        communityInteractionDTO.setContent("liked");
        if(communityInteractionDTO.getTargetId() == null)
            return Result.error(401, "TargetId is empty");
        CommunityInteraction targetCommunityInteraction = communityInteractionService.getById(communityInteractionDTO.getTargetId());
        if(targetCommunityInteraction == null)
            return Result.error(404, "Target CommunityInteraction not found");
        targetCommunityInteraction.setLikesCount(targetCommunityInteraction.getLikesCount() + 1);
        communityInteractionService.updateById(targetCommunityInteraction);
        CommunityInteraction communityInteraction = communityInteractionStructMapper.dtoToEntity(communityInteractionDTO);
        communityInteractionService.save(communityInteraction);
        return Result.success(true);
    }

    @PostMapping("/comment")
    public Result<Boolean> comment(@RequestBody CommunityInteractionDTO communityInteractionDTO, HttpServletRequest request) {
        //鉴权
        String jwtToken = getJwtTokenFromCookie(request);// 调用方法从 Cookie 中获取 JWT Token
        Integer userId;
        // 检查 JWT Token 是否存在
        if (jwtToken != null && !jwtToken.isEmpty()) {
            // 使用 JwtUtil 提取用户 ID
            userId = jwtUtil.extractUserId(jwtToken);
            User user = userService.getUserByUserId(userId);
            if (user == null) return Result.error(404, "User not found");
        } else {
            // 如果没有 JWT Token，返回错误信息
            return Result.error(401, "Invalid JWT token");
        }
        communityInteractionDTO.setUserId(userId);
        communityInteractionDTO.setType(Type.COMMENT.toString());
        if(communityInteractionDTO.getTargetId() == null)
            return Result.error(401, "TargetId is empty");
        CommunityInteraction targetCommunityInteraction = communityInteractionService.getById(communityInteractionDTO.getTargetId());
        if(targetCommunityInteraction == null)
            return Result.error(404, "Target CommunityInteraction not found");
        if(communityInteractionDTO.getContent() == null || communityInteractionDTO.getContent().isEmpty())
            return Result.error(401, "Content is empty");
        CommunityInteraction communityInteraction = communityInteractionStructMapper.dtoToEntity(communityInteractionDTO);
        communityInteractionService.save(communityInteraction);
        return Result.success(true);
    }

    @GetMapping("/list/post")
    public Result<List<CommunityInteraction>> listPost() {
        //返回所有帖子
        return Result.success(communityInteractionService.listPosts());
    }

    @GetMapping("/list/like")
    public Result<List<Integer>> listLike(@RequestParam Integer targetId) {
        //返回所有点赞这篇帖子的用户ID
        if(targetId == null)
            return Result.error(401, "TargetId is empty");
        CommunityInteraction targetCommunityInteraction = communityInteractionService.getById(targetId);
        if(targetCommunityInteraction == null)
            return Result.error(404, "Target CommunityInteraction not found");
        return Result.success(communityInteractionService.listLikedUsers(targetId));
    }

    @GetMapping("/list/comment")
    public Result<List<CommunityInteraction>> listComment(@RequestParam Integer targetId) {
        //返回这篇帖子的所有评论
        if(targetId == null)
            return Result.error(401, "TargetId is empty");
        CommunityInteraction targetCommunityInteraction = communityInteractionService.getById(targetId);
        if(targetCommunityInteraction == null)
            return Result.error(404, "Target CommunityInteraction not found");
        return Result.success(communityInteractionService.listComments(targetId));
    }

    @GetMapping("/list/postinfo")
    public Result<List<PostDTO>> listPostinfo() {
        return Result.success(communityInteractionMapper.listPostInfo());
    }

}
