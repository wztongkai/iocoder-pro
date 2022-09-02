package com.iocoder.yudao.module.system.mapper;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.mapper.BaseMapperX;
import com.iocoder.yudao.module.commons.enums.login.LoginResultEnum;
import com.iocoder.yudao.module.system.domain.LoginLogDO;
import com.iocoder.yudao.module.system.vo.logs.loginlog.LoginLogPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 系统访问记录 Mapper 接口
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Mapper
public interface LoginLogMapper extends BaseMapperX<LoginLogDO> {

    /**
     * 分页查询登录日志
     *
     * @param pageReqVO 筛选条件
     * @return 日志列表
     */
    default PageResult<LoginLogDO> selectLoginLogPage(LoginLogPageReqVO pageReqVO) {

        LambdaQueryWrapperX<LoginLogDO> queryWrapperX = new LambdaQueryWrapperX<LoginLogDO>()
                .likeIfPresent(LoginLogDO::getUserIp, pageReqVO.getSearch())
                .orIfPresent()
                .likeIfPresent(LoginLogDO::getUsername, pageReqVO.getSearch())
                .betweenIfPresent(LoginLogDO::getCreateTime, pageReqVO.getCreateTime());
        if (Boolean.TRUE.equals(pageReqVO.getStatus())) {
            queryWrapperX.eq(LoginLogDO::getResult, LoginResultEnum.SUCCESS.getResult());
        } else if (Boolean.FALSE.equals(pageReqVO.getStatus())) {
            queryWrapperX.gt(LoginLogDO::getResult, LoginResultEnum.SUCCESS.getResult());
        } else {
            queryWrapperX.ge(LoginLogDO::getResult, LoginResultEnum.SUCCESS.getResult());
        }
        queryWrapperX.orderByDesc(LoginLogDO::getCreateTime);
        return selectPage(pageReqVO, queryWrapperX);
    }

    /**
     * 清空登录日志表
     */
    @Update("truncate table system_login_log")
    void truncateLogs();
}
