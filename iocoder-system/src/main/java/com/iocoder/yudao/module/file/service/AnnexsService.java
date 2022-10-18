package com.iocoder.yudao.module.file.service;

import com.iocoder.yudao.module.file.domain.AnnexsDO;
import com.baomidou.mybatisplus.extension.service.IService;

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
    AnnexsDO getAnnexByDictCode(String code);
}
