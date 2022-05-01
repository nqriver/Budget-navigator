--liquibase formatted sql
--changeset lukas:5

ALTER TABLE assets
    ADD user_id BIGINT NOT NULL
        DEFAULT 1;

ALTER TABLE assets
    ADD CONSTRAINT fk_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id);
