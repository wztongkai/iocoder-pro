package com.iocoder.yudao.module.commons.exception.file;


import com.iocoder.yudao.module.commons.exception.base.BaseException;

/**
 * 文件信息异常类
 *
 * @author wu kai
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
