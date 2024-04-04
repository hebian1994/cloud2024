/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3306
 Source Schema         : db2024

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 04/04/2024 21:28:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_pay
-- ----------------------------
DROP TABLE IF EXISTS `t_pay`;
CREATE TABLE `t_pay`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `pay_no` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '支付流水号',
  `order_no` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '订单流水号',
  `user_id` int NULL DEFAULT 1 COMMENT '用户账号ID',
  `amount` decimal(8, 2) NOT NULL DEFAULT 9.90 COMMENT '交易金额',
  `deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标志, 默认0不删除,1删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '支付交易表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_pay
-- ----------------------------
INSERT INTO `t_pay` VALUES (1, 'pay202403121420', '202403121421', 1, 9.90, 0, '2024-03-12 14:23:55', '2024-03-12 19:05:02');
INSERT INTO `t_pay` VALUES (2, 'pay202403122048', '202403122048', 1, 9.90, 0, '2024-03-12 20:58:37', '2024-03-12 21:05:02');
INSERT INTO `t_pay` VALUES (3, 'pay202403130910', '202403130910', 0, 0.00, 0, '2024-03-12 21:52:33', '2024-03-13 09:10:27');
INSERT INTO `t_pay` VALUES (5, 'pay2024031220499', '20240312204799', 1, 9.90, 0, '2024-03-13 09:43:45', '2024-03-13 09:43:45');
INSERT INTO `t_pay` VALUES (6, 'feign2024031220499', 'feign2024031220499', 1, 9.90, 0, '2024-03-14 22:53:59', '2024-03-14 22:53:59');
INSERT INTO `t_pay` VALUES (55, '11', 'str11ing', 0, 0.00, 0, '2024-04-02 14:20:50', '2024-04-02 14:20:50');

SET FOREIGN_KEY_CHECKS = 1;
