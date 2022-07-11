package com.iocoder.yudao.module.commons.constant;


import com.iocoder.yudao.module.commons.exception.ErrorCode;

/**
 * 错误码枚举类
 *
 * @author wu kai
 * @date 2022/6/16
 */
public interface ErrorCodeConstants {

    ErrorCode REQ_ARGS_NOT_NULL = new ErrorCode(1001001000, "请求参数不能为空");
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

    interface PostErrorCode {
        ErrorCode POST_NOT_FOUND = new ErrorCode(1002005000, "当前岗位不存在");
        ErrorCode POST_NOT_ENABLE = new ErrorCode(1002005001, "岗位({}) 不处于开启状态，不允许选择");
        ErrorCode POST_NAME_DUPLICATE = new ErrorCode(1002005002, "已经存在该名字的岗位");
        ErrorCode POST_CODE_DUPLICATE = new ErrorCode(1002005003, "已经存在该标识的岗位");
    }

    interface FileErrorCode {
        ErrorCode FILE_PATH_EXISTS = new ErrorCode(1001003000, "文件路径已存在");
        ErrorCode FILE_NOT_EXISTS = new ErrorCode(1001003001, "文件不存在");
        ErrorCode FILE_IS_EMPTY = new ErrorCode(1001003002, "文件为空");
        ErrorCode FILE_UPDATE_EXCEPTION = new ErrorCode(1001003003, "图片上传异常，请联系管理员");
    }

    interface RoleErrorCode{
        ErrorCode ROLE_NOT_EXISTS = new ErrorCode(1002002000, "角色不存在");
        ErrorCode ROLE_NAME_DUPLICATE = new ErrorCode(1002002001, "已经存在名为【{}】的角色");
        ErrorCode ROLE_CODE_DUPLICATE = new ErrorCode(1002002002, "已经存在编码为【{}】的角色");
        ErrorCode ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE = new ErrorCode(1002002003, "不能操作类型为系统内置的角色");
        ErrorCode ROLE_IS_DISABLE = new ErrorCode(1002002004, "名字为【{}】的角色已被禁用");
        ErrorCode ROLE_ADMIN_CODE_ERROR = new ErrorCode(1002002005, "编码【{}】不能使用");
    }

    interface MenuErrorCode{
        ErrorCode MENU_NAME_DUPLICATE = new ErrorCode(1002001000, "已经存在该名字的菜单");
        ErrorCode MENU_PARENT_NOT_EXISTS = new ErrorCode(1002001001, "父菜单不存在");
        ErrorCode MENU_PARENT_ERROR = new ErrorCode(1002001002, "不能设置自己为父菜单");
        ErrorCode MENU_NOT_EXISTS = new ErrorCode(1002001003, "菜单不存在");
        ErrorCode MENU_EXISTS_CHILDREN = new ErrorCode(1002001004, "存在子菜单，无法删除");
        ErrorCode MENU_PARENT_NOT_DIR_OR_MENU = new ErrorCode(1002001005, "父菜单的类型必须是目录或者菜单");
    }

    interface LogErrorCode{
        ErrorCode LOG_NOT_FOUND = new ErrorCode(1002006000, "日志信息不存在");
    }

    interface DictErrorCode{
        // ========================== 字典数据 ============================
        ErrorCode DICT_DATA_NOT_EXISTS = new ErrorCode(1002007001, "当前字典数据不存在");
        ErrorCode DICT_DATA_NOT_ENABLE = new ErrorCode(1002007002, "字典数据({})不处于开启状态，不允许选择");
        ErrorCode DICT_DATA_VALUE_DUPLICATE = new ErrorCode(1002007003, "已经存在该值的字典数据");

        // ========================== 字典类型 ============================
        ErrorCode DICT_TYPE_NOT_EXISTS = new ErrorCode(1002006001, "当前字典类型不存在");
        ErrorCode DICT_TYPE_NOT_ENABLE = new ErrorCode(1002006002, "字典类型不处于开启状态，不允许选择");
        ErrorCode DICT_TYPE_NAME_DUPLICATE = new ErrorCode(1002006003, "已经存在该名字的字典类型");
        ErrorCode DICT_TYPE_TYPE_DUPLICATE = new ErrorCode(1002006004, "已经存在该类型的字典类型");
        ErrorCode DICT_TYPE_HAS_CHILDREN = new ErrorCode(1002006005, "无法删除，该字典类型还有字典数据");
    }

    ErrorCode MODEL_KEY_EXISTS = new ErrorCode(1009002000, "已经存在流程标识为【{}】的流程");


}
