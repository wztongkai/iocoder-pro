package com.iocoder.yudao.module.system.service;

import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.domain.UserPostDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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
     * @param userId 用户编号
     * @return 用户岗位信息集合
     */
    List<PostDO> selectPostInfoByUserId(Long userId);
}
