CREATE TABLE IF NOT EXISTS `accounts` (
    `email` varchar(64) NOT NULL,
    `password` varchar(128) NOT NULL,
    `nickname` varchar(32) NOT NULL,
    PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;