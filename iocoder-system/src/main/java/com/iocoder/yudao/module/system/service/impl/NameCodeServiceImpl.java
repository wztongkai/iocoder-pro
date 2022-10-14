package com.iocoder.yudao.module.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.system.domain.NameCodeDO;
import com.iocoder.yudao.module.system.mapper.NameCodeMapper;
import com.iocoder.yudao.module.system.service.NameCodeService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-10-14
 */
@Service
public class NameCodeServiceImpl extends ServiceImpl<NameCodeMapper, NameCodeDO> implements NameCodeService {

    @Override
    public String getCodeByName(String character) {
        NameCodeDO nameCodeDO = baseMapper.selectOne(new LambdaQueryWrapperX<NameCodeDO>()
                .eq(NameCodeDO::getName, character)
                .orderByDesc(NameCodeDO::getCreateTime).last("limit 1")
        );
        if (ObjectUtils.isEmpty(nameCodeDO)) {
            return null;
        }
        return nameCodeDO.getCode();
    }
}
