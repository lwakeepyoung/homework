CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `mobile` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `login_time` timestamp(1) NULL DEFAULT NULL,
  `create_time` timestamp(1) NULL DEFAULT NULL,
  `update_time` timestamp(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin

CREATE TABLE `user_address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `tag` varchar(45) DEFAULT NULL COMMENT '标签',
  `mobile` varchar(45) DEFAULT NULL COMMENT '联系人电话',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `goods` (
  `id` bigint NOT NULL,
  `name` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `price` double(6,2) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `num` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin

CREATE TABLE `shopping_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `good_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `num` bigint DEFAULT NULL COMMENT '购物数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin

CREATE TABLE `order` (
  `id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  `update_time` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin