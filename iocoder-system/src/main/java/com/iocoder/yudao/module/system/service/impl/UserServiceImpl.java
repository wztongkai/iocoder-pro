package com.iocoder.yudao.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.system.mapper.UserMapper;
import com.iocoder.yudao.module.system.service.DeptService;
import com.iocoder.yudao.module.system.service.UserService;
import com.iocoder.yudao.module.system.vo.user.UserCreateReqVO;
import com.iocoder.yudao.module.system.vo.user.UserPageQueryRequestVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.UserErrorCode.USER_NOT_EXISTS;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Resource
    DeptService deptService;

    @Override
    public PageResult<UserDO> selectUserList(UserPageQueryRequestVo requestVo) {

        return baseMapper.selectUserList(requestVo);
    }

    @Override
    public void updateUserLogin(Long userId, String loginIp) {
        baseMapper.update(null, new LambdaUpdateWrapper<UserDO>()
                .set(UserDO::getLoginIp, loginIp)
                .set(UserDO::getLoginDate, LocalDate.now())
                .eq(UserDO::getId, userId)
        );
    }

    @Override
    public Long createUser(UserCreateReqVO reqVO) {
        checkCreateOrUpdate(null,reqVO.getUsername(),reqVO.getMobile());
        return null;
    }

    private void checkCreateOrUpdate(Long id, String username, String mobile) {
        // 校验用户是否存在
        checkUserIdExist(id);
    }

    /**
     * 校验用户是否存在
     * @param id
     */
    private void checkUserIdExist(Long id) {
        if(id == null){
            return;
        }
        UserDO userDO = baseMapper.selectById(id);
        if(ObjectUtils.isEmpty(userDO)){
            throw ServiceExceptionUtil.exception(USER_NOT_EXISTS);
        }
    }


}
