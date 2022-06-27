package com.iocoder.yudao.module.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.enums.common.CommonStatusEnum;
import com.iocoder.yudao.module.commons.enums.role.DataScopeEnum;
import com.iocoder.yudao.module.commons.enums.role.RoleTypeEnum;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.system.domain.RoleDO;
import com.iocoder.yudao.module.system.mapper.RoleMapper;
import com.iocoder.yudao.module.system.service.RoleService;
import com.iocoder.yudao.module.system.vo.role.RoleCreateReqVO;
import com.iocoder.yudao.module.system.vo.role.RoleUpdateReqVO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.RoleErrorCode.ROLE_CODE_DUPLICATE;
import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.RoleErrorCode.ROLE_NAME_DUPLICATE;
import static com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil.exception;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {

    @Override
    public Long createRole(RoleCreateReqVO createReqVO) {
        // 校验参数
        checkCreateOrUpdate(null, createReqVO.getName(), createReqVO.getCode());
        // 构建新增数据
        RoleDO roleDO = new RoleDO();
        BeanUtil.copyProperties(createReqVO, roleDO);
        roleDO.setType(ObjectUtil.defaultIfNull(null, RoleTypeEnum.CUSTOM.getType()));
        roleDO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        roleDO.setDataScope(DataScopeEnum.ALL.getScope());
        // 执行新增
        baseMapper.insert(roleDO);
        return roleDO.getId();
    }

    @Override
    public void updateRole(RoleUpdateReqVO updateReqVO) {
        // 校验参数
        checkCreateOrUpdate(updateReqVO.getId(), updateReqVO.getName(), updateReqVO.getCode());
        RoleDO roleDO = new RoleDO();
        BeanUtil.copyProperties(updateReqVO,roleDO);
        baseMapper.updateById(roleDO);
        // TODO: 后期将角色权限、部门岗位等信息初始化进redis中， 此处编辑完成后需要发送redis的消息
    }

    /**
     * 校验参数
     *
     * @param id   角色编号
     * @param name 角色名称
     * @param code 角色code
     */
    private void checkCreateOrUpdate(Long id, String name, String code) {
        // 校验角色名是否唯一
        if (StringUtils.isBlank(name)) {
            return;
        }
        RoleDO roleDO = baseMapper.selectOne(new LambdaQueryWrapperX<RoleDO>()
                .eqIfPresent(RoleDO::getName, name)
                .orderByDesc(RoleDO::getCreateTime)
                .last("limit 1")
        );
        if (ObjectUtils.isNotEmpty(roleDO) && Objects.equals(roleDO.getId(), id)) {
            throw exception(ROLE_NAME_DUPLICATE, name);
        }
        // 校验角色编码是否唯一
        if (StringUtils.isBlank(code)) {
            return;
        }
        roleDO = baseMapper.selectOne(new LambdaQueryWrapperX<RoleDO>()
                .eqIfPresent(RoleDO::getCode, code)
                .orderByDesc(RoleDO::getCreateTime)
                .last("limit 1")
        );
        if (ObjectUtils.isNotEmpty(roleDO) && Objects.equals(roleDO.getId(), id)) {
            throw exception(ROLE_CODE_DUPLICATE, name);
        }

    }
}
