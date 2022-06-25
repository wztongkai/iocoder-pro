package com.iocoder.yudao.module.system.service.impl;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.UserDeptDO;
import com.iocoder.yudao.module.system.domain.UserPostDO;
import com.iocoder.yudao.module.system.mapper.UserDeptMapper;
import com.iocoder.yudao.module.system.service.DeptService;
import com.iocoder.yudao.module.system.service.UserDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户岗位表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-25
 */
@Service
public class UserDeptServiceImpl extends ServiceImpl<UserDeptMapper, UserDeptDO> implements UserDeptService {

    @Resource
    DeptService deptService;

    @Override
    public List<DeptDO> selectDeptInfoByUserId(Long userId) {
        // 根据用户编号查询用户部门信息
        List<UserDeptDO> userDeptList = baseMapper.selectList(new LambdaQueryWrapperX<UserDeptDO>()
                .eqIfPresent(UserDeptDO::getUserId, userId)
                .orderByDesc(UserDeptDO::getCreateTime)
        );
        if(CollectionUtils.isEmpty(userDeptList)){
            return Collections.emptyList();
        }
        // 获取部门编号集合
        Set<Long> deptId = CollConvertUtils.convertSet(userDeptList, UserDeptDO::getDeptId);
        // 根据部门编号集合获取对应的部门信息
        return deptService.getDeptInfoMap(deptId);
    }
}
