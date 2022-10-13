package com.iocoder.yudao.module.weboffice.controller;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wu kai
 * @since 2022/10/13
 */
@Api(tags = "在线编辑 - 生成文件接口")
@RestController
@RequestMapping("/online/web-office/gen-file")
@Validated
public class WebOfficeOnlineController {
}
