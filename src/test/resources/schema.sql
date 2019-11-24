CREATE TABLE `accounts` (
    `email` varchar(64) NOT NULL,
    `password` varchar(128) NOT NULL,
    `enabled` tinyint(4) NOT NULL DEFAULT '1',
    `nickname` varchar(32) NOT NULL,
    PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;