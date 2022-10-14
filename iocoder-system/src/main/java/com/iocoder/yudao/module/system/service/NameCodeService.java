package com.iocoder.yudao.module.system.service;

import com.iocoder.yudao.module.system.domain.NameCodeDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-10-14
 */
public interface NameCodeService extends IService<NameCodeDO> {

    /**
     * 根据名称获取电码
     * @param character 名称字符
     * @return 电码
     */
    String getCodeByName(String character);
}
