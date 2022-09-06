package com.iocoder.yudao.module.file.infra.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.iocoder.yudao.module.commons.constant.Constants;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.enums.db.DBMenuType;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.utils.DateUtils;
import com.iocoder.yudao.module.commons.utils.JdbcUtils;
import com.iocoder.yudao.module.file.domain.AnnexHistoricalDO;
import com.iocoder.yudao.module.file.domain.AnnexsDO;
import com.iocoder.yudao.module.file.domain.DbAnnexsDO;
import com.iocoder.yudao.module.file.infra.service.DatabaseDocService;
import com.iocoder.yudao.module.file.mapper.AnnexHistoricalMapper;
import com.iocoder.yudao.module.file.mapper.AnnexsMapper;
import com.iocoder.yudao.module.file.mapper.DbAnnexsMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

/**
 * @author wu kai
 * @since 2022/9/6
 */
@Slf4j
@Service
public class DatabaseDocServiceImpl implements DatabaseDocService {

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Resource
    AnnexsMapper annexsMapper;

    @Resource
    DbAnnexsMapper dbAnnexsMapper;

    @Resource
    AnnexHistoricalMapper historicalMapper;

    @Resource
    JdbcUtils jdbcUtils;

    private static final String FILE_OUTPUT_DIR = System.getProperty("java.io.tmpdir") + File.separator
            + "db-doc";
    private static final String DOC_FILE_NAME = "数据库文档";
    private static final String DOC_DESCRIPTION = "文档描述";

    @Override
    public String doExportFile(EngineFileType engineFileType, Integer type) {
        String DOC_VERSION = "v1.0_" + DateUtils.dateTime();
        // 获取当前数据库名称
        HashMap<String, String> dbInfo = jdbcUtils.getDBInfo();
        String dbName = dbInfo.get("dbName");
        String content = "";
        // 是否已经生成过文档
        DbAnnexsDO dbAnnexsDO = dbAnnexsMapper.selectOne(new LambdaQueryWrapperX<DbAnnexsDO>()
                .eq(DbAnnexsDO::getDbName, dbName)
                .eq(DbAnnexsDO::getAnnexType, type)
                .orderByDesc(DbAnnexsDO::getCreateTime).last("limit 1")
        );

        if (ObjectUtils.isNotEmpty(dbAnnexsDO)) {
            // 获取已生成的附件信息
            AnnexsDO annexsDO = annexsMapper.selectOne(new LambdaQueryWrapperX<AnnexsDO>()
                    .eq(AnnexsDO::getId, dbAnnexsDO.getDbAnnexId())
                    .eq(AnnexsDO::getAnnexType, type)
                    .orderByDesc(AnnexsDO::getCreateTime).last("limit 1")
            );
            AnnexHistoricalDO annexHistoricalDO = historicalMapper.selectOne(new LambdaQueryWrapperX<AnnexHistoricalDO>()
                    .eq(AnnexHistoricalDO::getAnnexId, annexsDO.getId())
                    .eq(AnnexHistoricalDO::getAnnexType, type)
                    .orderByDesc(AnnexHistoricalDO::getCreateTime).last("limit 1")
            );
            if (ObjectUtils.isNotEmpty(annexHistoricalDO)) {
                String annexVersion = annexHistoricalDO.getAnnexVersion();
                Integer oldVersion = Integer.valueOf(String.valueOf(annexVersion.charAt(annexVersion.length() - 12)));
                String version = String.valueOf(oldVersion + 1);
                StringBuilder dataNameBuilder = new StringBuilder(annexVersion);
                dataNameBuilder.replace(annexVersion.length() - 12, annexVersion.length() - 11, version);
                DOC_VERSION = dataNameBuilder.toString();
            }
            String docFileName = DOC_FILE_NAME + DOC_VERSION + "_" + System.currentTimeMillis();
            String filePath = doExportFile(engineFileType, docFileName, DOC_VERSION);

            byte[] bytes = FileUtil.readBytes(filePath);
            Base64.Encoder encoder = Base64.getMimeEncoder();
            content = encoder.encodeToString(bytes);
            // 更新附件基本信息
            annexsDO.setAnnexAddress(filePath);
            annexsDO.setAnnexContent(content);
            annexsDO.setAnnexType(type);
            int row = annexsMapper.updateById(annexsDO);
            if (row != Constants.ONE) {
                throw ServiceExceptionUtil.exception(500, "更新附件信息失败");
            }
            // 更新附件历史版本
            AnnexHistoricalDO historicalDO = AnnexHistoricalDO.builder().annexId(annexsDO.getId()).annexName(annexsDO.getAnnexName()).annexAddress(filePath).annexVersion(DOC_VERSION).annexType(type).build();
            int res = historicalMapper.insert(historicalDO);
            if (res != Constants.ONE) {
                throw ServiceExceptionUtil.exception(500, "更新附件历史版本信息失败");
            }
        } else {
            String docFileName = DOC_FILE_NAME + DOC_VERSION + "_" + System.currentTimeMillis();
            String filePath = doExportFile(engineFileType, docFileName, DOC_VERSION);
            String downloadFileName = DOC_FILE_NAME + engineFileType.getFileSuffix(); //下载后的文件名

            byte[] bytes = FileUtil.readBytes(filePath);
            Base64.Encoder encoder = Base64.getMimeEncoder();
            content = encoder.encodeToString(bytes);

            // 新增附件
            AnnexsDO annexsDO = AnnexsDO.builder().annexAddress(filePath).annexCode(DBMenuType.DB_FILE_ANNEX.getCode()).annexContent(content).annexName(downloadFileName).annexType(type).remark("数据库文档").build();
            int res = annexsMapper.insert(annexsDO);
            if (res != Constants.ONE) {
                throw ServiceExceptionUtil.exception(500, "新增系统附件表失败");
            }

            // 新增数据库文档附件
            DbAnnexsDO creatDbAnnexsDO = DbAnnexsDO.builder().dbAnnexId(annexsDO.getId()).dbName(dbName).annexType(type).build();
            int row = dbAnnexsMapper.insert(creatDbAnnexsDO);
            if (row != Constants.ONE) {
                throw ServiceExceptionUtil.exception(500, "新增数据库附件表失败");
            }

            // 新增数据库历史文档
            // 更新附件历史版本
            AnnexHistoricalDO historicalDO = AnnexHistoricalDO.builder().annexId(annexsDO.getId()).annexName(downloadFileName).annexAddress(filePath).annexVersion(DOC_VERSION).annexType(type).build();
            int hRes = historicalMapper.insert(historicalDO);
            if (hRes != Constants.ONE) {
                throw ServiceExceptionUtil.exception(500, "新增附件历史版本信息失败");
            }
        }
        return content;
    }

    /**
     * 输出文件，返回文件路径
     *
     * @param fileOutputType 文件类型
     * @param fileName       文件名
     * @return 生成的文件所在路径
     */
    private String doExportFile(EngineFileType fileOutputType, String fileName, String DOC_VERSION) {
        try (HikariDataSource dataSource = buildDataSource()) {
            // 创建 screw 的配置
            Configuration config = Configuration.builder()
                    .version(DOC_VERSION)  // 版本
                    .description(DOC_DESCRIPTION) // 描述
                    .dataSource(dataSource) // 数据源
                    .engineConfig(buildEngineConfig(fileOutputType, fileName)) // 引擎配置
                    .produceConfig(buildProcessConfig()) // 处理配置
                    .build();

            log.info("生成数据库文档中... ，文档配置为:{}", config);
            // 执行 screw，生成数据库文档
            new DocumentationExecute(config).execute();
            String dbPath = FILE_OUTPUT_DIR + File.separator + fileName + fileOutputType.getFileSuffix();
            log.info("文档生成成功，文档地址为：{}", dbPath);
            return dbPath;
        }
    }

    /**
     * 创建数据源
     */
    private HikariDataSource buildDataSource() {
        String primary = dynamicDataSourceProperties.getPrimary();
        DataSourceProperty dataSourceProperty = dynamicDataSourceProperties.getDatasource().get(primary);
        // 创建 HikariConfig 配置类
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dataSourceProperty.getUrl());
        hikariConfig.setUsername(dataSourceProperty.getUsername());
        hikariConfig.setPassword(dataSourceProperty.getPassword());
        hikariConfig.addDataSourceProperty("useInformationSchema", "true"); // 设置可以获取 tables remarks 信息
        // 创建数据源
        return new HikariDataSource(hikariConfig);
    }

    /**
     * 创建 screw 的引擎配置
     */
    private static EngineConfig buildEngineConfig(EngineFileType fileOutputType, String docFileName) {
        return EngineConfig.builder()
                .fileOutputDir(FILE_OUTPUT_DIR) // 生成文件路径
                .openOutputDir(false) // 打开目录
                .fileType(fileOutputType) // 文件类型
                .produceType(EngineTemplateType.velocity) // 文件类型
                .fileName(docFileName) // 自定义文件名称
                .build();
    }

    /**
     * 创建 screw 的处理配置，一般可忽略
     * 指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
     */
    private static ProcessConfig buildProcessConfig() {
        return ProcessConfig.builder()
                .ignoreTablePrefix(Arrays.asList("QRTZ_", "ACT_", "act_")) // 忽略表前缀
                .build();
    }
}
