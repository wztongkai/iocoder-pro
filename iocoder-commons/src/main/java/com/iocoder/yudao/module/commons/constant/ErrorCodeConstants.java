package com.iocoder.yudao.module.commons.constant;


import com.iocoder.yudao.module.commons.exception.ErrorCode;

/**
 * 错误码枚举类
 *
 * @author wu kai
 * @date 2022/6/16
 */
public interface ErrorCodeConstants {
    /**
     * 用户错误码
     */
    interface UserErrorCode {
        ErrorCode USER_USERNAME_EXISTS = new ErrorCode(1002003000, "用户账号已经存在");
        ErrorCode USER_MOBILE_EXISTS = new ErrorCode(1002003001, "手机号已经存在");
        ErrorCode USER_EMAIL_EXISTS = new ErrorCode(1002003002, "邮箱已经存在");
        ErrorCode USER_NOT_EXISTS = new ErrorCode(1002003003, "用户不存在");
        ErrorCode USER_EXISTS = new ErrorCode(1002003004, "用户不存在");
        ErrorCode USER_IMPORT_LIST_IS_EMPTY = new ErrorCode(1002003005, "导入用户数据不能为空！");
        ErrorCode USER_PASSWORD_FAILED = new ErrorCode(1002003006, "用户密码校验失败");
        ErrorCode USER_IS_DISABLE = new ErrorCode(1002003007, "名字为【{}】的用户已被禁用");
    }

    interface DeptErrorCode {
        ErrorCode DEPT_NAME_DUPLICATE = new ErrorCode(1002004000, "已经存在该名字的部门");
        ErrorCode DEPT_PARENT_NOT_EXITS = new ErrorCode(1002004001, "父级部门不存在");
        ErrorCode DEPT_NOT_FOUND = new ErrorCode(1002004002, "当前部门不存在");
        ErrorCode DEPT_EXITS_CHILDREN = new ErrorCode(1002004003, "存在子部门，无法删除");
        ErrorCode DEPT_PARENT_ERROR = new ErrorCode(1002004004, "不能设置自己为父部门");
        ErrorCode DEPT_EXISTS_USER = new ErrorCode(1002004005, "部门中存在员工，无法删除");
        ErrorCode DEPT_NOT_ENABLE = new ErrorCode(1002004006, "部门不处于开启状态，不允许选择");
        ErrorCode DEPT_PARENT_IS_CHILD = new ErrorCode(1002004007, "不能设置自己的子部门为父部门");
    }

    interface AuthCode {
        ErrorCode AUTH_LOGIN_CAPTCHA_NOT_FOUND = new ErrorCode(1002000003, "验证码不存在");
        ErrorCode AUTH_LOGIN_CAPTCHA_CODE_ERROR = new ErrorCode(1002000004, "验证码不正确");
        ErrorCode AUTH_LOGIN_CAPTCHA_ISENABLE_ERROR = new ErrorCode(1002000004, "验证码未开启");
        ErrorCode AUTH_LOGIN_CAPTCHA_GRN_ERROR = new ErrorCode(1002000004, "验证码未开启");
        ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1002000000, "登录失败，账号密码不正确");
        ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1002000001, "登录失败，账号被禁用");
    }

}
