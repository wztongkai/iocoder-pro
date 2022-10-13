package com.iocoder.yudao.module.weboffice.service.impl;

import com.iocoder.yudao.module.commons.constant.Constants;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.enums.user.UserStatus;
import com.iocoder.yudao.module.commons.utils.file.FileUploadUtils;
import com.iocoder.yudao.module.commons.utils.file.FileUtils;
import com.iocoder.yudao.module.commons.utils.generate.WordUtils;
import com.iocoder.yudao.module.commons.utils.io.IoUtils;
import com.iocoder.yudao.module.system.mapper.UserMapper;
import com.iocoder.yudao.module.weboffice.bo.OnlineGenUserDocBO;
import com.iocoder.yudao.module.weboffice.bo.OnlineGenerateBaseBO;
import com.iocoder.yudao.module.weboffice.service.WebOfficeOnlineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.iocoder.yudao.module.commons.constant.Constants.ONE;

/**
 * @author wu kai
 * @since 2022/10/13
 */
@Service
@Slf4j
public class WebOfficeOnlineServiceImpl implements WebOfficeOnlineService {

    // 生成的word文件后缀
    public static final String GENERATE_WORD_SUFFIX = ".docx";

    @Value("${resourceServer.url}")
    private String RESOURCE_SERVER_URL;

    @Value("${resourceServer.absoluteURL}")
    private String resourceServerAbsoluteURL;

    @Value("${iocoder.name}")
    private String sysName;

    @Resource
    UserMapper userMapper;


    @Override
    public OnlineGenerateBaseBO onlineGenerateUserDoc() {
        // 组装文件生成数据
        Map<String, Object> dataMap = assemblyGenerateData();
        // 获取模板文件地址
        String templatePath = RESOURCE_SERVER_URL + this.getTemplatePath(ONE) + "用户信息导出模板.docx";
        // 生成的临时文件存储路径
        String temporaryPath = this.getTemporaryPath(ONE) + Constants.FILE_SEPARATOR;
        // 生成的文件名称
        String fileName = "用户信息" + System.currentTimeMillis();
        // 生成用户信息doc
        String temporaryFile = WordUtils.generateWordByTemplate(templatePath, temporaryPath, fileName, dataMap, "lblist");
        // 获取文件上传地址
        String uploadPath = getUploadPath(ONE);
        byte[] temporaryBytes = IoUtils.toByteArray(temporaryFile);
        // 页面显示文件名
        String dataName = "用户信息.docx";
        // 上传
        FileUploadUtils.fileUpload(temporaryBytes, resourceServerAbsoluteURL + uploadPath, fileName + GENERATE_WORD_SUFFIX);
        // 删除生成的临时文件
        FileUtils.deleteFile(temporaryFile);
        // 生成后文件的存储地址
        String dataAddress = uploadPath + fileName;
        // 组装返回数据并返回
        return OnlineGenerateBaseBO.builder().fileName(dataName).filePath(dataAddress).build();
    }

    private Map<String, Object> assemblyGenerateData() {
        // 获取开启状态的用户列表
        List<UserDO> userList = userMapper.selectList(new LambdaQueryWrapperX<UserDO>()
                .eq(UserDO::getStatus, UserStatus.OK.getCode())
                .orderByDesc(UserDO::getCreateTime)
        );
        Map<String, Object> dataMap = new HashMap<>();
        List<OnlineGenUserDocBO> onlineGenUserDocList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            UserDO userDO = userList.get(i);
            OnlineGenUserDocBO onlineGenUserDocBO = new OnlineGenUserDocBO();
            onlineGenUserDocBO.setXh(i + 1);
            onlineGenUserDocBO.setXm(userDO.getUsername());
            onlineGenUserDocBO.setNc(userDO.getNickname());
            onlineGenUserDocBO.setXb(Objects.equals(userDO.getSex(), ONE) ? "男" : "女");
            onlineGenUserDocBO.setYx(userDO.getEmail());
            onlineGenUserDocBO.setDh(userDO.getMobile());
            onlineGenUserDocList.add(onlineGenUserDocBO);
        }
        dataMap.put("lblist", onlineGenUserDocList);
        dataMap.put("rs", onlineGenUserDocList.size());
        dataMap.put("xtmc", sysName);
        return dataMap;
    }

    /**
     * 模板文件存放地址
     *
     * @param type 模板类型  1、用户信息导出模板
     * @return 模板文件存放地址
     */
    private String getTemplatePath(int type) {
        String templatePath = "";
        switch (type) {
            case 1:
                templatePath = "/GroupData/template/" + "guoji" + Constants.FILE_SEPARATOR;
                break;
            default:
                break;
        }
        return templatePath;
    }

    /**
     * 获取临时文件路径
     *
     * @param type 1、用户信息导出模板
     * @return 临时文件路径
     */
    private String getTemporaryPath(int type) {
        String temporaryPath = "";
        switch (type) {
            case 1:
                temporaryPath = "/GroupData/template/temporary/" + "guoji" + Constants.FILE_SEPARATOR;
                break;
            default:
                break;
        }
        if (StringUtils.isNotEmpty(temporaryPath)) {
            FileUtils.createFileDir(resourceServerAbsoluteURL + temporaryPath);
        }
        return temporaryPath;
    }

    /**
     * 获取服务器文件存储路径
     *
     * @param type 1、用户信息导出模板
     * @return 存储路径
     */
    private String getUploadPath(int type) {
        String filePath = "";
        switch (type) {
            case 1:
                filePath = "/GroupData/" + "guoji" + Constants.FILE_SEPARATOR;
                break;
            default:
                break;
        }
        if (StringUtils.isNotEmpty(filePath)) {
            FileUtils.createFileDir(resourceServerAbsoluteURL + filePath);
        }
        return filePath;
    }
}
