package com.iocoder.yudao.module.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.mapper.PostMapper;
import com.iocoder.yudao.module.system.service.PostService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 岗位信息表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, PostDO> implements PostService {

    @Override
    public List<PostDO> getSimplePosts(String[] postIds) {
        List<PostDO> doList = baseMapper.selectList(new LambdaQueryWrapperX<PostDO>()
                .inIfPresent(PostDO::getId,postIds)
        );
        if(CollectionUtils.isEmpty(doList)){
            return Collections.emptyList();
        }
        return doList;
    }
}
