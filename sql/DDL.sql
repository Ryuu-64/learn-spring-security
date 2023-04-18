CREATE SCHEMA IF NOT EXISTS learn_spring_security
    CHARACTER SET utf8mb4
    COLLATE = utf8mb4_unicode_ci;

USE learn_spring_security;

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    username VARCHAR(50)  NOT NULL PRIMARY KEY,
    password VARCHAR(500) NOT NULL
) ENGINE = InnoDB
  CHARACTER SET utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role
(
    username VARCHAR(50) NOT NULL,
    role     VARCHAR(50) NOT NULL,
    PRIMARY KEY (username, role)
) ENGINE = InnoDB
  CHARACTER SET utf8mb4
  COLLATE = utf8mb4_unicode_ci;