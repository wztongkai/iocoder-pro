package com.iocoder.yudao.module.system.api.dict;


import com.iocoder.yudao.module.commons.annotation.Log;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.enums.BusinessType;
import com.iocoder.yudao.module.system.service.DictTypeService;
import com.iocoder.yudao.module.system.vo.dict.type.DictTypeBatchDeleteReqVO;
import com.iocoder.yudao.module.system.vo.dict.type.DictTypeCreateReqVO;
import com.iocoder.yudao.module.system.vo.dict.type.DictTypeUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 *
 * @author wu kai
 * @since 2022-06-30
 */
@Api(tags = "管理后台 - 字典类型")
@RestController
@RequestMapping("/system/dict-type")
@Validated
public class DictTypeController {

    @Resource
    DictTypeService dictTypeService;

    @Log(title = "字典类型",businessType = BusinessType.INSERT)
    @PostMapping("/create")
    @ApiOperation("创建字典类型")
    @PreAuthorize("@ss.hasPermission('system:dict:create')")
    public CommonResult<Long> createDictType(@Valid @RequestBody DictTypeCreateReqVO createReqVO) {
        return success(dictTypeService.createDictType(createReqVO));
    }

    @Log(title = "字典类型",businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    @ApiOperation("修改字典类型")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CommonResult<Boolean> updateDictType(@Valid @RequestBody DictTypeUpdateReqVO reqVO) {
        dictTypeService.updateDictType(reqVO);
        return success(true);
    }

    @Log(title = "字典类型",businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    @ApiOperation("删除字典类型")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public CommonResult<Boolean> deleteDictType(Long id) {
        dictTypeService.deleteDictType(id);
        return success(true);
    }

    @Log(title = "字典类型",businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    @DeleteMapping("/delete-dict-batch")
    @ApiOperation("批量删除字典类型")
    public CommonResult<Boolean> deleteDictTypeBatch(@Valid @RequestBody DictTypeBatchDeleteReqVO batchDeleteReqVO){
        dictTypeService.deleteDictTypeBatch(batchDeleteReqVO);
        return success(true);
    }

}
