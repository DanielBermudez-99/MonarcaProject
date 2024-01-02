CREATE TABLE orders (
    id BIGINT PRIMARY KEY not null AUTO_INCREMENT,
    date_purchase DATE,
    date_payment DATE,
    date_delivery DATE,
    total_price DOUBLE,
    status VARCHAR(255),
    payment_method VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user (id)
);