/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : iocoder-pro

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 25/06/2022 23:19:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for system_dept
-- ----------------------------
DROP TABLE IF EXISTS `system_dept`;
CREATE TABLE `system_dept` (
  `id` bigint(20) NOT NULL COMMENT '部门ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父部门id',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `leader_user_id` bigint(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(4) NOT NULL COMMENT '部门状态（0正常 1停用）',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of system_dept
-- ----------------------------
BEGIN;
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (100, '国家机械工业集团', 0, 0, 1, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103', '2022-06-25 23:19:03', b'0');
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (101, '北京总公司', 100, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-06-25 23:19:20', b'0');
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (102, '上海分公司', 100, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2022-06-25 23:19:27', b'0');
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (103, '研发部门', 101, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103', '2022-01-14 01:04:14', b'0');
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (104, '市场部门', 101, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:38', b'0');
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (105, '测试部门', 101, 3, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-05-16 20:25:15', b'0');
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (106, '财务部门', 101, 4, 103, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103', '2022-01-15 21:32:22', b'0');
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (107, '运维部门', 101, 5, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:33', b'0');
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (108, '市场部门', 102, 1, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-02-16 08:35:45', b'0');
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (109, '财务部门', 102, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:29', b'0');
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (110, '新部门', 0, 1, NULL, NULL, NULL, 0, '110', '2022-02-23 20:46:30', '110', '2022-02-23 20:46:30', b'0');
INSERT INTO `system_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `phone`, `email`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (111, '顶级部门', 0, 1, NULL, NULL, NULL, 0, '113', '2022-03-07 21:44:50', '113', '2022-03-07 21:44:50', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_login_log
-- ----------------------------
DROP TABLE IF EXISTS `system_login_log`;
CREATE TABLE `system_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `log_type` bigint(20) NOT NULL COMMENT '日志类型',
  `trace_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户编号',
  `user_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户类型',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户账号',
  `result` tinyint(4) NOT NULL COMMENT '登陆结果',
  `user_ip` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
  `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1540712722142896131 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统访问记录';

-- ----------------------------
-- Records of system_login_log
-- ----------------------------
BEGIN;
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540263831509348353, 100, '', 1, 2, 'admin', 10, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 17:21:29', NULL, '2022-06-24 17:21:29', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540264075412320258, 100, '', 1, 2, 'admin', 10, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 17:22:27', NULL, '2022-06-24 17:22:27', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540264234024120321, 100, '', 0, 2, 'admin', 30, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 17:23:05', NULL, '2022-06-24 17:23:05', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540264332846116866, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 17:23:28', NULL, '2022-06-24 17:23:28', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540266699926745090, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 17:32:53', NULL, '2022-06-24 17:32:53', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540269577206697986, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 17:44:19', NULL, '2022-06-24 17:44:19', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540282730053296130, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 18:36:35', NULL, '2022-06-24 18:36:35', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540283218190589954, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 18:38:31', NULL, '2022-06-24 18:38:31', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540283712208359426, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 18:40:29', NULL, '2022-06-24 18:40:29', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540286525453869058, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 18:51:39', NULL, '2022-06-24 18:51:39', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540286864873725953, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 18:53:00', NULL, '2022-06-24 18:53:00', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540288489025650690, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 18:59:28', NULL, '2022-06-24 18:59:28', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540289356038619137, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 19:02:54', NULL, '2022-06-24 19:02:54', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540295189241765890, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 19:26:05', NULL, '2022-06-24 19:26:05', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540296583923314689, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 19:31:38', NULL, '2022-06-24 19:31:38', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540298367005876226, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 19:38:43', NULL, '2022-06-24 19:38:43', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540299647447105537, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 19:43:48', NULL, '2022-06-24 19:43:48', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540310387054764034, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 20:26:29', NULL, '2022-06-24 20:26:29', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540318602941349890, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 20:59:07', NULL, '2022-06-24 20:59:07', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540320421323104258, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 21:06:21', NULL, '2022-06-24 21:06:21', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540370911968997378, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 00:26:59', NULL, '2022-06-25 00:26:59', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540572587405365250, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 13:48:22', NULL, '2022-06-25 13:48:22', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540594995201859585, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 15:17:24', NULL, '2022-06-25 15:17:24', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540610549304299521, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 16:19:13', NULL, '2022-06-25 16:19:13', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540677604707692546, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 20:45:40', NULL, '2022-06-25 20:45:40', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540679001926815746, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 20:51:13', NULL, '2022-06-25 20:51:13', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540680069763149826, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 20:55:28', NULL, '2022-06-25 20:55:28', b'0');
INSERT INTO `system_login_log` (`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540712722142896130, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 23:05:13', NULL, '2022-06-25 23:05:13', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `system_operate_log`;
CREATE TABLE `system_operate_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `trace_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `user_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户类型',
  `module` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模块标题',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作名',
  `type` bigint(20) NOT NULL DEFAULT '0' COMMENT '操作分类',
  `content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作内容',
  `exts` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '拓展字段',
  `request_method` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '请求方法名',
  `request_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '请求地址',
  `user_ip` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户 IP',
  `user_agent` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '浏览器 UA',
  `java_method` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'Java 方法名',
  `java_method_args` varchar(8000) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'Java 方法的参数',
  `start_time` datetime NOT NULL COMMENT '操作时间',
  `duration` int(11) NOT NULL COMMENT '执行时长',
  `result_code` int(11) NOT NULL DEFAULT '0' COMMENT '结果码',
  `result_msg` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '结果提示',
  `result_data` varchar(4000) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '结果数据',
  `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='操作日志记录';

-- ----------------------------
-- Records of system_operate_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for system_post
-- ----------------------------
DROP TABLE IF EXISTS `system_post`;
CREATE TABLE `system_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位编码',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位名称',
  `sort` int(11) NOT NULL COMMENT '显示顺序',
  `status` tinyint(4) NOT NULL COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='岗位信息表';

-- ----------------------------
-- Records of system_post
-- ----------------------------
BEGIN;
INSERT INTO `system_post` (`id`, `code`, `name`, `sort`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1, 'ceo', '董事长', 1, 0, '', 'admin', '2021-01-06 17:03:48', '1', '2022-04-19 16:53:39', b'0');
INSERT INTO `system_post` (`id`, `code`, `name`, `sort`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (2, 'se', '项目经理', 2, 0, '', 'admin', '2021-01-05 17:03:48', '1', '2021-12-12 10:47:47', b'0');
INSERT INTO `system_post` (`id`, `code`, `name`, `sort`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (4, 'user', '普通员工', 4, 0, '111', 'admin', '2021-01-05 17:03:48', '1', '2022-05-04 22:46:35', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串',
  `sort` int(11) NOT NULL COMMENT '显示顺序',
  `data_scope` tinyint(4) NOT NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `data_scope_dept_ids` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据范围(指定部门数组)',
  `status` tinyint(4) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `type` tinyint(4) NOT NULL COMMENT '角色类型',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='角色信息表';

-- ----------------------------
-- Records of system_role
-- ----------------------------
BEGIN;
INSERT INTO `system_role` (`id`, `name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1, '超级管理员', 'super_admin', 1, 1, '', 0, 1, '超级管理员', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:21', b'0');
INSERT INTO `system_role` (`id`, `name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (2, '普通角色', 'common', 2, 2, '', 0, 1, '普通角色', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:20', b'0');
INSERT INTO `system_role` (`id`, `name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (101, '测试账号', 'test', 0, 1, '[]', 0, 2, '132', '', '2021-01-06 13:49:35', '1', '2022-04-01 21:37:13', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '用户邮箱',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '手机号码',
  `sex` tinyint(4) DEFAULT '0' COMMENT '用户性别',
  `avatar` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '头像地址',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1540614322441457666 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

-- ----------------------------
-- Records of system_user
-- ----------------------------
BEGIN;
INSERT INTO `system_user` (`id`, `username`, `password`, `nickname`, `remark`, `email`, `mobile`, `sex`, `avatar`, `status`, `login_ip`, `login_date`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1, 'admin', '$2a$10$0acJOIk2D25/oC87nyclE..0lzeu9DtQ/n3geP4fkun/zIVRhHJIO', '芋道源码', '管理员', 'aoteman@126.com', '15612345678', 1, 'http://127.0.0.1:48080/admin-api/infra/file/get/b7de3474-3805-4e09-80e3-185f20c31a74', 0, '0:0:0:0:0:0:0:1', '2022-06-25 00:00:00', 'admin', '2021-01-05 17:03:47', NULL, '2022-06-25 00:26:58', b'0');
INSERT INTO `system_user` (`id`, `username`, `password`, `nickname`, `remark`, `email`, `mobile`, `sex`, `avatar`, `status`, `login_ip`, `login_date`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540614322441457665, 'ztongkai', '$2a$10$xv12d3kwznAwrbrhFkKH0upULZDmBPB8sX2IQipxe5msU.4KhTxIy', '吴凯', NULL, '2580211264@qq.com', '13121022995', 1, '', 0, '', NULL, '1', '2022-06-25 16:34:12', '1', '2022-06-25 23:15:47', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `system_user_dept`;
CREATE TABLE `system_user_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `dept_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '部门ID',
  `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1540680272335450115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户岗位表';

-- ----------------------------
-- Records of system_user_dept
-- ----------------------------
BEGIN;
INSERT INTO `system_user_dept` (`id`, `user_id`, `dept_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (111, 1, 100, 'admin', '2022-06-25 14:51:03', 'admin', '2022-06-25 14:51:21', b'0');
INSERT INTO `system_user_dept` (`id`, `user_id`, `dept_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540614322588258305, 1540614322441457665, 100, '1', '2022-06-25 16:34:12', '1', '2022-06-25 23:15:22', b'0');
INSERT INTO `system_user_dept` (`id`, `user_id`, `dept_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540678067884666881, 1540614322441457665, 101, '1', '2022-06-25 20:47:30', '1', '2022-06-25 23:15:29', b'0');
INSERT INTO `system_user_dept` (`id`, `user_id`, `dept_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540680272335450114, 1540614322441457665, 102, '1', '2022-06-25 20:56:16', '1', '2022-06-25 23:15:29', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_user_post
-- ----------------------------
DROP TABLE IF EXISTS `system_user_post`;
CREATE TABLE `system_user_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '岗位ID',
  `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1540678067985330178 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户岗位表';

-- ----------------------------
-- Records of system_user_post
-- ----------------------------
BEGIN;
INSERT INTO `system_user_post` (`id`, `user_id`, `post_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (112, 1, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0');
INSERT INTO `system_user_post` (`id`, `user_id`, `post_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (113, 100, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0');
INSERT INTO `system_user_post` (`id`, `user_id`, `post_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (114, 114, 3, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0');
INSERT INTO `system_user_post` (`id`, `user_id`, `post_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540614322659561474, 1540614322441457665, 1, '1', '2022-06-25 16:34:12', '1', '2022-06-25 23:15:14', b'0');
INSERT INTO `system_user_post` (`id`, `user_id`, `post_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1540678067985330177, 1540614322441457665, 2, '1', '2022-06-25 20:47:30', '1', '2022-06-25 23:15:16', b'0');
COMMIT;

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
BEGIN;
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1, 1, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:17', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (2, 2, 2, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:13', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (4, 100, 101, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:13', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (5, 100, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:12', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (6, 100, 2, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:11', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (7, 104, 101, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:11', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (10, 103, 1, '1', '2022-01-11 13:19:45', '1', '2022-01-11 13:19:45', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (11, 107, 106, '1', '2022-02-20 22:59:33', '1', '2022-02-20 22:59:33', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (12, 108, 107, '1', '2022-02-20 23:00:50', '1', '2022-02-20 23:00:50', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (13, 109, 108, '1', '2022-02-20 23:11:50', '1', '2022-02-20 23:11:50', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (14, 110, 109, '1', '2022-02-22 00:56:14', '1', '2022-02-22 00:56:14', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (15, 111, 110, '110', '2022-02-23 13:14:38', '110', '2022-02-23 13:14:38', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (16, 113, 111, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (17, 114, 101, '1', '2022-03-19 21:51:13', '1', '2022-03-19 21:51:13', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (18, 1, 2, '1', '2022-05-12 20:39:29', '1', '2022-05-12 20:39:29', b'0');
INSERT INTO `system_user_role` (`id`, `user_id`, `role_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (19, 116, 113, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
