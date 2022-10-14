package com.iocoder.yudao.module.system.service.impl;

import com.iocoder.yudao.module.commons.config.Assertion;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.system.domain.CityDO;
import com.iocoder.yudao.module.system.mapper.CityMapper;
import com.iocoder.yudao.module.system.service.CityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.system.vo.city.CitySimpleRespVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 中国城市表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-10-14
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, CityDO> implements CityService {

    @Override
    public List<CitySimpleRespVO> getSimpleCity(Long cityId) {
        Assertion.isNull(cityId, "城市编号不为空");
        List<CityDO> cityList = baseMapper.selectList(new LambdaQueryWrapperX<CityDO>()
                .eq(CityDO::getParentId, cityId)
        );
        if (CollectionUtils.isEmpty(cityList)) {
            return Collections.emptyList();
        }
        List<CitySimpleRespVO> citySimpleRespVOList = new ArrayList<>();
        BeanUtil.copyListProperties(cityList, citySimpleRespVOList, CitySimpleRespVO.class);
        return citySimpleRespVOList;
    }


}
