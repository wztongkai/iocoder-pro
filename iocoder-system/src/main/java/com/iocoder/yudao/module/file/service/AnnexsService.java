package com.iocoder.yudao.module.file.service;

import com.iocoder.yudao.module.file.domain.AnnexsDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.vo.annex.AnnexsSimpleRespVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-09-06
 */
public interface AnnexsService extends IService<AnnexsDO> {

    /**
     * 新增附件
     *
     * @param annexsDO 附件信息
     */
    void insertAnnex(AnnexsDO annexsDO);

    /**
     * 根据附件code 查询附件信息
     *
     * @param code 附件code
     * @return 附件信息
     */
    List<AnnexsSimpleRespVO> getAnnexByDictCode(String code);

    /**
     * 根据附件code 和 附件格式类型查询附件
     * @param code 附件code
     * @param type 附件格式类型 1、html 2、word 3、md 4、pdf
     * @return 附件信息
     */
    AnnexsDO getAnnexByDictCodeAndAnnexType(String code, Integer type);
}
