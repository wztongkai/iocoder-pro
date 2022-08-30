package com.iocoder.yudao.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.enums.common.CommonStatusEnum;
import com.iocoder.yudao.module.commons.enums.dept.DeptIdEnum;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.UserDeptDO;
import com.iocoder.yudao.module.system.mapper.DeptMapper;
import com.iocoder.yudao.module.system.mapper.UserMapper;
import com.iocoder.yudao.module.system.service.DeptService;
import com.iocoder.yudao.module.system.service.UserDeptService;
import com.iocoder.yudao.module.system.vo.dept.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.DeptErrorCode.*;

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

    @Resource
    UserDeptService userDeptService;

    @Resource
    UserMapper userMapper;

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
    public List<DeptRespVO> getSimpleDepts(DeptListReqVO reqVO) {
        String search = reqVO.getSearch();
        if(StringUtils.isNotBlank(search)){
            List<UserDO> userDOList = userMapper.selectList(new LambdaQueryWrapperX<UserDO>()
                    .like(UserDO::getUsername, search)
            );
            List<Long> userIds = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(userDOList)){
                userIds = userDOList.stream().map(UserDO::getId).filter(Objects::nonNull).collect(Collectors.toList());
            }
            reqVO.setUserIds(userIds);
        }
        List<DeptDO> simpleDepts = baseMapper.getSimpleDepts(reqVO);
        List<DeptRespVO> deptInfoList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(simpleDepts)){
            BeanUtil.copyListProperties(simpleDepts, deptInfoList, DeptRespVO.class);
            deptInfoList.forEach(deptRespVO -> {
                Long leaderUserId = deptRespVO.getLeaderUserId();
                UserDO userDO = userMapper.selectById(leaderUserId);
                if(ObjectUtils.isNotEmpty(userDO)){
                    deptRespVO.setLeaderUserName(userDO.getUsername());
                }
            });
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

    @Override
    public Long createDept(DeptCreateReqVO createReqVO) {
        // 添加部门是父部门编号为空，默认为顶级部门
        if (createReqVO.getParentId() == null) {
            createReqVO.setParentId(DeptIdEnum.ROOT.getId());
        }
        // 校验参数
        checkCreateOrUpdate(null, createReqVO.getParentId(), createReqVO.getName());
        // 校验通过，添加部门
        DeptDO deptDO = new DeptDO();
        BeanUtil.copyProperties(createReqVO, deptDO);
        baseMapper.insert(deptDO);
        return deptDO.getId();
    }

    @Override
    public void updateDept(DeptUpdateReqVO updateReqVO) {
        // 部门是父部门编号为空，默认为顶级部门
        if (updateReqVO.getParentId() == null) {
            updateReqVO.setParentId(DeptIdEnum.ROOT.getId());
        }
        // 校验参数
        checkCreateOrUpdate(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());
        // 校验通过，更新部门信息
        DeptDO deptDO = new DeptDO();
        BeanUtil.copyProperties(updateReqVO, deptDO);
        baseMapper.updateById(deptDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDept(Long id) {
        // 校验是否存在
        checkDeptExists(id);
        // 校验是否有子部门
        Long aLong = baseMapper.selectCount(new LambdaQueryWrapperX<DeptDO>()
                .eqIfPresent(DeptDO::getParentId, id)
        );
        if (aLong > 0) {
            throw ServiceExceptionUtil.exception(DEPT_EXITS_CHILDREN);
        }
        // 校验通过，删除
        baseMapper.deleteById(id);
        // 删除关联信息
        userDeptService.remove(new LambdaUpdateWrapper<UserDeptDO>()
                .eq(UserDeptDO::getDeptId, id)
        );
    }

    @Override
    public DeptRespVO getDeptInfo(Long id) {
        // 校验是否存在
        checkDeptExists(id);
        DeptDO deptDO = baseMapper.selectById(id);
        DeptRespVO deptRespVO = new DeptRespVO();
        BeanUtil.copyProperties(deptDO, deptRespVO);
        return deptRespVO;
    }

    @Override
    public void updateDeptStatus(DeptUpdateStatusReqVO updateStatusReqVO) {
        // 校验是否存在
        checkDeptExists(updateStatusReqVO.getId());
        // 校验通过，执行更新
        DeptDO deptDO = new DeptDO();
        BeanUtil.copyProperties(updateStatusReqVO, deptDO);
        baseMapper.updateById(deptDO);
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

    /**
     * 校验参数有效性
     *
     * @param id       部门编号
     * @param parentId 父部门编号
     * @param name     部门名称
     */
    private void checkCreateOrUpdate(Long id, Long parentId, String name) {
        // 校验部门是否存在
        checkDeptExists(id);
        // 校验父部门
        checkParentDeptEnable(id, parentId);
        // 校验部门名是否唯一
        checkDeptNameUnique(id, parentId, name);
    }

    /**
     * 校验部门是否存在
     *
     * @param id 部门编号
     */
    private void checkDeptExists(Long id) {
        if (id == null) {
            return;
        }
        DeptDO dept = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(dept)) {
            throw ServiceExceptionUtil.exception(DEPT_NOT_FOUND);
        }
    }

    /**
     * 校验父部门
     *
     * @param id       部门编号
     * @param parentId 父部门编号
     */
    private void checkParentDeptEnable(Long id, Long parentId) {
        if (parentId == null || DeptIdEnum.ROOT.getId().equals(parentId)) {
            return;
        }
        // 不能设置自己为父部门
        if (parentId.equals(id)) {
            throw ServiceExceptionUtil.exception(DEPT_PARENT_ERROR);
        }
        // 父部门不存在
        DeptDO dept = baseMapper.selectById(parentId);
        if (ObjectUtils.isEmpty(dept)) {
            throw ServiceExceptionUtil.exception(DEPT_PARENT_NOT_EXITS);
        }
        // 父部门被禁用
        if (!CommonStatusEnum.ENABLE.getStatus().equals(dept.getStatus())) {
            throw ServiceExceptionUtil.exception(DEPT_NOT_ENABLE);
        }
    }

    /**
     * 校验部门名是否唯一
     *
     * @param id       部门编号
     * @param parentId 父部门编号
     * @param name     部门名称
     */
    private void checkDeptNameUnique(Long id, Long parentId, String name) {
        DeptDO deptDO = baseMapper.selectOne(new LambdaQueryWrapperX<DeptDO>()
                .eqIfPresent(DeptDO::getParentId, parentId)
                .eqIfPresent(DeptDO::getName, name)
                .orderByDesc(DeptDO::getCreateTime).last("limit 1")
        );
        if (ObjectUtils.isEmpty(deptDO)) {
            return;
        }
        if (!deptDO.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(DEPT_NAME_DUPLICATE);
        }
    }
}
