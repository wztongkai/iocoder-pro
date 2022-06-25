package com.iocoder.yudao.module.system.service.impl;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.domain.UserPostDO;
import com.iocoder.yudao.module.system.mapper.UserPostMapper;
import com.iocoder.yudao.module.system.service.PostService;
import com.iocoder.yudao.module.system.service.UserPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 用户岗位表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Service
public class UserPostServiceImpl extends ServiceImpl<UserPostMapper, UserPostDO> implements UserPostService {

    @Resource
    PostService postService;

    @Override
    public List<PostDO> selectPostInfoByUserId(Long userId) {
        // 根据用户编号查询用户岗位信息
        List<UserPostDO> userPostList = baseMapper.selectList(new LambdaQueryWrapperX<UserPostDO>()
                .eqIfPresent(UserPostDO::getUserId, userId)
                .orderByDesc(UserPostDO::getCreateTime)
        );
        if(CollectionUtils.isEmpty(userPostList)){
            return Collections.emptyList();
        }
        // 获取岗位编号集合
        Set<Long> postId = CollConvertUtils.convertSet(userPostList, UserPostDO::getPostId);
        // 根据岗位编号集合获取对应的岗位信息
        return postService.getPostInfoMap(postId);
    }
}
