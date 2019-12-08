DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts`
(
    `email`    varchar(64)  NOT NULL,
    `password` varchar(128) NOT NULL,
    `nickname` varchar(32)  NOT NULL,
    PRIMARY KEY (`email`)
);

DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`
(
    `room_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`room_id`)
);

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`
(
    `message_id` BIGINT NOT NULL AUTO_INCREMENT,
    `room_id` BIGINT NOT NULL,
    `sender_id` VARCHAR(64) NOT NULL,
    `content` VARCHAR(255) NOT NULL,
    `created_at` DATETIME NOT NULL,
    PRIMARY KEY (`message_id`),
    INDEX `idx_room_id_created_at` (`room_id` ASC, `created_at` DESC)
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