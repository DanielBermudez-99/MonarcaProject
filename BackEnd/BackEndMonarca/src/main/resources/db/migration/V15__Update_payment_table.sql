ALTER TABLE payment
ADD COLUMN user_id BIGINT;

ALTER TABLE payment
ADD CONSTRAINT FK_payment_user
FOREIGN KEY (user_id)
REFERENCES user (id);