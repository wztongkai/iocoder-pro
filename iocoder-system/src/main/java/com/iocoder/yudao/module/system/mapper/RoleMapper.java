package com.iocoder.yudao.module.system.mapper;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.BaseEntity;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.mapper.BaseMapperX;
import com.iocoder.yudao.module.system.domain.RoleDO;
import com.iocoder.yudao.module.system.vo.permission.role.RolePageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Mapper
public interface RoleMapper extends BaseMapperX<RoleDO> {

    /**
     * 分页获取角色列表
     *
     * @param reqVO 分页信息
     * @return 角色列表
     */
    default PageResult<RoleDO> getRolePage(RolePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoleDO>()
                .likeIfPresent(RoleDO::getName, reqVO.getName())
                .likeIfPresent(RoleDO::getCode, reqVO.getCode())
                .eqIfPresent(RoleDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(BaseEntity::getCreateTime, reqVO.getBeginTime(), reqVO.getEndTime())
                .orderByAsc(RoleDO::getId));
    }
}
