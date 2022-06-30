package com.iocoder.yudao.module.system.mapper;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.mapper.BaseMapperX;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.vo.dept.DeptListReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Mapper
public interface DeptMapper extends BaseMapperX<DeptDO> {

    /**
     * 筛选部门列表
     * @param reqVO 筛选信息
     * @return 列表信息
     */
    default List<DeptDO> getSimpleDepts(DeptListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DeptDO>()
                .likeIfPresent(DeptDO::getName, reqVO.getName())
                .eqIfPresent(DeptDO::getStatus, reqVO.getStatus())
                .orderByDesc(DeptDO::getSort)
        );
    }

}
