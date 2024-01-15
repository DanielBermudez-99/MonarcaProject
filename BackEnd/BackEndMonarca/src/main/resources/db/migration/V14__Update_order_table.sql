ALTER TABLE orders
ADD COLUMN payment_id BIGINT,
ADD CONSTRAINT fk_payment
FOREIGN KEY (payment_id)
REFERENCES payment(id);