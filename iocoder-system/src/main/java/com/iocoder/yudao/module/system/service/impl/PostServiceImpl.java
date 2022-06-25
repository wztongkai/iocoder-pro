package com.iocoder.yudao.module.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.enums.CommonStatusEnum;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.mapper.PostMapper;
import com.iocoder.yudao.module.system.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.DeptErrorCode.DEPT_NOT_ENABLE;
import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.DeptErrorCode.DEPT_NOT_FOUND;
import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.PostErrorCode.POST_NOT_ENABLE;
import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.PostErrorCode.POST_NOT_FOUND;

/**
 * <p>
 * 岗位信息表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Service
@Slf4j
public class PostServiceImpl extends ServiceImpl<PostMapper, PostDO> implements PostService {


    @Override
    public List<PostDO> getSimplePostInfos(Set<Long> postId) {
        // 根据岗位编号批量获取岗位信息集合
        List<PostDO> postList = baseMapper.selectBatchIds(postId);
        if (CollectionUtils.isEmpty(postList)) {
            return Collections.emptyList();
        }
        return postList;
    }

    @Override
    public void validPosts(List<Long> postIds) {
        if (CollectionUtils.isEmpty(postIds)) {
            return;
        }
        // 批量查询岗位信息
        List<PostDO> postList = baseMapper.selectBatchIds(postIds);
        // 将岗位信息List 转为 Map
        Map<Long, PostDO> postMap = CollConvertUtils.convertMap(postList, PostDO::getId);
        // 校验
        postIds.forEach(deptId -> {
            PostDO post = postMap.get(deptId);
            if (ObjectUtils.isEmpty(post)) {
                throw ServiceExceptionUtil.exception(POST_NOT_FOUND);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(post.getStatus())) {
                throw ServiceExceptionUtil.exception(POST_NOT_ENABLE, post.getName());
            }
        });
    }
}
