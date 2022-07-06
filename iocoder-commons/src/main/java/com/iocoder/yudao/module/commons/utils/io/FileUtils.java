package com.iocoder.yudao.module.commons.utils.io;

import cn.hutool.core.io.FileUtil;
import com.iocoder.yudao.module.commons.utils.uuid.IdUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * 文件工具类
 *
 * @author wu kai
 * @date 2022/6/14
 */
@Slf4j
public class FileUtils {
    /**
     * 创建临时文件
     * 该文件会在 JVM 退出时，进行删除
     *
     * @param data 文件内容
     * @return 文件
     */
    @SneakyThrows
    public static File createTempFile(String data) {
        File file = createTempFile();
        // 写入内容
        FileUtil.writeUtf8String(data, file);
        return file;
    }

    /**
     * 创建临时文件
     * 该文件会在 JVM 退出时，进行删除
     *
     * @param data 文件内容
     * @return 文件
     */
    @SneakyThrows
    public static File createTempFile(byte[] data) {
        File file = createTempFile();
        // 写入内容
        FileUtil.writeBytes(data, file);
        return file;
    }

    /**
     * 创建临时文件，无内容
     * 该文件会在 JVM 退出时，进行删除
     *
     * @return 文件
     */
    @SneakyThrows
    public static File createTempFile() {
        // 创建文件，通过 UUID 保证唯一
        File file = File.createTempFile(IdUtils.simpleUUID(), null);
        // 标记 JVM 退出时，自动删除
        file.deleteOnExit();
        return file;
    }

    /**
     * 创建目录
     * @param fileDir 目录地址
     */
    public static void createFileDir(String fileDir) {
        File dir = new File(fileDir);
        if (!dir.exists()) {
            log.info("文件目录{}不存在,目录创建中!", fileDir);
            dir.mkdirs();
        }
    }
}
