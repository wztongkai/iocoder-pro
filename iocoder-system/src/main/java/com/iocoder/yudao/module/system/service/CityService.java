package com.iocoder.yudao.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.domain.CityDO;
import com.iocoder.yudao.module.system.vo.city.CitySimpleRespVO;

import java.util.List;

/**
 * <p>
 * 中国城市表 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-10-14
 */
public interface CityService extends IService<CityDO> {

    /**
     * 获取当前城市的次级城市列表
     *
     * @param cityId 城市编啊好
     * @return 次级城市列表
     */
    List<CitySimpleRespVO> getSimpleCity(Long cityId);
}
