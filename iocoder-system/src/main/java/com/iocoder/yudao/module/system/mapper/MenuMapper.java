package com.iocoder.yudao.module.system.mapper;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.mapper.BaseMapperX;
import com.iocoder.yudao.module.system.domain.MenuDO;
import com.iocoder.yudao.module.system.vo.permission.menu.MenuListReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author wu kai
 * @since 2022-06-27
 */
@Mapper
public interface MenuMapper extends BaseMapperX<MenuDO> {

    /**
     * 查询菜单列表
     * @param reqVO 筛选信息
     * @return 菜单列表
     */
    default List<MenuDO> selectList(MenuListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MenuDO>().likeIfPresent(MenuDO::getName, reqVO.getName())
                .eqIfPresent(MenuDO::getStatus, reqVO.getStatus())
                .orderByDesc(MenuDO::getSort)
        );
    }

    List<String> selectMenuPermsByUserId(Long userId);

    List<MenuDO> selectMenuTreeAll();

    List<MenuDO> selectMenuTreeByUserId(Long userId);
}
