package com.iocoder.yudao.module.system.api.city;


import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.system.service.CityService;
import com.iocoder.yudao.module.system.vo.city.CitySimpleRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * <p>
 * 中国城市表 前端控制器
 * </p>
 *
 * @author wu kai
 * @since 2022-10-14
 */
@Api(tags = "管理后台 - 城市")
@RestController
@RequestMapping("/system/city")
@Validated
public class CityController {

    @Resource
    CityService cityService;

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取次城市信息列表", notes = "查询该城市的所有下级城市信息 查询陕西省所有的市，主要用于前端的下拉选项")
    @ApiImplicitParam(name = "id", value = "城市编号", required = true, example = "0", dataTypeClass = Long.class)
    public CommonResult<List<CitySimpleRespVO>> getSimpleCity(@RequestParam("id") Long id) {
        return success(cityService.getSimpleCity(id));
    }

}
