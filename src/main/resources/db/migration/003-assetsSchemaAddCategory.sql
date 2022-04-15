--liquibase formatted sql
--changeset lukas:3

ALTER TABLE assets ADD COLUMN category
    TEXT