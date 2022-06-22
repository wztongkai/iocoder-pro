package com.iocoder.yudao.commons.core.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.iocoder.yudao.commons.core.domain.BaseEntity;
import com.iocoder.yudao.commons.utils.ServletUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * mybatis-plus 自动填充配置类
 *
 * @Author: kai wu
 * @Date: 2022/6/11 03:07
 */
@Component
public class AutoFillMetaInfoHandler implements MetaObjectHandler {

    /**
     * 插入元对象填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
            // 创建时间为空，以当前时间为创建时间
            if (Objects.isNull(baseEntity.getCreateTime())) {
                baseEntity.setCreateTime(LocalDateTime.now());
            }
            // 更新时间为空，以当前时间作为更新时间
            if (Objects.isNull(baseEntity.getUpdateTime())) {
                baseEntity.setUpdateTime(LocalDateTime.now());
            }

            Long userId = ServletUtils.getLoginUserId();
            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
            if (Objects.nonNull(userId) && Objects.isNull(baseEntity.getCreator())) {
                baseEntity.setCreator(userId.toString());
            }
            // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
            if (Objects.nonNull(userId) && Objects.isNull(baseEntity.getUpdater())) {
                baseEntity.setUpdater(userId.toString());
            }

        }
//        this.setFieldValByName(BaseEntity.Fields.createTime, LocalDateTime.now(), metaObject);
//        this.setFieldValByName(BaseEntity.Fields.updateTime, LocalDateTime.now(), metaObject);
    }

    /**
     * 更新元对象填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        // 更新时间为空，以当前时间为更新时间
        Object modifyTime = this.getFieldValByName("updateTime", metaObject);
        if (Objects.isNull(modifyTime)) {
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
//        this.setFieldValByName(BaseEntity.Fields.updateTime, LocalDateTime.now(), metaObject);
    }
}
