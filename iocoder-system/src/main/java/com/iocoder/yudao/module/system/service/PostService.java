package com.iocoder.yudao.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.domain.PostDO;

import java.util.List;

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
     * 获取指定编号的岗位列表
     * @param postIds 岗位编号
     * @return 岗位信息列表
     */
    List<PostDO> getSimplePosts(String[] postIds);
}
