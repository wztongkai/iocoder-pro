package com.iocoder.yudao.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.enums.CommonStatusEnum;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.mapper.DeptMapper;
import com.iocoder.yudao.module.system.service.DeptService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.DeptErrorCode.DEPT_NOT_ENABLE;
import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.DeptErrorCode.DEPT_NOT_FOUND;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, DeptDO> implements DeptService {

    @Override
    public Set<Long> getDeptCondition(Long deptId) {
        if (Objects.isNull(deptId)) {
            return Collections.emptySet();
        }
        // 获取子部门
        List<DeptDO> deptDOList = this.getDeptsByParentId(deptId, true);
        // 将获取的子部门信息中的 子部门编号转为 set集合
        Set<Long> deptIds = CollConvertUtils.convertSet(deptDOList, DeptDO::getId);
        // 将父部门编号添加进行部门编号集合中
        deptIds.add(deptId);
        return deptIds;
    }

    @Override
    public List<DeptDO> getSimpleDepts(Collection<Long> deptIds) {
        List<DeptDO> deptInfoList = baseMapper.selectBatchIds(deptIds);
        if (CollectionUtils.isEmpty(deptInfoList)) {
            return Collections.emptyList();
        }
        return deptInfoList;
    }

    @Override
    public void validDepts(List<Long> deptIds) {
        if (CollectionUtils.isEmpty(deptIds)) {
            return;
        }
        // 批量查询部门信息
        List<DeptDO> deptList = baseMapper.selectBatchIds(deptIds);
        // 将部门信息List 转为 Map
        Map<Long, DeptDO> deptMap = CollConvertUtils.convertMap(deptList, DeptDO::getId);
        // 校验
        deptIds.forEach(deptId -> {
            DeptDO dept = deptMap.get(deptId);
            if (ObjectUtils.isEmpty(dept)) {
                throw ServiceExceptionUtil.exception(DEPT_NOT_FOUND);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus())) {
                throw ServiceExceptionUtil.exception(DEPT_NOT_ENABLE, dept.getName());
            }
        });
    }

    /**
     * 获得所有子部门
     *
     * @param parentId  部门编号
     * @param recursive 是否递归获取所有
     * @return 子部门列表
     */
    private List<DeptDO> getDeptsByParentId(Long parentId, boolean recursive) {
        if (parentId == null) {
            return Collections.emptyList();
        }
        List<DeptDO> result = new ArrayList<>();
        this.getDeptsByParentIdFromTable(result, parentId, recursive ? Integer.MAX_VALUE : 1);
        return result;
    }

    /**
     * 递归获取所有子部门
     *
     * @param result         结果
     * @param parentId       父部门编号
     * @param recursiveCount 递归次数
     */
    private void getDeptsByParentIdFromTable(List<DeptDO> result, Long parentId, int recursiveCount) {
        // 递归次数为 0，结束！
        if (recursiveCount == 0) {
            return;
        }
        // 获得子部门
        List<DeptDO> depts = baseMapper.selectList(new LambdaQueryWrapper<DeptDO>()
                .eq(DeptDO::getParentId, parentId)
        );
        if (CollectionUtils.isEmpty(depts)) {
            return;
        }
        result.addAll(depts);

        // 继续递归
        depts.forEach(dept -> this.getDeptsByParentIdFromTable(result, dept.getId(), recursiveCount - 1));
    }
}
