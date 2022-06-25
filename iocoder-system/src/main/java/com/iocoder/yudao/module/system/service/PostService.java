package com.iocoder.yudao.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.PostDO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
}
