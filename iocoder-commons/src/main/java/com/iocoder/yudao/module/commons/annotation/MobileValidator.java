package com.iocoder.yudao.module.commons.annotation;


import com.iocoder.yudao.module.commons.utils.StringUtils;
import com.iocoder.yudao.module.commons.utils.validation.ValidationUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public void initialize(Mobile annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果手机号为空，默认不校验，即校验通过
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        // 校验手机
        return ValidationUtils.isMobile(value);
    }

}
