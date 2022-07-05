# iocoder-pro

#### 介绍
使用SpringBoot，Spring Security，JWT实现的后端开发脚手架
参考ruoyi-vue、ruoyi-vue-pro搭建

#### 软件架构

| 项目                     | 说明              |
|------------------------|-----------------|
| `iocoder-dependencies` | Maven 依赖版本管理    |
| `iocoder-framework`    | Java 框架拓展       |
| `iocoder-server`       | 启动系统服务          |
| `iocoder-system`       | 系统功能的 Module 模块 |
| `iocoder-api`          | 系统接口模块          |
| `iocoder-commons`      | 公共模块            |



#### 使用说明


1、克隆代码
    使用 [IDEA](https://www.jetbrains.com/idea/) 克隆 [https://gitee.com/wztongkai/iocoder-pro.git ](https://gitee.com/wztongkai/iocoder-pro.git) 仓库的最新代码

2、初始化MySQL
    创建名为 ``iocoder-pro`` 数据库，并执行 ``sql`` 目录下的 ``iocoder-pro.sql`` 文件

3、修改 [jasypt](http://www.jasypt.org/) 加密配置信息 (修改前缀、后缀以及加密秘钥)

3、执行 ``iocoder-server`` 模块 ``test`` 下的 ``JasyptTest`` 文件 中的 test()方法，生成数据库账号密码的加密字符串

4、修改 ``application-dev.yml`` 配置文件中用户名、密码 (使用第三步生成的加密字符串替换 username、password  JASYPT_WK() 中的字符串)

5、修改 ``application.yml`` 配置文件中的 redis 配置信息

6、运行 ``iocoder-server`` 模块中启动类 ``IoCoderApplication.java`` 启动服务 
