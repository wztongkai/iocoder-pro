package com.iocoder.yudao.module.file.infra.service;

import cn.smallbun.screw.core.engine.EngineFileType;

/**
 * @author wu kai
 * @since 2022/9/6
 */
public interface DatabaseDocService {

    String doExportFile(EngineFileType engineFileType, Integer type);
}
