package com.iocoder.yudao.module.weboffice.controller;

import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.weboffice.bo.OnlineGenerateBaseBO;
import com.iocoder.yudao.module.weboffice.service.WebOfficeOnlineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * @author wu kai
 * @since 2022/10/13
 */
@Api(tags = "在线编辑 - 生成文件接口")
@RestController
@RequestMapping("/online/web-office/gen-file")
@Validated
public class WebOfficeOnlineController {

    @Resource
    WebOfficeOnlineService webOfficeOnlineService;

    @PostMapping("/onlineGenerateUserDoc")
    @ApiOperation(value = "在线生成用户信息文档")
    public CommonResult<OnlineGenerateBaseBO> onlineGenerateUserDoc() {
        return success(webOfficeOnlineService.onlineGenerateUserDoc());
    }
}
