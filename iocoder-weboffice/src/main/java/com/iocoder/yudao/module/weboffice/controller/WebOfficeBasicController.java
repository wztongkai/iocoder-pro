package com.iocoder.yudao.module.weboffice.controller;

import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.weboffice.service.WebOfficeBasicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * @author wu kai
 * @since 2022/10/13
 */

@Api(tags = "在线编辑word - 在线编辑基础接口")
@RestController
@RequestMapping("/online/web-office/basic")
@Validated
public class WebOfficeBasicController {

    @Resource
    WebOfficeBasicService webOfficeBasicService;

    @PostMapping("/onlineOpenFile")
    @ApiOperation(value = "打开文件", notes = "通过在线编辑插件，打开文件")
    @ApiImplicitParam(name = "fileUrl", value = "文件链接", required = true, dataTypeClass = String.class)
    public CommonResult<Boolean> onlineOpenFile(@RequestParam("fileUrl") String fileUrl, HttpServletResponse response) {
        return success(webOfficeBasicService.onlineOpenFile(fileUrl, response));
    }

    @PostMapping("/onlineSaveFile")
    @ApiOperation(value = "保存文件", notes = "通过在线编辑插件，保存文件")
    public CommonResult<Boolean> onlineSaveFile(HttpServletRequest request) {
        return success(webOfficeBasicService.onlineSaveFile(request));
    }

    @PostMapping("/onlineSaveFilePDF")
    @ApiOperation(value = "保存文件为PDF", notes = "通过在线编辑插件，将word保存为pdf")
    public CommonResult<Boolean> onlineSaveFilePDF(HttpServletRequest request) {
        return success(webOfficeBasicService.onlineSaveFilePDF(request));
    }
}
