--liquibase formatted sql
--changeset lukas:6

DROP TABLE IF EXISTS expenses;
CREATE TABLE expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount NUMERIC NOT NULL,
    date TIMESTAMP
        NOT NULL
        DEFAULT CURRENT_TIMESTAMP,
    category VARCHAR(90) DEFAULT 'PERSONAL_SPENDING',
    user_id BIGINT NOT NULL DEFAULT 1
);

ALTER TABLE expenses ADD CONSTRAINT fk_expense_user_id
FOREIGN KEY (user_id) REFERENCES users(id);
