package com.iocoder.yudao.module.file.service.impl;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.file.domain.AnnexsDO;
import com.iocoder.yudao.module.file.mapper.AnnexsMapper;
import com.iocoder.yudao.module.file.service.AnnexsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.system.vo.annex.AnnexsSimpleRespVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-09-06
 */
@Service
public class AnnexsServiceImpl extends ServiceImpl<AnnexsMapper, AnnexsDO> implements AnnexsService {

    @Value("${resourceServer.url}")
    private String RESOURCE_SERVER_URL;

    @Override
    public void insertAnnex(AnnexsDO annexsDO) {
        baseMapper.insert(annexsDO);
    }

    @Override
    public List<AnnexsSimpleRespVO> getAnnexByDictCode(String code) {
        List<AnnexsDO> annexDOList = baseMapper.selectList(new LambdaQueryWrapperX<AnnexsDO>()
                .eq(AnnexsDO::getAnnexCode, code)
        );
        List<AnnexsSimpleRespVO> annexsSimpleRespVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(annexDOList)) {
            annexDOList.forEach(annexsDO -> {
                AnnexsSimpleRespVO annexsSimpleRespVO = new AnnexsSimpleRespVO();
                annexsSimpleRespVO.setFileName(annexsDO.getAnnexName());
                annexsSimpleRespVO.setFilePath(StringUtils.isNotEmpty(annexsDO.getAnnexAddress()) ? RESOURCE_SERVER_URL + annexsDO.getAnnexAddress() : "");
                annexsSimpleRespVO.setFileType(annexsDO.getAnnexType());
                annexsSimpleRespVOList.add(annexsSimpleRespVO);
            });
        }
        return annexsSimpleRespVOList;

    }

    @Override
    public AnnexsDO getAnnexByDictCodeAndAnnexType(String code, Integer type) {
        AnnexsDO annexsDO = baseMapper.selectOne(new LambdaQueryWrapperX<AnnexsDO>()
                .eq(AnnexsDO::getAnnexCode, code)
                .eq(AnnexsDO::getAnnexType, type)
                .orderByDesc(AnnexsDO::getCreateTime).last("limit 1")
        );
        if (ObjectUtils.isNotEmpty(annexsDO)) {
            annexsDO.setAnnexAddress(StringUtils.isNotEmpty(annexsDO.getAnnexAddress()) ? RESOURCE_SERVER_URL + annexsDO.getAnnexAddress() : "");
        }
        return annexsDO;
    }
}
