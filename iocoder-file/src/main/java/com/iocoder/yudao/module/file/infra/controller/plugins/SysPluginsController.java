package com.iocoder.yudao.module.file.infra.controller.plugins;

import com.iocoder.yudao.module.file.infra.service.SysPluginsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wu kai
 * @since 2022/11/3
 */
@Api(tags = "管理后台 - 插件相关接口")
@RestController
@Slf4j
@RequestMapping("/file/infra/plugin")
public class SysPluginsController {

    @Resource
    SysPluginsService pluginsService;

    @GetMapping("/JGPluginsDownload")
    @ApiOperation(value = "金格插件下载")
    @ApiImplicitParam(name = "pluginsType", value = "插件类型  1:在线编辑插件  2：签章插件",required = true, example = "1", dataTypeClass = Long.class)
    public void JGPluginsDownload(@RequestParam("pluginsType") Integer pluginsType, HttpServletResponse response) {
        pluginsService.JGPluginsDownload(pluginsType, response);
    }
}
