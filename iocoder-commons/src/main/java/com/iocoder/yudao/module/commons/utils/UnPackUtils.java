package com.iocoder.yudao.module.commons.utils;

import cn.hutool.core.util.ObjectUtil;
import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 压缩文件解压工具类
 * @author wu kai
 * @since 2022/9/23
 */
@Slf4j
public class UnPackUtils {

    /**
     * 解压 rar 格式压缩文件
     */
    public static void unPackRar(File rarFile, String destPath) {
        try (Archive archive = new Archive(rarFile)) {
            if (ObjectUtil.isNotEmpty(archive)) {
                FileHeader fileHeader = archive.nextFileHeader();
                File file;
                while (null != fileHeader) {
                    // 防止文件名中文乱码问题的处理
                    String fileName = fileHeader.getFileNameW().isEmpty() ? fileHeader.getFileNameString() : fileHeader.getFileNameW();
                    if (fileHeader.isDirectory()) {
                        //是文件夹
                        file = new File(destPath + File.separator + fileName);
                        file.mkdirs();
                    } else {
                        //不是文件夹
                        file = new File(destPath + File.separator + fileName.trim());
                        if (!file.exists()) {
                            if (!file.getParentFile().exists()) {
                                // 相对路径可能多级，可能需要创建父目录.
                                file.getParentFile().mkdirs();
                            }
                            file.createNewFile();
                        }
                        FileOutputStream os = new FileOutputStream(file);
                        archive.extractFile(fileHeader, os);
                        os.close();
                    }
                    fileHeader = archive.nextFileHeader();
                }
            }
        } catch (Exception e) {
            log.error("解压rar文件失败，错误信息为：{}", e.getMessage());
        }
    }

    /**
     * 解压ZIP格式压缩包
     */
    public static String unPackZip(String filePath, String destPath) {
        String name = "";
        try {
            BufferedOutputStream dest;
            BufferedInputStream is;
            ZipEntry entry;
            ZipFile zipfile = new ZipFile(filePath, Charset.forName("GBK"));

            Enumeration<? extends ZipEntry> dir = zipfile.entries();
            while (dir.hasMoreElements()) {
                entry = dir.nextElement();

                if (entry.isDirectory()) {
                    name = entry.getName();
                    name = name.substring(0, name.length() - 1);
                    File fileObject = new File(destPath + name);
                    fileObject.mkdir();
                }
            }

            Enumeration<? extends ZipEntry> e = zipfile.entries();
            while (e.hasMoreElements()) {
                entry = e.nextElement();
                if (!entry.isDirectory()) {
                    is = new BufferedInputStream(zipfile.getInputStream(entry));
                    int count;
                    int BUFFER = 1024;
                    byte[] dataByte = new byte[BUFFER];
                    FileOutputStream fos = new FileOutputStream(destPath + entry.getName());
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = is.read(dataByte, 0, BUFFER)) != -1) {
                        dest.write(dataByte, 0, count);
                    }
                    dest.flush();
                    fos.close();
                    dest.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            log.error("解压ZIP文件失败，错误信息为：{}", e.getMessage());
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 解压缩7z文件
     * @param file 压缩包文件
     * @param destPath 目标文件夹
     */
    public static void unPack7z(File file, String destPath){
        try (SevenZFile sevenZFile = new SevenZFile(file)) {
            byte[] buffer = new byte[4096];
            SevenZArchiveEntry entry;
            while ((entry = sevenZFile.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }

                File outputFile = new File(destPath + entry.getName());

                if (!outputFile.getParentFile().exists()) {
                    outputFile.getParentFile().mkdirs();
                }

                try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                    while (sevenZFile.read(buffer) > 0) {
                        fos.write(buffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
