/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : iocoder-pro

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 11/07/2022 15:00:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for act_evt_log
-- ----------------------------
DROP TABLE IF EXISTS `act_evt_log`;
CREATE TABLE `act_evt_log`  (
  `LOG_NR_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DATA_` longblob NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`LOG_NR_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_evt_log
-- ----------------------------

-- ----------------------------
-- Table structure for act_ge_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_bytearray`;
CREATE TABLE `act_ge_bytearray`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `BYTES_` longblob NULL,
  `GENERATED_` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_FK_BYTEARR_DEPL`(`DEPLOYMENT_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ge_bytearray
-- ----------------------------

-- ----------------------------
-- Table structure for act_ge_property
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_property`;
CREATE TABLE `act_ge_property`  (
  `NAME_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VALUE_` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`NAME_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ge_property
-- ----------------------------
INSERT INTO `act_ge_property` VALUES ('cfg.execution-related-entities-count', 'false', 1);
INSERT INTO `act_ge_property` VALUES ('next.dbid', '1', 1);
INSERT INTO `act_ge_property` VALUES ('schema.history', 'create(7.1.0-M6)', 1);
INSERT INTO `act_ge_property` VALUES ('schema.version', '7.1.0-M6', 1);

-- ----------------------------
-- Table structure for act_hi_actinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_actinst`;
CREATE TABLE `act_hi_actinst`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACT_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACT_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) NULL DEFAULT NULL,
  `DURATION_` bigint(20) NULL DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_ACT_INST_START`(`START_TIME_`) USING BTREE,
  INDEX `ACT_IDX_HI_ACT_INST_END`(`END_TIME_`) USING BTREE,
  INDEX `ACT_IDX_HI_ACT_INST_PROCINST`(`PROC_INST_ID_`, `ACT_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_ACT_INST_EXEC`(`EXECUTION_ID_`, `ACT_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_actinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_attachment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_attachment`;
CREATE TABLE `act_hi_attachment`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `URL_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CONTENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TIME_` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_comment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_comment`;
CREATE TABLE `act_hi_comment`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACTION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `MESSAGE_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `FULL_MSG_` longblob NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_comment
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_detail
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_detail`;
CREATE TABLE `act_hi_detail`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DOUBLE_` double NULL DEFAULT NULL,
  `LONG_` bigint(20) NULL DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_PROC_INST`(`PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_ACT_INST`(`ACT_INST_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_TIME`(`TIME_`) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_NAME`(`NAME_`) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_TASK_ID`(`TASK_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_detail
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_identitylink`;
CREATE TABLE `act_hi_identitylink`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_USER`(`USER_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_TASK`(`TASK_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_PROCINST`(`PROC_INST_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_identitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_procinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_procinst`;
CREATE TABLE `act_hi_procinst`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) NULL DEFAULT NULL,
  `DURATION_` bigint(20) NULL DEFAULT NULL,
  `START_USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `START_ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `END_ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `PROC_INST_ID_`(`PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_PRO_INST_END`(`END_TIME_`) USING BTREE,
  INDEX `ACT_IDX_HI_PRO_I_BUSKEY`(`BUSINESS_KEY_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_procinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_taskinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_taskinst`;
CREATE TABLE `act_hi_taskinst`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ASSIGNEE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `CLAIM_TIME_` datetime(3) NULL DEFAULT NULL,
  `END_TIME_` datetime(3) NULL DEFAULT NULL,
  `DURATION_` bigint(20) NULL DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PRIORITY_` int(11) NULL DEFAULT NULL,
  `DUE_DATE_` datetime(3) NULL DEFAULT NULL,
  `FORM_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_TASK_INST_PROCINST`(`PROC_INST_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_taskinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_varinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_varinst`;
CREATE TABLE `act_hi_varinst`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DOUBLE_` double NULL DEFAULT NULL,
  `LONG_` bigint(20) NULL DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NULL DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_PROCVAR_PROC_INST`(`PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_PROCVAR_NAME_TYPE`(`NAME_`, `VAR_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_HI_PROCVAR_TASK_ID`(`TASK_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_varinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_procdef_info
-- ----------------------------
DROP TABLE IF EXISTS `act_procdef_info`;
CREATE TABLE `act_procdef_info`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `ACT_UNIQ_INFO_PROCDEF`(`PROC_DEF_ID_`) USING BTREE,
  INDEX `ACT_IDX_INFO_PROCDEF`(`PROC_DEF_ID_`) USING BTREE,
  INDEX `ACT_FK_INFO_JSON_BA`(`INFO_JSON_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_INFO_JSON_BA` FOREIGN KEY (`INFO_JSON_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_INFO_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_procdef_info
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_deployment
-- ----------------------------
DROP TABLE IF EXISTS `act_re_deployment`;
CREATE TABLE `act_re_deployment`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `DEPLOY_TIME_` timestamp(3) NULL DEFAULT NULL,
  `ENGINE_VERSION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `VERSION_` int(11) NULL DEFAULT 1,
  `PROJECT_RELEASE_VERSION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_re_deployment
-- ----------------------------
INSERT INTO `act_re_deployment` VALUES ('0faedc7e-00e2-11ed-bf38-d89ef33ad32f', 'SpringAutoDeployment', NULL, NULL, '', '2022-07-11 06:24:04.397', NULL, 3, NULL);
INSERT INTO `act_re_deployment` VALUES ('4540f878-00e5-11ed-9930-d89ef33ad32f', 'SpringAutoDeployment', NULL, NULL, '', '2022-07-11 06:47:02.765', NULL, 7, NULL);
INSERT INTO `act_re_deployment` VALUES ('8c9112bf-00e2-11ed-9498-d89ef33ad32f', 'SpringAutoDeployment', NULL, NULL, '', '2022-07-11 06:27:33.918', NULL, 4, NULL);
INSERT INTO `act_re_deployment` VALUES ('c2f8890d-00e2-11ed-8efb-d89ef33ad32f', 'SpringAutoDeployment', NULL, NULL, '', '2022-07-11 06:29:05.195', NULL, 5, NULL);
INSERT INTO `act_re_deployment` VALUES ('d28976c5-00dd-11ed-83d4-d89ef33ad32f', 'SpringAutoDeployment', NULL, NULL, '', '2022-07-11 05:53:43.832', NULL, 1, NULL);
INSERT INTO `act_re_deployment` VALUES ('e488a29a-00df-11ed-93e1-d89ef33ad32f', 'SpringAutoDeployment', NULL, NULL, '', '2022-07-11 06:08:33.015', NULL, 2, NULL);
INSERT INTO `act_re_deployment` VALUES ('f8d50005-00e6-11ed-af8a-d89ef33ad32f', 'SpringAutoDeployment', NULL, NULL, '', '2022-07-11 06:59:13.546', NULL, 8, NULL);
INSERT INTO `act_re_deployment` VALUES ('feb35d62-00e2-11ed-b1a2-d89ef33ad32f', 'SpringAutoDeployment', NULL, NULL, '', '2022-07-11 06:30:45.403', NULL, 6, NULL);

-- ----------------------------
-- Table structure for act_re_model
-- ----------------------------
DROP TABLE IF EXISTS `act_re_model`;
CREATE TABLE `act_re_model`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `VERSION_` int(11) NULL DEFAULT NULL,
  `META_INFO_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_FK_MODEL_SOURCE`(`EDITOR_SOURCE_VALUE_ID_`) USING BTREE,
  INDEX `ACT_FK_MODEL_SOURCE_EXTRA`(`EDITOR_SOURCE_EXTRA_VALUE_ID_`) USING BTREE,
  INDEX `ACT_FK_MODEL_DEPLOYMENT`(`DEPLOYMENT_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_re_model
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_procdef
-- ----------------------------
DROP TABLE IF EXISTS `act_re_procdef`;
CREATE TABLE `act_re_procdef`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) NULL DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) NULL DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `ENGINE_VERSION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `APP_VERSION_` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `ACT_UNIQ_PROCDEF`(`KEY_`, `VERSION_`, `TENANT_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_re_procdef
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_deadletter_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_deadletter_job`;
CREATE TABLE `act_ru_deadletter_job`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_FK_DEADLETTER_JOB_EXECUTION`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE`(`PROCESS_INSTANCE_ID_`) USING BTREE,
  INDEX `ACT_FK_DEADLETTER_JOB_PROC_DEF`(`PROC_DEF_ID_`) USING BTREE,
  INDEX `ACT_FK_DEADLETTER_JOB_EXCEPTION`(`EXCEPTION_STACK_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_deadletter_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_event_subscr
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_event_subscr`;
CREATE TABLE `act_ru_event_subscr`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CONFIGURATION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATED_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_EVENT_SUBSCR_CONFIG_`(`CONFIGURATION_`) USING BTREE,
  INDEX `ACT_FK_EVENT_EXEC`(`EXECUTION_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_event_subscr
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_execution
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_execution`;
CREATE TABLE `act_ru_execution`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PARENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) NULL DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) NULL DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) NULL DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) NULL DEFAULT NULL,
  `IS_MI_ROOT_` tinyint(4) NULL DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) NULL DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `START_TIME_` datetime(3) NULL DEFAULT NULL,
  `START_USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `IS_COUNT_ENABLED_` tinyint(4) NULL DEFAULT NULL,
  `EVT_SUBSCR_COUNT_` int(11) NULL DEFAULT NULL,
  `TASK_COUNT_` int(11) NULL DEFAULT NULL,
  `JOB_COUNT_` int(11) NULL DEFAULT NULL,
  `TIMER_JOB_COUNT_` int(11) NULL DEFAULT NULL,
  `SUSP_JOB_COUNT_` int(11) NULL DEFAULT NULL,
  `DEADLETTER_JOB_COUNT_` int(11) NULL DEFAULT NULL,
  `VAR_COUNT_` int(11) NULL DEFAULT NULL,
  `ID_LINK_COUNT_` int(11) NULL DEFAULT NULL,
  `APP_VERSION_` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_EXEC_BUSKEY`(`BUSINESS_KEY_`) USING BTREE,
  INDEX `ACT_IDC_EXEC_ROOT`(`ROOT_PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_FK_EXE_PROCINST`(`PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_FK_EXE_PARENT`(`PARENT_ID_`) USING BTREE,
  INDEX `ACT_FK_EXE_SUPER`(`SUPER_EXEC_`) USING BTREE,
  INDEX `ACT_FK_EXE_PROCDEF`(`PROC_DEF_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_execution
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_identitylink`;
CREATE TABLE `act_ru_identitylink`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `GROUP_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_IDENT_LNK_USER`(`USER_ID_`) USING BTREE,
  INDEX `ACT_IDX_IDENT_LNK_GROUP`(`GROUP_ID_`) USING BTREE,
  INDEX `ACT_IDX_ATHRZ_PROCEDEF`(`PROC_DEF_ID_`) USING BTREE,
  INDEX `ACT_FK_TSKASS_TASK`(`TASK_ID_`) USING BTREE,
  INDEX `ACT_FK_IDL_PROCINST`(`PROC_INST_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_identitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_integration
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_integration`;
CREATE TABLE `act_ru_integration`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `FLOW_NODE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATED_DATE_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_FK_INT_EXECUTION`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_FK_INT_PROC_INST`(`PROCESS_INSTANCE_ID_`) USING BTREE,
  INDEX `ACT_FK_INT_PROC_DEF`(`PROC_DEF_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_INT_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_INT_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_INT_PROC_INST` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_integration
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_job`;
CREATE TABLE `act_ru_job`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RETRIES_` int(11) NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_FK_JOB_EXECUTION`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_FK_JOB_PROCESS_INSTANCE`(`PROCESS_INSTANCE_ID_`) USING BTREE,
  INDEX `ACT_FK_JOB_PROC_DEF`(`PROC_DEF_ID_`) USING BTREE,
  INDEX `ACT_FK_JOB_EXCEPTION`(`EXCEPTION_STACK_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_suspended_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_suspended_job`;
CREATE TABLE `act_ru_suspended_job`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RETRIES_` int(11) NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_FK_SUSPENDED_JOB_EXECUTION`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE`(`PROCESS_INSTANCE_ID_`) USING BTREE,
  INDEX `ACT_FK_SUSPENDED_JOB_PROC_DEF`(`PROC_DEF_ID_`) USING BTREE,
  INDEX `ACT_FK_SUSPENDED_JOB_EXCEPTION`(`EXCEPTION_STACK_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_suspended_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_task
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_task`;
CREATE TABLE `act_ru_task`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ASSIGNEE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DELEGATION_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PRIORITY_` int(11) NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `DUE_DATE_` datetime(3) NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `FORM_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CLAIM_TIME_` datetime(3) NULL DEFAULT NULL,
  `APP_VERSION_` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_TASK_CREATE`(`CREATE_TIME_`) USING BTREE,
  INDEX `ACT_FK_TASK_EXE`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_FK_TASK_PROCINST`(`PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_FK_TASK_PROCDEF`(`PROC_DEF_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_task
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_timer_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_timer_job`;
CREATE TABLE `act_ru_timer_job`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RETRIES_` int(11) NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_FK_TIMER_JOB_EXECUTION`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_FK_TIMER_JOB_PROCESS_INSTANCE`(`PROCESS_INSTANCE_ID_`) USING BTREE,
  INDEX `ACT_FK_TIMER_JOB_PROC_DEF`(`PROC_DEF_ID_`) USING BTREE,
  INDEX `ACT_FK_TIMER_JOB_EXCEPTION`(`EXCEPTION_STACK_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_TIMER_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TIMER_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TIMER_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TIMER_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_timer_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_variable
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_variable`;
CREATE TABLE `act_ru_variable`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DOUBLE_` double NULL DEFAULT NULL,
  `LONG_` bigint(20) NULL DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_VARIABLE_TASK_ID`(`TASK_ID_`) USING BTREE,
  INDEX `ACT_FK_VAR_EXE`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_FK_VAR_PROCINST`(`PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_FK_VAR_BYTEARRAY`(`BYTEARRAY_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_variable
-- ----------------------------

-- ----------------------------
-- Table structure for system_dept
-- ----------------------------
DROP TABLE IF EXISTS `system_dept`;
CREATE TABLE `system_dept`  (
  `id` bigint(20) NOT NULL COMMENT '部门ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父部门id',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `leader_user_id` bigint(20) NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(4) NOT NULL COMMENT '部门状态（0正常 1停用）',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_dept
-- ----------------------------
INSERT INTO `system_dept` VALUES (100, '国家机械工业集团', 0, 0, 1, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103', '2022-06-25 23:19:03', b'0');
INSERT INTO `system_dept` VALUES (101, '北京总公司', 100, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-06-25 23:19:20', b'0');
INSERT INTO `system_dept` VALUES (102, '上海分公司', 100, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2022-06-25 23:19:27', b'0');
INSERT INTO `system_dept` VALUES (103, '研发部门', 101, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103', '2022-01-14 01:04:14', b'0');
INSERT INTO `system_dept` VALUES (104, '市场部门', 101, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:38', b'0');
INSERT INTO `system_dept` VALUES (105, '测试部门', 101, 3, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-05-16 20:25:15', b'0');
INSERT INTO `system_dept` VALUES (106, '财务部门', 101, 4, 103, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103', '2022-01-15 21:32:22', b'0');
INSERT INTO `system_dept` VALUES (107, '运维部门', 101, 5, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:33', b'0');
INSERT INTO `system_dept` VALUES (108, '市场部门', 102, 1, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-02-16 08:35:45', b'0');
INSERT INTO `system_dept` VALUES (109, '财务部门', 102, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:29', b'0');
INSERT INTO `system_dept` VALUES (110, '新部门', 0, 1, NULL, NULL, NULL, 0, '110', '2022-02-23 20:46:30', '110', '2022-02-23 20:46:30', b'0');
INSERT INTO `system_dept` VALUES (111, '顶级部门', 0, 1, NULL, NULL, NULL, 0, '113', '2022-03-07 21:44:50', '113', '2022-03-07 21:44:50', b'0');

-- ----------------------------
-- Table structure for system_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_data`;
CREATE TABLE `system_dict_data`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '字典排序',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典标签',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `color_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '颜色类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT 'css 样式',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1161 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_dict_data
-- ----------------------------

-- ----------------------------
-- Table structure for system_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_type`;
CREATE TABLE `system_dict_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典名称',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 148 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_dict_type
-- ----------------------------

-- ----------------------------
-- Table structure for system_login_log
-- ----------------------------
DROP TABLE IF EXISTS `system_login_log`;
CREATE TABLE `system_login_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `log_type` bigint(20) NOT NULL COMMENT '日志类型',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户编号',
  `user_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '用户类型',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户账号',
  `result` tinyint(4) NOT NULL COMMENT '登陆结果',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1544946379489439747 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_login_log
-- ----------------------------
INSERT INTO `system_login_log` VALUES (1540263831509348353, 100, '', 1, 2, 'admin', 10, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 17:21:29', NULL, '2022-06-24 17:21:29', b'0');
INSERT INTO `system_login_log` VALUES (1540264075412320258, 100, '', 1, 2, 'admin', 10, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 17:22:27', NULL, '2022-06-24 17:22:27', b'0');
INSERT INTO `system_login_log` VALUES (1540264234024120321, 100, '', 0, 2, 'admin', 30, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 17:23:05', NULL, '2022-06-24 17:23:05', b'0');
INSERT INTO `system_login_log` VALUES (1540264332846116866, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 17:23:28', NULL, '2022-06-24 17:23:28', b'0');
INSERT INTO `system_login_log` VALUES (1540266699926745090, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 17:32:53', NULL, '2022-06-24 17:32:53', b'0');
INSERT INTO `system_login_log` VALUES (1540269577206697986, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 17:44:19', NULL, '2022-06-24 17:44:19', b'0');
INSERT INTO `system_login_log` VALUES (1540282730053296130, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 18:36:35', NULL, '2022-06-24 18:36:35', b'0');
INSERT INTO `system_login_log` VALUES (1540283218190589954, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 18:38:31', NULL, '2022-06-24 18:38:31', b'0');
INSERT INTO `system_login_log` VALUES (1540283712208359426, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 18:40:29', NULL, '2022-06-24 18:40:29', b'0');
INSERT INTO `system_login_log` VALUES (1540286525453869058, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 18:51:39', NULL, '2022-06-24 18:51:39', b'0');
INSERT INTO `system_login_log` VALUES (1540286864873725953, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 18:53:00', NULL, '2022-06-24 18:53:00', b'0');
INSERT INTO `system_login_log` VALUES (1540288489025650690, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 18:59:28', NULL, '2022-06-24 18:59:28', b'0');
INSERT INTO `system_login_log` VALUES (1540289356038619137, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 19:02:54', NULL, '2022-06-24 19:02:54', b'0');
INSERT INTO `system_login_log` VALUES (1540295189241765890, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 19:26:05', NULL, '2022-06-24 19:26:05', b'0');
INSERT INTO `system_login_log` VALUES (1540296583923314689, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 19:31:38', NULL, '2022-06-24 19:31:38', b'0');
INSERT INTO `system_login_log` VALUES (1540298367005876226, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 19:38:43', NULL, '2022-06-24 19:38:43', b'0');
INSERT INTO `system_login_log` VALUES (1540299647447105537, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 19:43:48', NULL, '2022-06-24 19:43:48', b'0');
INSERT INTO `system_login_log` VALUES (1540310387054764034, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 20:26:29', NULL, '2022-06-24 20:26:29', b'0');
INSERT INTO `system_login_log` VALUES (1540318602941349890, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 20:59:07', NULL, '2022-06-24 20:59:07', b'0');
INSERT INTO `system_login_log` VALUES (1540320421323104258, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-24 21:06:21', NULL, '2022-06-24 21:06:21', b'0');
INSERT INTO `system_login_log` VALUES (1540370911968997378, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 00:26:59', NULL, '2022-06-25 00:26:59', b'0');
INSERT INTO `system_login_log` VALUES (1540572587405365250, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 13:48:22', NULL, '2022-06-25 13:48:22', b'0');
INSERT INTO `system_login_log` VALUES (1540594995201859585, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 15:17:24', NULL, '2022-06-25 15:17:24', b'0');
INSERT INTO `system_login_log` VALUES (1540610549304299521, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 16:19:13', NULL, '2022-06-25 16:19:13', b'0');
INSERT INTO `system_login_log` VALUES (1540677604707692546, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 20:45:40', NULL, '2022-06-25 20:45:40', b'0');
INSERT INTO `system_login_log` VALUES (1540679001926815746, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 20:51:13', NULL, '2022-06-25 20:51:13', b'0');
INSERT INTO `system_login_log` VALUES (1540680069763149826, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 20:55:28', NULL, '2022-06-25 20:55:28', b'0');
INSERT INTO `system_login_log` VALUES (1540712722142896130, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-25 23:05:13', NULL, '2022-06-25 23:05:13', b'0');
INSERT INTO `system_login_log` VALUES (1542016625799016450, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-29 13:26:28', NULL, '2022-06-29 13:26:28', b'0');
INSERT INTO `system_login_log` VALUES (1542018686125027330, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-29 13:34:39', NULL, '2022-06-29 13:34:39', b'0');
INSERT INTO `system_login_log` VALUES (1542019137398620162, 100, '', 0, 2, 'admin', 30, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-29 13:36:26', NULL, '2022-06-29 13:36:26', b'0');
INSERT INTO `system_login_log` VALUES (1542019249835327490, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-29 13:36:53', NULL, '2022-06-29 13:36:53', b'0');
INSERT INTO `system_login_log` VALUES (1542019833766334466, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-29 13:39:12', NULL, '2022-06-29 13:39:12', b'0');
INSERT INTO `system_login_log` VALUES (1542020789149028354, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-29 13:43:00', NULL, '2022-06-29 13:43:00', b'0');
INSERT INTO `system_login_log` VALUES (1542022299710881793, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-29 13:49:00', NULL, '2022-06-29 13:49:00', b'0');
INSERT INTO `system_login_log` VALUES (1542023141599969281, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-29 13:52:21', NULL, '2022-06-29 13:52:21', b'0');
INSERT INTO `system_login_log` VALUES (1542025003136937985, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-29 13:59:45', NULL, '2022-06-29 13:59:45', b'0');
INSERT INTO `system_login_log` VALUES (1542027299547746305, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-29 14:08:52', NULL, '2022-06-29 14:08:52', b'0');
INSERT INTO `system_login_log` VALUES (1542082135039406081, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-29 17:46:46', NULL, '2022-06-29 17:46:46', b'0');
INSERT INTO `system_login_log` VALUES (1542458404612567042, 100, '', 1, 1, 'admin', 10, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-30 18:41:56', NULL, '2022-06-30 18:41:56', b'0');
INSERT INTO `system_login_log` VALUES (1542458470194704386, 100, '', 1, 1, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-06-30 18:42:11', NULL, '2022-06-30 18:42:11', b'0');
INSERT INTO `system_login_log` VALUES (1544573045916479490, 100, '', 1, 1, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-07-06 14:44:46', NULL, '2022-07-06 14:44:46', b'0');
INSERT INTO `system_login_log` VALUES (1544946379489439746, 100, '', 1, 1, 'admin', 0, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.29.0', NULL, '2022-07-07 15:28:15', NULL, '2022-07-07 15:28:15', b'0');

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限标识',
  `type` tinyint(4) NOT NULL COMMENT '菜单类型',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父菜单ID',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '路由地址',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '菜单状态',
  `visible` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可见',
  `keep_alive` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否缓存',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1268 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_menu
-- ----------------------------

-- ----------------------------
-- Table structure for system_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `system_operate_log`;
CREATE TABLE `system_operate_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `user_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '用户类型',
  `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模块标题',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '操作分类',
  `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求方法名',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求地址',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户 IP',
  `oper_location` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作地点',
  `user_agent` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '浏览器 UA',
  `java_method` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'Java 方法名',
  `java_method_args` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT 'Java 方法的参数',
  `start_time` datetime(0) NOT NULL COMMENT '操作时间',
  `duration` int(11) NOT NULL COMMENT '执行时长',
  `result_code` int(11) NOT NULL DEFAULT 0 COMMENT '结果码',
  `result_msg` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果提示',
  `result_data` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果数据',
  `error_msg` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '错误信息',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1542458577015238658 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_operate_log
-- ----------------------------
INSERT INTO `system_operate_log` VALUES (1542083651812896769, 1, 0, '用户中心', 'UPDATE', 'PUT', '/system/user/profile/update-password', '127.0.0.1', NULL, 'PostmanRuntime/7.29.0', 'com.iocoder.yudao.module.system.api.user.UserProfileController.updateUserProfilePassword()', '{\"newPassword\":\"123456\",\"oldPassword\":\"admin123\"}', '2022-06-29 17:52:48', 495, 200, '成功', '{\"code\":200,\"data\":true,\"flag\":0,\"msg\":\"成功\"}', NULL, '1', '2022-06-29 17:52:48', '1', '2022-06-29 17:52:48', b'0');
INSERT INTO `system_operate_log` VALUES (1542087817029398530, 1, 0, '用户中心', 'UPDATE', 'PUT', '/system/user/profile/update-password', '127.0.0.1', NULL, 'PostmanRuntime/7.29.0', 'com.iocoder.yudao.module.system.api.user.UserProfileController.updateUserProfilePassword()', '{\"newPassword\":\"123456\",\"oldPassword\":\"admin123\"}', '2022-06-29 18:09:21', 97, -1, '失败', 'null', '用户密码校验失败', '1', '2022-06-29 18:09:21', '1', '2022-06-29 18:09:21', b'0');
INSERT INTO `system_operate_log` VALUES (1542092491933573122, 1, 0, '用户中心-修改用户个人密码', 'UPDATE', 'PUT', '/system/user/profile/update-password', '127.0.0.1', NULL, 'PostmanRuntime/7.29.0', 'com.iocoder.yudao.module.system.api.user.UserProfileController.updateUserProfilePassword()', '{\"newPassword\":\"123456\",\"oldPassword\":\"admin123\"}', '2022-06-29 18:27:55', 363, -1, '失败', 'null', '用户密码校验失败', '1', '2022-06-29 18:27:55', '1', '2022-06-29 18:27:55', b'0');
INSERT INTO `system_operate_log` VALUES (1542458577015238657, 1, 1, '用户中心-修改用户个人密码', 'UPDATE', 'PUT', '/system/user/profile/update-password', '127.0.0.1', '内网IP', 'PostmanRuntime/7.29.0', 'com.iocoder.yudao.module.system.api.user.UserProfileController.updateUserProfilePassword()', '{\"newPassword\":\"123456\",\"oldPassword\":\"admin123\"}', '2022-06-30 18:42:37', 94, -1, '失败', 'null', '用户密码校验失败', '1', '2022-06-30 18:42:37', '1', '2022-06-30 18:42:37', b'0');

-- ----------------------------
-- Table structure for system_post
-- ----------------------------
DROP TABLE IF EXISTS `system_post`;
CREATE TABLE `system_post`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位名称',
  `sort` int(11) NOT NULL COMMENT '显示顺序',
  `status` tinyint(4) NOT NULL COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_post
-- ----------------------------
INSERT INTO `system_post` VALUES (1, 'ceo', '董事长', 1, 0, '', 'admin', '2021-01-06 17:03:48', '1', '2022-04-19 16:53:39', b'0');
INSERT INTO `system_post` VALUES (2, 'se', '项目经理', 2, 0, '', 'admin', '2021-01-05 17:03:48', '1', '2021-12-12 10:47:47', b'0');
INSERT INTO `system_post` VALUES (4, 'user', '普通员工', 4, 0, '111', 'admin', '2021-01-05 17:03:48', '1', '2022-05-04 22:46:35', b'0');

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串',
  `sort` int(11) NOT NULL COMMENT '显示顺序',
  `data_scope` tinyint(4) NOT NULL DEFAULT 1 COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `data_scope_dept_ids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据范围(指定部门数组)',
  `status` tinyint(4) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `type` tinyint(4) NOT NULL COMMENT '角色类型',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES (1, '超级管理员', 'super_admin', 1, 1, '', 0, 1, '超级管理员', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:21', b'0');
INSERT INTO `system_role` VALUES (2, '普通角色', 'common', 2, 2, '', 0, 1, '普通角色', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:20', b'0');
INSERT INTO `system_role` VALUES (101, '测试账号', 'test', 0, 1, '[]', 0, 2, '132', '', '2021-01-06 13:49:35', '1', '2022-04-01 21:37:13', b'0');

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1729 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` tinyint(4) NULL DEFAULT 0 COMMENT '用户性别',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '头像地址',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1540614322441457666 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES (1, 'admin', '$2a$10$jsTY9CXjz7/2g6qFYhYZDeDTie.0oWTpHGvg8tzTWzvWBpGkwo866', '芋道源码', '管理员', 'aoteman@126.com', '15612345678', 1, 'http://127.0.0.1:48080/admin-api/infra/file/get/b7de3474-3805-4e09-80e3-185f20c31a74', 0, '0:0:0:0:0:0:0:1', '2022-07-07 00:00:00', 'admin', '2021-01-05 17:03:47', NULL, '2022-07-07 15:28:15', b'0');
INSERT INTO `system_user` VALUES (1540614322441457665, 'ztongkai', '$2a$10$xv12d3kwznAwrbrhFkKH0upULZDmBPB8sX2IQipxe5msU.4KhTxIy', '吴凯', NULL, '2580211264@qq.com', '13121022995', 1, 'http://127.0.0.1:48080/admin-api/infra/file/get/b7de3474-3805-4e09-80e3-185f20c31a74', 0, '', NULL, '1', '2022-06-25 16:34:12', '1', '2022-06-29 13:54:01', b'0');

-- ----------------------------
-- Table structure for system_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `system_user_dept`;
CREATE TABLE `system_user_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户ID',
  `dept_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '部门ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1540680272335450115 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户岗位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_user_dept
-- ----------------------------
INSERT INTO `system_user_dept` VALUES (111, 1, 100, 'admin', '2022-06-25 14:51:03', 'admin', '2022-06-25 14:51:21', b'0');
INSERT INTO `system_user_dept` VALUES (1540614322588258305, 1540614322441457665, 100, '1', '2022-06-25 16:34:12', '1', '2022-06-25 23:15:22', b'0');
INSERT INTO `system_user_dept` VALUES (1540678067884666881, 1540614322441457665, 101, '1', '2022-06-25 20:47:30', '1', '2022-06-25 23:15:29', b'0');
INSERT INTO `system_user_dept` VALUES (1540680272335450114, 1540614322441457665, 102, '1', '2022-06-25 20:56:16', '1', '2022-06-25 23:15:29', b'0');

-- ----------------------------
-- Table structure for system_user_post
-- ----------------------------
DROP TABLE IF EXISTS `system_user_post`;
CREATE TABLE `system_user_post`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '岗位ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1540678067985330178 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户岗位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_user_post
-- ----------------------------
INSERT INTO `system_user_post` VALUES (112, 1, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0');
INSERT INTO `system_user_post` VALUES (113, 100, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0');
INSERT INTO `system_user_post` VALUES (114, 114, 3, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0');
INSERT INTO `system_user_post` VALUES (1540614322659561474, 1540614322441457665, 1, '1', '2022-06-25 16:34:12', '1', '2022-06-25 23:15:14', b'0');
INSERT INTO `system_user_post` VALUES (1540678067985330177, 1540614322441457665, 2, '1', '2022-06-25 20:47:30', '1', '2022-06-25 23:15:16', b'0');

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES (1, 1, 1, '', '2022-01-11 13:19:45', '', '2022-06-29 13:51:45', b'0');
INSERT INTO `system_user_role` VALUES (2, 2, 2, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:13', b'0');
INSERT INTO `system_user_role` VALUES (4, 100, 101, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:13', b'0');
INSERT INTO `system_user_role` VALUES (5, 100, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:12', b'0');
INSERT INTO `system_user_role` VALUES (6, 100, 2, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:11', b'0');
INSERT INTO `system_user_role` VALUES (7, 104, 101, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:11', b'0');
INSERT INTO `system_user_role` VALUES (10, 103, 1, '1', '2022-01-11 13:19:45', '1', '2022-01-11 13:19:45', b'0');
INSERT INTO `system_user_role` VALUES (11, 107, 106, '1', '2022-02-20 22:59:33', '1', '2022-02-20 22:59:33', b'0');
INSERT INTO `system_user_role` VALUES (12, 108, 107, '1', '2022-02-20 23:00:50', '1', '2022-02-20 23:00:50', b'0');
INSERT INTO `system_user_role` VALUES (13, 109, 108, '1', '2022-02-20 23:11:50', '1', '2022-02-20 23:11:50', b'0');
INSERT INTO `system_user_role` VALUES (14, 110, 109, '1', '2022-02-22 00:56:14', '1', '2022-02-22 00:56:14', b'0');
INSERT INTO `system_user_role` VALUES (15, 111, 110, '110', '2022-02-23 13:14:38', '110', '2022-02-23 13:14:38', b'0');
INSERT INTO `system_user_role` VALUES (16, 113, 111, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0');
INSERT INTO `system_user_role` VALUES (17, 114, 101, '1', '2022-03-19 21:51:13', '1', '2022-03-19 21:51:13', b'0');
INSERT INTO `system_user_role` VALUES (18, 1, 2, '1', '2022-05-12 20:39:29', '1', '2022-05-12 20:39:29', b'0');
INSERT INTO `system_user_role` VALUES (19, 116, 113, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0');

SET FOREIGN_KEY_CHECKS = 1;
