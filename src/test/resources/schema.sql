CREATE TABLE `accounts` (
    `accountId` bigint(20) NOT NULL AUTO_INCREMENT,
    `email` varchar(64) NOT NULL,
    `password` varchar(64) NOT NULL,
    `nickname` varchar(32) NOT NULL,
    PRIMARY KEY (`accountId`),
    UNIQUE KEY `unq_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;