/*
Navicat MySQL Data Transfer

Source Server         : my
Source Server Version : 80025
Source Host           : localhost:3307
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 80025
File Encoding         : 65001

Date: 2022-11-10 16:02:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('1', '老司机');

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `id` bigint NOT NULL COMMENT '商品id',
  `goods_name` varchar(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品图片',
  `goods_detail` longtext COLLATE utf8mb4_general_ci COMMENT '商品详情',
  `goods_price` decimal(12,2) DEFAULT '0.00' COMMENT '商品价格',
  `goods_stock` int DEFAULT '0' COMMENT '商品库存',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES ('1', 'iphone 12 64GB', 'iphone12 64GB', '/img/iphone12.png', 'iphone12 64GB', '5299.00', '100');
INSERT INTO `t_goods` VALUES ('2', 'iphone12pro64GB', 'iphone12pro64GB', '/img/iphone12pro.png', 'iphone12pro64GB', '9299.00', '100');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` bigint DEFAULT NULL,
  `goods_id` bigint DEFAULT NULL,
  `delivery_addr_id` bigint DEFAULT NULL COMMENT '收货地址id',
  `goods_name` varchar(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int DEFAULT '0' COMMENT '商品数量',
  `goods_price` decimal(12,2) DEFAULT '0.00' COMMENT '商品单价',
  `order_channel` tinyint DEFAULT '0' COMMENT '1pc,2android,3ios',
  `status` tinyint DEFAULT '0' COMMENT '0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成',
  `create_date` datetime DEFAULT NULL COMMENT '订单创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('10', '15689929571', '1', '0', 'iphone 12 64GB', '1', '4000.00', '1', '0', '2022-04-06 21:18:07', null);
INSERT INTO `t_order` VALUES ('11', '15689929571', '1', '0', 'iphone 12 64GB', '1', '4000.00', '1', '0', '2022-04-06 21:21:52', null);

-- ----------------------------
-- Table structure for t_seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_goods`;
CREATE TABLE `t_seckill_goods` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '秒杀商品id',
  `goods_id` bigint DEFAULT NULL,
  `seckill_price` decimal(12,2) DEFAULT '0.00' COMMENT '秒杀价格',
  `stock_count` bigint DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_seckill_goods
-- ----------------------------
INSERT INTO `t_seckill_goods` VALUES ('1', '1', '4000.00', '9', '2022-04-06 16:43:28', '2022-04-07 20:48:36');
INSERT INTO `t_seckill_goods` VALUES ('2', '2', '5000.00', '10', '2022-04-05 20:48:31', '2022-04-05 20:48:36');

-- ----------------------------
-- Table structure for t_seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_order`;
CREATE TABLE `t_seckill_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '秒杀商品id',
  `user_id` bigint DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `goods_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_seckill_order
-- ----------------------------
INSERT INTO `t_seckill_order` VALUES ('4', '15689929571', '11', '1');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id，手机号码',
  `nickname` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'MD5（MD5（pass明文+固定salt）+salt）',
  `slat` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `head` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `login_count` int DEFAULT NULL COMMENT '登录次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15689929572 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('15689929571', '光影', 'e53d30b06d4d0fb2e0d0bd6412e66440', '1a2b3c4d', null, '2022-04-04 21:01:25', '2022-04-04 21:01:28', null);
