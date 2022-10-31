package com.iocoder.yudao.module.commons.utils.file;


import com.iocoder.yudao.module.commons.config.Assertion;
import com.iocoder.yudao.module.commons.config.iocoderConfig.IocoderConfig;
import com.iocoder.yudao.module.commons.constant.Constants;
import com.iocoder.yudao.module.commons.exception.file.FileNameLengthLimitExceededException;
import com.iocoder.yudao.module.commons.exception.file.FileSizeLimitExceededException;
import com.iocoder.yudao.module.commons.exception.file.InvalidExtensionException;
import com.iocoder.yudao.module.commons.utils.DateUtils;
import com.iocoder.yudao.module.commons.utils.StringUtils;
import com.iocoder.yudao.module.commons.utils.uuid.Seq;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Objects;

import static com.iocoder.yudao.module.commons.constant.Constants.FILE_SEPARATOR;

/**
 * 文件上传工具类
 *
 * @author wu kai
 */
@Configuration
@Component
@Slf4j
public class FileUploadUtils {

    @Resource
    IocoderConfig iocoderConfig;

    static public String ip;

    /**
     * 指定的资源服务器ip
     */
    @Value("${resourceServer.ip}")
    public void setIp(String ip) {
        FileUploadUtils.ip = ip;
    }

    static public Integer port;

    /**
     * 指定的资源服务器端口号
     */
    @Value("${resourceServer.port}")
    public void setPort(Integer port) {
        FileUploadUtils.port = port;
    }

    static public String user;

    /**
     * 指定的资源服务器用户名
     */
    @Value("${resourceServer.user}")
    public void setUser(String user) {
        FileUploadUtils.user = user;
    }

    static public String password;

    /**
     * 指定的资源服务器用户名
     */
    @Value("${resourceServer.password}")
    public void setPassword(String password) {
        FileUploadUtils.password = password;
    }

    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir;

    @PostConstruct
    public void getYmlParam() {
        defaultBaseDir = iocoderConfig.getProfile();
    }

    public static String getDefaultBaseDir() {
        return defaultBaseDir;
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */
    public static String upload(MultipartFile file) throws IOException {
        try {
            return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static String upload(String baseDir, MultipartFile file) throws IOException {
        try {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir          相对应用的基目录
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException       如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException                          比如读写文件出错时
     * @throws InvalidExtensionException            文件校验异常
     */
    public static String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException {
        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);

        String fileName = extractFilename(file);

        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        file.transferTo(Paths.get(absPath));
        return getPathFileName(baseDir, fileName);
    }

    /**
     * 编码文件名
     */
    public static String extractFilename(MultipartFile file) {
        return StringUtils.format("{}/{}_{}.{}", DateUtils.datePath(),
                FilenameUtils.getBaseName(file.getOriginalFilename()), Seq.getId(Seq.uploadSeqType), getExtension(file));
    }

    public static File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    public static String getPathFileName(String uploadDir, String fileName) throws IOException {
        int dirLastIndex = getDefaultBaseDir().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        return Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws InvalidExtensionException
     */
    public static void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException {
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION) {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
                        fileName);
            } else {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }

    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }

    /**
     * 获取文件流
     *
     * @param path 文件链接
     */
    public static InputStream getInputStream(String path) throws Exception {
        URL url = new URL(path);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }

    /**
     * sftp 上传文件
     * @param bytes 文件字节码
     * @param filePath 文件上传地址
     * @param fileName 文件名称
     */
    public static void fileUpload(byte[] bytes, String filePath, String fileName) {
        log.info("文件上传  -->  文件上传地址为：{}，文件名为：{} 开始上传", filePath + fileName, fileName);
        OutputStream outputStream;
        try {
            JSch jSch = new JSch();
            Session session;
            if (port <= Constants.ZERO) {
                // 采用默认端口连接服务器
                session = jSch.getSession(user, ip);
            } else {
                // 采用指定端口连接服务器
                session = jSch.getSession(user, ip, port);
            }
            Assertion.isNull(session, "服务器连接异常，SSH session 为空");
            //设置登陆主机的密码
            session.setPassword(password);
            //设置第一次登陆时的提示，可选值：(ask | yes | no)
            session.setConfig("StrictHostKeyChecking", "no");
            //设置登陆超时时间
            session.connect(30000);
            session.setTimeout(60000);
            // 获取sftp通道
            Channel channel = session.openChannel("sftp");
            channel.connect(60 * 1000);
            ChannelSftp channelSftp = (ChannelSftp) channel;
            if (!isDirExist(filePath, channelSftp)) {
                String[] dirs = filePath.split(FILE_SEPARATOR);
                String tempPath = "";
                int index = 0;
                // 不存在，创建目录
                mkdirDir(dirs, tempPath, dirs.length, index, channelSftp);
            }
            //进入服务器指定的文件夹
            channelSftp.cd(filePath);

            //以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
            outputStream = channelSftp.put(fileName);
            outputStream.write(bytes);

            // 关闭流
            outputStream.close();
            session.disconnect();
            channel.disconnect();
        } catch (JSchException | SftpException | IOException e) {
            log.error("文件上传失败，失败的文件名称为：{}，错误信息为：{}", fileName, e.getMessage());
            throw new RuntimeException("文件上传失败！");
        }

    }

    /**
     * 判断目录是否存在
     */
    public static boolean isDirExist(String directory, ChannelSftp sftp) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    /**
     * 递归根据路径创建文件夹
     *
     * @param dirs     根据 / 分隔后的数组文件夹名称
     * @param tempPath 拼接路径
     * @param length   文件夹的格式长度
     * @param index    数组下标
     * @return
     */
    public static void mkdirDir(String[] dirs, String tempPath, int length, int index, ChannelSftp sftp) {
        // 以"/a/b/c/d"为例按"/"分隔后,第0位是"";顾下标从1开始

        index++;
        if (index < length) {
            // 目录不存在，则创建文件夹
            tempPath += "/" + dirs[index];
        }
        try {
            log.info("检测目录[" + tempPath + "]");
            sftp.cd(tempPath);
            if (index < length) {
                mkdirDir(dirs, tempPath, length, index, sftp);
            }
        } catch (SftpException ex) {
            log.warn("创建目录[" + tempPath + "]");
            try {
                sftp.mkdir(tempPath);
                sftp.cd(tempPath);
            } catch (SftpException e) {
                e.printStackTrace();
                log.error("创建目录[" + tempPath + "]失败,异常信息[" + e.getMessage() + "]");
                throw new RuntimeException("创建目录[" + tempPath + "]失败,异常信息[" + e.getMessage() + "]");
            }
            log.info("进入目录[" + tempPath + "]");
            mkdirDir(dirs, tempPath, length, index, sftp);
        }
    }
}
