package com.iocoder.yudao.system.service.impl;

import com.iocoder.yudao.system.domain.PostDO;
import com.iocoder.yudao.system.mapper.PostMapper;
import com.iocoder.yudao.system.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
