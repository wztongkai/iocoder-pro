package com.iocoder.yudao.module.system.api.dict;


import com.iocoder.yudao.module.commons.annotation.Log;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.enums.BusinessType;
import com.iocoder.yudao.module.system.service.DictDataService;
import com.iocoder.yudao.module.system.vo.dict.data.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author wu kai
 * @since 2022-06-30
 */
@Api(tags = "管理后台 - 字典数据")
@RestController
@RequestMapping("/system/dict-data")
@Validated
public class DictDataController {

    @Resource
    DictDataService dictDataService;

    @Log(title = "字典数据",businessType = BusinessType.INSERT)
    @PostMapping("/create")
    @ApiOperation("新增字典数据")
    @PreAuthorize("@ss.hasPermission('system:dict:create')")
    public CommonResult<Long> createDictData(@Valid @RequestBody DictDataCreateReqVO createReqVO) {
        return success(dictDataService.createDictData(createReqVO));
    }

    @Log(title = "字典数据",businessType = BusinessType.UPDATE)
    @PutMapping("update")
    @ApiOperation("修改字典数据")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CommonResult<Boolean> updateDictData(@Valid @RequestBody DictDataUpdateReqVO reqVO) {
        dictDataService.updateDictData(reqVO);
        return success(true);
    }

    @Log(title = "字典数据管理",businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermission('system:dict:update-status')")
    @PutMapping("/update-status")
    @ApiOperation("修改字典数据状态")
    public CommonResult<Boolean> updateDictDataStatus(@Valid @RequestBody DictDataUpdateStatusReqVO updateStatusReqVO) {
        dictDataService.updateDictDataStatus(updateStatusReqVO);
        return success(true);
    }

    @Log(title = "字典数据",businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    @ApiOperation("删除字典数据")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public CommonResult<Boolean> deleteDictData(Long id) {
        dictDataService.deleteDictData(id);
        return success(true);
    }

    @Log(title = "字典数据",businessType = BusinessType.DELETE)
    @DeleteMapping("/delete-data-batch")
    @ApiOperation("批量删除字典数据")
    public CommonResult<Boolean> deleteDictDataBatch(@Valid @RequestBody DictDataBatchDeleteReqVO batchDeleteReqVO){
        dictDataService.deleteDictDataBatch(batchDeleteReqVO);
        return success(true);
    }

    @Log(title = "字典数据",businessType = BusinessType.SELECT)
    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得全部字典数据列表", notes = "一般用于管理后台缓存字典数据在本地")
    public CommonResult<List<DictDataSimpleRespVO>> getSimpleDictDatas() {
        List<DictDataSimpleRespVO> list = dictDataService.getDictDatas();
        return success(list);
    }

    @Log(title = "字典数据",businessType = BusinessType.SELECT)
    @GetMapping("/page")
    @ApiOperation("/获得字典类型的分页列表")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<PageResult<DictDataRespVO>> getDictTypePage(@Valid DictDataPageReqVO pageReqVO) {
        return success(dictDataService.getDictDataPage(pageReqVO));
    }

    @Log(title = "字典数据",businessType = BusinessType.SELECT)
    @GetMapping(value = "/get-info")
    @ApiOperation("/查询字典数据详细")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<DictDataRespVO> getDictData(@RequestParam("id") Long id) {
        return success(dictDataService.getDictData(id));
    }

    @Log(title = "字典数据",businessType = BusinessType.SELECT)
    @GetMapping(value = "/type/{dictType}")
    @ApiOperation("根据字典类型查询对应的数据字典列表")
    @ApiImplicitParam(name = "dictType", value = "字典类型", required = true, example = "system_user_sex", dataTypeClass = String.class)
    public CommonResult<List<DictDataSimpleRespVO>> getDictDataListByDictType(@PathVariable("dictType") String dictType){
        List<DictDataSimpleRespVO> list = dictDataService.getDictDataListByDictType(dictType);
        return success(list);
    }
}
