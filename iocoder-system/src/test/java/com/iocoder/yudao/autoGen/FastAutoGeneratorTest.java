package com.iocoder.yudao.autoGen;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.iocoder.yudao.module.commons.core.domain.BaseEntity;
import com.iocoder.yudao.module.commons.core.mapper.BaseMapperX;

import java.util.HashMap;

/**
 * mybatis-plus 代码生成器配置
 *
 * @Author: kai wu
 * @Date: 2022/6/3 14:35
 */
public class FastAutoGeneratorTest {

    public static void main(String[] args) {
        // 输出路径
        String projectPath = System.getProperty("user.dir");
        // 实现类模块名
        String GlobalModelName = "iocoder-system";
        // api模块名
        String ApiModelName = "iocoder-api";
        // 业务模块名
        String modelName = "system";
        // 表名
        String[] tableNames = {"system_user"};

        // 数据库连接信息
        String jdbcUrl = "jdbc:mysql://localhost:3306/iocoder-pro?useUnicode=true&characterEncoding=utf8&serverTimezone" +
                "=GMT&useSSL=false";
        String username = "root";
        String password = "123456";
        // 作者信息
        String author = "wu kai";


        // controller 和 mapperXml 的生成路径
        HashMap<OutputFile, String> pathMap = new HashMap<>();
        pathMap.put(OutputFile.mapperXml,projectPath+"/"+GlobalModelName+"/src/main/resources/mapper/"+modelName);
        pathMap.put(OutputFile.controller,projectPath+"/"+ApiModelName+"/src/main/java/com/iocoder/yudao/module/system/api");
        // 生成策略配置
        FastAutoGenerator.create(jdbcUrl, username, password)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir()
                            .fileOverride() // 覆盖已生成文件
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd")
                            .outputDir(projectPath + "/" + GlobalModelName + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.iocoder.yudao.module.system") // 设置父包名
                            .entity("domain")
                            .service("service")
                            .mapper("mapper")
                            .controller("controller")
                            .serviceImpl("service.impl")
                            .pathInfo(pathMap); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableNames) // 设置需要生成的表名
                            .addTablePrefix("system_")// 设置过滤表前缀
                            .entityBuilder()// 实体类配置
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .superClass(BaseEntity.class)
                            .addSuperEntityColumns("id", "creator", "createTime", "updater", "updateTime", "deleted")
                            // 主键策略配置-- 雪花算法
                            .idType(IdType.ASSIGN_ID)
                            .enableChainModel()
                            .formatFileName("%sDO")
                            // controller 配置
                            .controllerBuilder()
                            // 开启生成@RestController 控制器
                            .enableRestStyle()
                            // 格式化文件名称
                            .formatFileName("%sController")
                            // service 配置
                            .serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl")
                            // mapper 配置
                            .mapperBuilder().superClass(BaseMapperX.class).formatMapperFileName("%sMapper").enableMapperAnnotation().formatXmlFileName("%sMapper")
                    ;

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
