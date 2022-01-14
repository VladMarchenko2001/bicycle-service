CREATE SCHEMA IF NOT EXISTS `bicycle` DEFAULT CHARACTER SET utf8;
USE `bicycle`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


CREATE TABLE `users` (
                           `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                           `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                           `license_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                           `login` varchar(255) NOT NULL,
                           `password` varchar(255) NOT NULL,
                           `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                           PRIMARY KEY (`id`) USING BTREE,
                           UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;



DROP TABLE IF EXISTS `manufacturers`;
CREATE TABLE `manufacturers`  (
                                  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
                                  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                  `country` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                  `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `bicycles`;
CREATE TABLE `bicycles`  (
                         `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
                         `model` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                         `manufacturer_id` bigint(0) UNSIGNED NOT NULL,
                         `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                         PRIMARY KEY (`id`) USING BTREE,
                         INDEX `FK_manufacturer_id`(`manufacturer_id`) USING BTREE,
                         CONSTRAINT `FK_manufacturer_id` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturers` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `bicycles_users`;
CREATE TABLE `bicycles_users`  (
                                 `bicycle_id` bigint(0) UNSIGNED NOT NULL,
                                 `user_id` bigint(0) UNSIGNED NOT NULL,
                                 PRIMARY KEY (`bicycle_id`, `user_id`) USING BTREE,
                                 INDEX `user_id`(`user_id`) USING BTREE,
                                 INDEX `bicycle_id`(`bicycle_id`) USING BTREE,
                                 CONSTRAINT `car_id` FOREIGN KEY (`bicycle_id`) REFERENCES `bicycles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                 CONSTRAINT `driver_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
