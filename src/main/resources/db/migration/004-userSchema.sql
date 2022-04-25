--liquibase formatted sql
--changeset lukas:4


CREATE TABLE users
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    username TEXT NOT NULL,
    password TEXT NOT NULL
);
