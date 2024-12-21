package com.zhuzirui.brt.service;

import com.zhuzirui.brt.model.entity.CommunityInteraction;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 社区互动表，记录用户在社区的互动内容 服务类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
public interface CommunityInteractionService extends IService<CommunityInteraction> {
    List<Integer> listLikedUsers(Integer communityId);
    List<CommunityInteraction> listPosts();
    List<CommunityInteraction> listComments(Integer communityId);
}
