DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`
(
    `email`    VARCHAR(64)  NOT NULL,
    `password` VARCHAR(128) NOT NULL,
    `nickname` VARCHAR(32)  NOT NULL,
    PRIMARY KEY (`email`)
);

DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`
(
    `room_id` BIGINT NOT NULL AUTO_INCREMENT,
    `subject_id` BIGINT NOT NULL,
    PRIMARY KEY (`room_id`),
    UNIQUE INDEX `unq_room_id_subject_id` (`room_id`, `subject_id`)
);

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`
(
    `message_id` BIGINT NOT NULL AUTO_INCREMENT,
    `room_id` BIGINT NOT NULL,
    `sender_id` VARCHAR(64) NOT NULL,
    `content` VARCHAR(255) NOT NULL,
    `create_at` DATETIME NOT NULL,
    PRIMARY KEY (`message_id`),
    INDEX `idx_room_id_create_at` (`room_id`, `create_at`)
);

DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`
(
    `subject_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(64) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`subject_id`),
    INDEX `idx_name` (`name`)
);

DROP TABLE IF EXISTS `subject_subscribe`;
CREATE TABLE `subject_subscribe`
(
    `subject_id` BIGINT NOT NULL,
    `account_id` VARCHAR(64) NOT NULL,
    `subscribe_at` DATETIME NOT NULL,
    PRIMARY KEY (`subject_id`, `account_id`)
);

DROP TABLE IF EXISTS `subject_like`;
CREATE TABLE `subject_like`
(
    `subject_id` BIGINT NOT NULL,
    `account_id` VARCHAR(64) NOT NULL,
    `like_at` DATETIME NOT NULL,
    PRIMARY KEY (`subject_id`, `account_id`)
);

DROP TABLE IF EXISTS `subject_schedule`;
CREATE TABLE `subject_schedule`
(
    `schedule_id` BIGINT NOT NULL AUTO_INCREMENT,
    `subject_id` BIGINT NOT NULL,
    `start_at` DATETIME NOT NULL,
    `end_at` DATETIME NOT NULL,
    PRIMARY KEY (`schedule_id`),
    INDEX `idx_subject_id` (`subject_id`),
    UNIQUE INDEX `unq_subject_id_start_at_end_at` (`subject_id`, `start_at`, `end_at`)
);

DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`
(
    `client_id`               VARCHAR(256)  NOT NULL,
    `resource_ids`            VARCHAR(256)  NULL,
    `client_secret`           VARCHAR(256)  NULL,
    `scope`                   VARCHAR(256)  NULL,
    `authorized_grant_types`  VARCHAR(256)  NULL,
    `web_server_redirect_uri` VARCHAR(256)  NULL,
    `authorities`             VARCHAR(256)  NULL,
    `access_token_validity`   INT           NULL,
    `refresh_token_validity`  INT           NULL,
    `additional_information`  VARCHAR(4096) NULL,
    `autoapprove`             VARCHAR(256)  NULL,
    PRIMARY KEY (`client_id`)
);

DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`
(
    `token_id`          VARCHAR(256) NULL,
    `token`             BLOB         NULL,
    `authentication_id` VARCHAR(256) NOT NULL,
    `user_name`         VARCHAR(256) NULL,
    `client_id`         VARCHAR(256) NULL,
    `authentication`    BLOB         NULL,
    `refresh_token`     VARCHAR(256) NULL,
    PRIMARY KEY (`authentication_id`)
);

DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`
(
    `token_id`       VARCHAR(256) NULL,
    `token`          BLOB         NULL,
    `authentication` BLOB         NULL
);