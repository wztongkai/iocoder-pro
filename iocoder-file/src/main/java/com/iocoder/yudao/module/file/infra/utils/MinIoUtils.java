package com.iocoder.yudao.module.file.infra.utils;

import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MinIoUtils {

    @Resource
    private MinioClient client;

    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 判断bucket是否存在，不存在则创建
     * @param name bucket名称
     */
    public void existBucket(String name) {
        try {
            boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(name).build());
            if (!exists) {
                client.makeBucket(MakeBucketArgs.builder().bucket(name).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建存储bucket
     * @param bucketName  存储bucket名称
     * @return 创建结果
     */
    public Boolean makeBucket(String bucketName) {
        try {
            client.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除存储bucket
     * @param bucketName    存储bucket名称
     * @return 删除结果
     */
    public Boolean removeBucket(String bucketName) {
        try {
            client.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 上传文件
     * @param multipartFile 文件
     * @return
     */
    public String upload(MultipartFile multipartFile) {

        String fileName = multipartFile.getOriginalFilename();
        String[] split = fileName.split("\\.");
        if (split.length > 1) {
            fileName = split[0] + "_" + System.currentTimeMillis() + "." + split[1];
        } else {
            fileName = fileName + System.currentTimeMillis();
        }
        InputStream in = null;
        try {
            in = multipartFile.getInputStream();
            client.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(in, in.available(), -1)
                    .contentType(multipartFile.getContentType())
                    .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileName;
    }

    /**
     * 下载文件
     * @param response
     * @param bucketName    存储bucket名称
     * @param minFileName   文件名称
     */
    public void download(HttpServletResponse response, String bucketName, String minFileName) {
        InputStream fileInputStream = null;
        try {
            fileInputStream = client.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(minFileName).build());
            response.setHeader("Content-Disposition", "attachment;filename=" + minFileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error("文件下载异常！" + e);
        }
    }


    /**
     * 批量删除文件对象
     * @param bucketName    存储bucket名称
     * @param objects       对象名称集合
     */
    public Iterable<Result<DeleteError>> removeObjects(String bucketName, List<String> objects) {
        List<DeleteObject> dos = objects.stream().map(e -> new DeleteObject(e)).collect(Collectors.toList());
        Iterable<Result<DeleteError>> results = client.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(dos).build());
        return results;
    }

    /**
     * 生成上传的文件名
     * @param originalFileName
     * @return
     */
    private String minFileName(String originalFileName) {
        String suffix = originalFileName;
        if (originalFileName.contains(".")) {
            suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return UUID.randomUUID().toString().replace("-", "").toUpperCase() + suffix;
    }

}
