package com.iocoder.yudao.module.leave;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wu kai
 * @since 2022/7/12
 */
@Api(tags = "管理后台 - 请假流程相关接口")
@RestController
@RequestMapping("/bpm/leave-process")
@Validated
public class LeaveProcessController {

}
