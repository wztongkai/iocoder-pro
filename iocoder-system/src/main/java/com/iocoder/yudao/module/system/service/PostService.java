package com.iocoder.yudao.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.vo.post.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 岗位信息表 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
public interface PostService extends IService<PostDO> {


    /**
     * 查询岗位信息集合
     *
     * @param postId 岗位编号集合
     * @return 岗位信息集合
     */
    default List<PostDO> getPostInfoMap(Set<Long> postId) {
        if (CollectionUtils.isEmpty(postId)) {
            return Collections.emptyList();
        }
        return getSimplePostInfos(postId);
    }

    /**
     * 查询岗位信息集合
     *
     * @param postId 岗位编号集合
     * @return 岗位信息集合
     */
    List<PostDO> getSimplePostInfos(Set<Long> postId);

    /**
     * 校验岗位是否处于开启状态
     *
     * @param postIds 岗位编号集合
     */
    void validPosts(List<Long> postIds);

    /**
     * 创建岗位
     *
     * @param createReqVO 岗位信息
     * @return 岗位编号
     */
    Long createPost(PostCreateReqVO createReqVO);

    /**
     * 更新岗位
     *
     * @param updateReqVO 岗位信息
     */
    void updatePost(PostUpdateReqVO updateReqVO);

    /**
     * 修改岗位状态
     *
     * @param updateStatusReqVO 岗位状态信息
     */
    void updatePostStatus(PostUpdateStatusReqVO updateStatusReqVO);

    /**
     * 删除岗位
     *
     * @param postId 岗位编号
     */
    void deletePost(Long postId);

    /**
     * 批量删除岗位
     * @param batchDeleteReqVO 岗位编号集合
     */
    void deletePostBatch(PostBatchDeleteReqVO batchDeleteReqVO);

    /**
     * 获取部门列表
     *
     * @param listReqVO 筛选条件
     * @return 列表信息
     */
    List<PostRespVO> getSimplePosts(PostListReqVO listReqVO);

    /**
     * 获取岗位详情
     *
     * @param postId 岗位编号
     * @return 岗位信息详情
     */
    PostRespVO getPostInfo(Long postId);

}
