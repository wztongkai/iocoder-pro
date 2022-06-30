package com.iocoder.yudao.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.enums.common.CommonStatusEnum;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.domain.UserPostDO;
import com.iocoder.yudao.module.system.mapper.PostMapper;
import com.iocoder.yudao.module.system.service.PostService;
import com.iocoder.yudao.module.system.service.UserPostService;
import com.iocoder.yudao.module.system.vo.post.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.PostErrorCode.*;
import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.REQ_ARGS_NOT_NULL;
import static com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil.exception;

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

    @Resource
    UserPostService userPostService;


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
                throw exception(POST_NOT_FOUND);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(post.getStatus())) {
                throw exception(POST_NOT_ENABLE, post.getName());
            }
        });
    }

    @Override
    public Long createPost(PostCreateReqVO createReqVO) {
        // 校验数据
        checkCreateOrUpdate(null, createReqVO.getName(), createReqVO.getCode());
        // 校验通过，执行插入操作
        PostDO postDO = new PostDO();
        BeanUtil.copyProperties(createReqVO, postDO);
        baseMapper.insert(postDO);
        return postDO.getId();
    }

    @Override
    public void updatePost(PostUpdateReqVO updateReqVO) {
        // 校验数据
        checkCreateOrUpdate(updateReqVO.getId(), updateReqVO.getName(), updateReqVO.getCode());
        // 校验通过，执行插入操作
        PostDO postDO = new PostDO();
        BeanUtil.copyProperties(updateReqVO, postDO);
        baseMapper.updateById(postDO);
    }

    @Override
    public void updatePostStatus(PostUpdateStatusReqVO updateStatusReqVO) {
        // 校验岗位是否存在
        checkPostExists(updateStatusReqVO.getId());
        baseMapper.update(null, new LambdaUpdateWrapper<PostDO>()
                .set(PostDO::getStatus, updateStatusReqVO.getStatus())
                .eq(PostDO::getId, updateStatusReqVO.getId())
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePost(Long postId) {
        // 校验岗位是否存在
        checkPostExists(postId);
        // 校验通过，删除岗位信息
        baseMapper.deleteById(postId);
        // 删除关联信息
        userPostService.remove(new LambdaUpdateWrapper<UserPostDO>()
                .eq(UserPostDO::getPostId, postId)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePostBatch(PostBatchDeleteReqVO batchDeleteReqVO) {
        if(CollectionUtils.isEmpty(batchDeleteReqVO.getPostIds())){
            throw ServiceExceptionUtil.exception(REQ_ARGS_NOT_NULL);
        }
        batchDeleteReqVO.getPostIds().forEach(this::deletePost);
    }

    @Override
    public List<PostRespVO> getSimplePosts(PostListReqVO listReqVO) {
        List<PostDO> simplePosts = baseMapper.getSimplePosts(listReqVO);
        List<PostRespVO> postInfoList = new ArrayList<>();
        BeanUtil.copyListProperties(simplePosts, postInfoList, PostRespVO.class);
        return postInfoList;
    }

    @Override
    public PostRespVO getPostInfo(Long postId) {
        // 校验岗位是否存在
        checkPostExists(postId);
        PostDO postDO = baseMapper.selectById(postId);
        PostRespVO postRespVO = new PostRespVO();
        BeanUtil.copyProperties(postDO, postRespVO);
        return postRespVO;
    }

    /**
     * 校验参数
     *
     * @param id   岗位编号
     * @param name 岗位名称
     * @param code 岗位编号
     */
    private void checkCreateOrUpdate(Long id, String name, String code) {
        // 校验岗位是否存在
        checkPostExists(id);
        // 校验岗位名称是否唯一
        checkPostNameUnique(id, name);
        // 校验岗位编码是否唯一
        checkPostCodeUnique(id, code);

    }

    /**
     * 校验岗位是否存在
     *
     * @param id 岗位编号
     */
    public void checkPostExists(Long id) {
        if (id == null) {
            return;
        }
        PostDO postDO = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(postDO)) {
            throw exception(POST_NOT_FOUND);
        }
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param id   岗位编号
     * @param name 岗位名称
     */
    public void checkPostNameUnique(Long id, String name) {
        PostDO postDO = baseMapper.selectOne(new LambdaQueryWrapperX<PostDO>()
                .eqIfPresent(PostDO::getName, name)
                .orderByDesc(PostDO::getCreateTime).last("limit 1")
        );
        if (ObjectUtils.isEmpty(postDO)) {
            return;
        }
        if (!postDO.getId().equals(id)) {
            throw exception(POST_NAME_DUPLICATE);
        }

    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param id   岗位编号
     * @param code 岗位编码
     */
    public void checkPostCodeUnique(Long id, String code) {
        PostDO postDO = baseMapper.selectOne(new LambdaQueryWrapperX<PostDO>()
                .eqIfPresent(PostDO::getName, code)
                .orderByDesc(PostDO::getCreateTime).last("limit 1")
        );
        if (ObjectUtils.isEmpty(postDO)) {
            return;
        }
        if (!postDO.getId().equals(id)) {
            throw exception(POST_CODE_DUPLICATE);
        }

    }
}
