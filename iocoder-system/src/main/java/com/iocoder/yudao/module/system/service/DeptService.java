package com.iocoder.yudao.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.domain.DeptDO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
public interface DeptService extends IService<DeptDO> {

    /**
     * 查询指定部门的子部门编号，包括自身
     *
     * @param deptId 部门编号
     * @return 部门编号集合
     */
    Set<Long> getDeptCondition(Long deptId);

    /**
     * 获取指定编号的部门map
     *
     * @param deptIds 部门编号数组
     * @return 部门信息Map
     */
    default List<DeptDO> getDeptInfoMap(Collection<Long> deptIds) {
        if (CollectionUtils.isEmpty(deptIds)) {
            return Collections.emptyList();
        }
        return getSimpleDepts(deptIds);
    }

    /**
     * 获取指定编号的部门列表
     *
     * @param deptIds 部门编号数组
     * @return 部门Map
     */
    List<DeptDO> getSimpleDepts(Collection<Long> deptIds);

    /**
     * 校验部门状态是否喂已开启
     *
     * @param deptIds 部门编号集合
     */
    void validDepts(List<Long> deptIds);
}