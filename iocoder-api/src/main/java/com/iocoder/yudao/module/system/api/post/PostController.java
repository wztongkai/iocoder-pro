package com.iocoder.yudao.module.system.api.post;


import com.iocoder.yudao.module.commons.annotation.Log;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.enums.BusinessType;
import com.iocoder.yudao.module.commons.enums.common.CommonStatusEnum;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.system.service.PostService;
import com.iocoder.yudao.module.system.vo.post.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * <p>
 * 岗位信息表 前端控制器
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Api(tags = "管理后台 - 岗位")
@RestController
@RequestMapping("/system/post")
@Validated
public class PostController {

    @Resource
    PostService postService;

    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermission('system:post:create')")
    @PostMapping("create")
    @ApiOperation("创建岗位")
    public CommonResult<Long> createPost(@Valid @RequestBody PostCreateReqVO createReqVO) {
        return success(postService.createPost(createReqVO));
    }

    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermission('system:post:update')")
    @PutMapping("update")
    @ApiOperation("更新岗位")
    public CommonResult<Boolean> updatePost(@Valid @RequestBody PostUpdateReqVO updateReqVO) {
        postService.updatePost(updateReqVO);
        return success(true);
    }

    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermission('system:post:update-status')")
    @PutMapping("/update-status")
    @ApiOperation("修改岗位状态")
    public CommonResult<Boolean> updatePostStatus(@Valid @RequestBody PostUpdateStatusReqVO updateStatusReqVO) {
        postService.updatePostStatus(updateStatusReqVO);
        return success(true);
    }

    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermission('system:post:delete')")
    @DeleteMapping("delete")
    @ApiOperation("删除岗位")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<Boolean> deletePost(@RequestParam("id") Long id) {
        postService.deletePost(id);
        return success(true);
    }

    @Log(title = "岗位管理",businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermission('system:post:delete')")
    @DeleteMapping("/delete-post-batch")
    @ApiOperation("批量删除岗位")
    public CommonResult<Boolean> deletePostBatch(@Valid @RequestBody PostBatchDeleteReqVO batchDeleteReqVO){
        postService.deletePostBatch(batchDeleteReqVO);
        return success(true);
    }

    @PreAuthorize("@ss.hasPermission('system:post:list')")
    @GetMapping("/list")
    @ApiOperation("获取岗位列表")
    public CommonResult<List<PostRespVO>> listPosts(PostListReqVO listReqVO) {
        List<PostRespVO> list = postService.getSimplePosts(listReqVO);
        return success(list);
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取岗位精简信息列表", notes = "只包含被开启的岗位，主要用于前端的下拉选项")
    public CommonResult<List<PostSimpleRespVO>> getSimplePost() {
        // 获得岗位列表，只要开启状态的
        PostListReqVO reqVO = new PostListReqVO();
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<PostRespVO> list = postService.getSimplePosts(reqVO);
        List<PostSimpleRespVO> simpleRespList = new ArrayList<>();
        BeanUtil.copyListProperties(list, simpleRespList, PostSimpleRespVO.class);
        return success(simpleRespList);
    }

    @GetMapping("/get-info")
    @ApiOperation("获得岗位信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<PostRespVO> getPostInfo(@RequestParam("id") Long id) {
        return success(postService.getPostInfo(id));
    }
}
