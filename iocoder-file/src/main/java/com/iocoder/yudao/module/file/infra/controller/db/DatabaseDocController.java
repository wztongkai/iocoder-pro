package com.iocoder.yudao.module.file.infra.controller.db;

import cn.smallbun.screw.core.engine.EngineFileType;
import com.iocoder.yudao.module.commons.constant.Constants;
import com.iocoder.yudao.module.file.infra.service.DatabaseDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wu kai
 * @since 2022/9/5
 */
@Api(tags = "管理后台 - 数据库文档")
@RestController
@Slf4j
@RequestMapping("/file/infra/db-doc")
public class DatabaseDocController {

    @Resource
    DatabaseDocService databaseDocService;

    @GetMapping("/export-html")
    @ApiOperation("导出 html 格式的数据文档")
    public String exportHtml() {
        return databaseDocService.doExportFile(EngineFileType.HTML, Constants.ONE);
    }


    @GetMapping("/export-word")
    @ApiOperation("导出 word 格式的数据文档")
    public String exportWord() {
        return databaseDocService.doExportFile(EngineFileType.WORD, Constants.TWO);
    }

    @GetMapping("/export-markdown")
    @ApiOperation("导出 markdown 格式的数据文档")
    public String exportMarkdown() {
        return databaseDocService.doExportFile(EngineFileType.MD, Constants.THREE);
    }


}
