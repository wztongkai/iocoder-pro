package com.iocoder.yudao.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.vo.dept.*;
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
     * 筛选部门列表
     *
     * @param reqVO 筛选条件请求 VO
     * @return 部门列表
     */
    List<DeptRespVO> getSimpleDepts(DeptListReqVO reqVO);

    /**
     * 校验部门状态是否已开启
     *
     * @param deptIds 部门编号集合
     */
    void validDepts(List<Long> deptIds);

    /**
     * 创建部门
     *
     * @param createReqVO 部门信息
     * @return 部门编号
     */
    Long createDept(DeptCreateReqVO createReqVO);

    /**
     * 修改部门信息
     *
     * @param updateReqVO 部门信息
     */
    void updateDept(DeptUpdateReqVO updateReqVO);

    /**
     * 删除部门
     *
     * @param id 部门编号
     */
    void deleteDept(Long id);

    /**
     * 获取部门详情
     *
     * @param id 部门编号
     * @return 部门详细信息
     */
    DeptRespVO getDeptInfo(Long id);

    /**
     * 更新部门状态
     * @param updateStatusReqVO 部门状态更新信息
     */
    void updateDeptStatus(DeptUpdateStatusReqVO updateStatusReqVO);
}
