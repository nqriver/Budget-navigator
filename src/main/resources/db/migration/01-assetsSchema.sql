--liquibase formatted sql
--changeset lukas:1


CREATE TABLE assets
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount NUMERIC
);
