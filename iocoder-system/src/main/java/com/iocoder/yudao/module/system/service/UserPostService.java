package com.iocoder.yudao.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.domain.UserPostDO;

import java.util.List;

/**
 * <p>
 * 用户岗位表 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
public interface UserPostService extends IService<UserPostDO> {

    /**
     * 查询用户的岗位信息集合
     *
     * @param userId 用户编号
     * @return 用户岗位信息集合
     */
    List<PostDO> selectPostInfoByUserId(Long userId);

    /**
     * 查询用户岗位
     * @param userId 用户编号
     * @return 岗位信息
     */
    List<UserPostDO> selectUserPostListByUserId(Long userId);
}
