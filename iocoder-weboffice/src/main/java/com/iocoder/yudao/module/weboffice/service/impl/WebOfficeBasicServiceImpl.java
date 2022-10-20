package com.iocoder.yudao.module.weboffice.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.iocoder.yudao.module.commons.config.Assertion;
import com.iocoder.yudao.module.commons.constant.Constants;
import com.iocoder.yudao.module.commons.enums.FileTypeEnum;
import com.iocoder.yudao.module.commons.utils.file.FileUploadUtils;
import com.iocoder.yudao.module.file.domain.AnnexsDO;
import com.iocoder.yudao.module.file.service.AnnexsService;
import com.iocoder.yudao.module.weboffice.service.WebOfficeBasicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;

import static com.iocoder.yudao.module.commons.enums.db.DBMenuType.USER_INFO_ANNEX;

/**
 * @author wu kai
 * @since 2022/10/13
 */
@Service
@Slf4j
public class WebOfficeBasicServiceImpl implements WebOfficeBasicService {

    @Value("${resourceServer.url}")
    private String RESOURCE_SERVER_URL;

    @Value("${resourceServer.absoluteURL}")
    private String resourceServerAbsoluteURL;

    @Resource
    AnnexsService annexsService;

    @Override
    public Boolean onlineOpenFile(String fileUrl, HttpServletResponse response) {
        log.info("在线编辑 -- 打开链接为 {} 的文件中！", fileUrl);
        try {
            // 获取文件input流
            InputStream inputStream = FileUploadUtils.getInputStream(RESOURCE_SERVER_URL + fileUrl);
            // 将流转为byte数组
            byte[] bytes = IOUtils.toByteArray(inputStream);
            //用于输出响应流
            OutputStream out = response.getOutputStream();
            String fileName = "word.docx";
            //设置响应头信息
            //设置响应头信息，告诉浏览器不要缓存此内容
            //转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/octet-stream;charset=utf-8");
            //输出并关闭
            out.write(bytes);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("在线编辑 -- 打开链接为 {} 的文件失败，错误信息为：{}", fileUrl, e.getMessage());
            e.printStackTrace();
        }
        log.info("在线编辑 -- 链接为 {} 的文件已成功打开！", fileUrl);
        return Constants.success;
    }

    @Override
    public Boolean onlineSaveFile(HttpServletRequest request) {
        try {
            log.info("在线编辑 -- 保存word文件至服务器-----开始");
            // 从请求中获取参数
            Map<String, String[]> parameterMap = request.getParameterMap();
            // 获取文件
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
            Collection<MultipartFile> fileList = fileMap.values();
            Assertion.isNull(fileList, "文件不能为空");
            // 获取表单数据
            String[] formData = parameterMap.get("FormData");
            JSONObject jsonObject = JSONObject.parseObject(formData[0]);
            // 从表单数据中获取文件链接
            String fileUrl = jsonObject.getString("fileUrl");
            Assertion.isBlank(fileUrl, "需要保存的文件链接不能为空");
            log.info("在线编辑 -- 保存文件至资源服务器，文件链接为：{}", fileUrl);
            // 拼接完整的资源服务器路径
            String filePath = resourceServerAbsoluteURL + fileUrl.replace(RESOURCE_SERVER_URL, "");

            File file = new File(filePath);
            String fileName = file.getName();
            // 去掉路径中的文件名
            filePath = filePath.replace(fileName, "");

            // 获取文件byte数组
            byte[] bytes = null;

            for (MultipartFile multipartFile : fileList) {
                bytes = multipartFile.getBytes();
            }
            FileUploadUtils.fileUpload(bytes, filePath, fileName);
            log.info("在线编辑word --> 保存，保存文件成功，存放地址为：{}", filePath);
        } catch (IOException e) {
            log.error("在线编辑word --> 保存，保存文件至服务器失败，错误信息为：{}", e.getMessage());
            throw new RuntimeException("在线编辑word --> 保存，保存文件至服务器失败！");
        }
        return Constants.success;
    }

    @Override
    public Boolean onlineSaveFilePDF(HttpServletRequest request) {
        try {
            log.info("在线编辑 -- word文件保存为PDF文件开始");
            // 从请求中获取参数
            Map<String, String[]> parameterMap = request.getParameterMap();
            // 获取文件
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
            Collection<MultipartFile> fileList = fileMap.values();
            Assertion.isNull(fileList, "文件不能为空");
            // 获取表单数据
            String[] formData = parameterMap.get("FormData");
            JSONObject jsonObject = JSONObject.parseObject(formData[0]);
            // 从表单数据中获取文件链接
            String fileUrl = jsonObject.getString("fileUrl");
            // 字典编码
            String dictCode = jsonObject.getString("dictCode");
            String dataName = jsonObject.getString("dataName");
            Assertion.isBlank(fileUrl, "需要保存的文件链接不能为空");
            log.info("在线编辑 -- 保存PDF文件至资源服务器，文件链接为：{}", fileUrl);
            // 拼接完整的资源服务器路径
            String filePath = resourceServerAbsoluteURL + fileUrl.replace(RESOURCE_SERVER_URL, "");
            File file = new File(filePath);
            String fileName = file.getName();
            // 去掉路径中的文件名
            filePath = filePath.replace(fileName, "");
            fileName = fileName.replace(".docx", ".pdf").replace(".doc", ".pdf");
            // 获取文件byte数组
            byte[] bytes = null;

            for (MultipartFile multipartFile : fileList) {
                bytes = multipartFile.getBytes();
            }
            FileUploadUtils.fileUpload(bytes, filePath, fileName);
            log.info("在线编辑word --> word转化为PDF成功，存放地址为：{}", filePath);
            AnnexsDO annexsDO = annexsService.getAnnexByDictCodeAndAnnexType(dictCode, FileTypeEnum.PDF.getCode());
            if (ObjectUtils.isEmpty(annexsDO)) {
                annexsService.insertAnnex(AnnexsDO.builder().annexName(dataName+".pdf").annexType(FileTypeEnum.PDF.getCode()).annexCode(USER_INFO_ANNEX.getCode()).annexAddress(filePath.replace(resourceServerAbsoluteURL, "")+fileName).build());
            } else {
                annexsDO.setAnnexAddress(filePath.replace(resourceServerAbsoluteURL, "")+fileName);
                annexsService.updateById(annexsDO);
            }
        } catch (Exception e) {
            log.error("在线编辑word --> word转化为PDF失败，错误信息为：{}", e.getMessage());
            throw new RuntimeException("在线编辑word --> word转化为PDF失败！");
        }
        return Constants.success;
    }
}
