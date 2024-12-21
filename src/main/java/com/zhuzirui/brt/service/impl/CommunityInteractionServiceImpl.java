package com.zhuzirui.brt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuzirui.brt.model.entity.CommunityInteraction;
import com.zhuzirui.brt.dao.CommunityInteractionMapper;
import com.zhuzirui.brt.service.CommunityInteractionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 社区互动表，记录用户在社区的互动内容 服务实现类
 * </p>
 *
 * @author zhuzirui
 * @since 2024-10-16
 */
@Service
public class CommunityInteractionServiceImpl extends ServiceImpl<CommunityInteractionMapper, CommunityInteraction> implements CommunityInteractionService {
   @Autowired
   private CommunityInteractionMapper communityInteractionMapper;

    @Override
    public List<CommunityInteraction> listComments(Integer communityId) {
        QueryWrapper<CommunityInteraction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", "COMMENT");
        queryWrapper.eq("is_deleted", false);
        queryWrapper.eq("target_id", communityId);
        return communityInteractionMapper.selectList(queryWrapper);
    }

    @Override
    public List<Integer> listLikedUsers(Integer communityId) {
        QueryWrapper<CommunityInteraction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", "LIKE");
        queryWrapper.eq("is_deleted", false);
        queryWrapper.eq("target_id", communityId);
        List<CommunityInteraction> list = communityInteractionMapper.selectList(queryWrapper);
        List<Integer> ids = new ArrayList<>();
        for (CommunityInteraction ci : list) {
            ids.add(ci.getUserId());
        }
        return ids;
    }

    @Override
    public List<CommunityInteraction> listPosts() {
        QueryWrapper<CommunityInteraction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", "POST");
        queryWrapper.eq("is_deleted", false);
        return communityInteractionMapper.selectList(queryWrapper);
    }
}
