CREATE TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    card_number VARCHAR(16) NOT NULL,
    card_holder_name VARCHAR(255) NOT NULL,
    expiry_date DATE NOT NULL,
    cvv VARCHAR(3) NOT NULL,
    balance DOUBLE NOT NULL
);