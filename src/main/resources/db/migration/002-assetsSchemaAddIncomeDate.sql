--liquibase formatted sql
--changeset lukas:2

ALTER TABLE assets ADD COLUMN income_date
    TIMESTAMP
    NOT NULL
    DEFAULT CURRENT_TIMESTAMP