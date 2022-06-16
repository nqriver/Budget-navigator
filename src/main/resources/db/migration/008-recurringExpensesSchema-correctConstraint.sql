-- liquibase formatted sql
-- changeset lukas:8

ALTER TABLE recurring_expenses DROP CONSTRAINT check_reccuring_expenses_month;

ALTER TABLE recurring_expenses ADD CONSTRAINT check_recurring_expenses_month
    CHECK (month BETWEEN 1 AND 12);
