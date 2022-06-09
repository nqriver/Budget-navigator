-- liquibase formatted sql
-- changeset lukas:7

DROP TABLE IF EXISTS recurring_expenses;
CREATE TABLE recurring_expenses (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          amount NUMERIC NOT NULL,
                          category VARCHAR(90) DEFAULT 'PERSONAL_SPENDING',
                          user_id BIGINT NOT NULL,
                          month SMALLINT,
                          day SMALLINT

);

ALTER TABLE recurring_expenses ADD CONSTRAINT fk_recurring_expense_user_id
    FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE recurring_expenses ADD CONSTRAINT check_reccuring_expenses_month
    CHECK (month BETWEEN  1 AND 12)

