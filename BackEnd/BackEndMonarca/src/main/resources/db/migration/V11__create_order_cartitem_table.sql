CREATE TABLE order_cartitem (
        order_id BIGINT NOT NULL,
        cartitem_id BIGINT NOT NULL,
        PRIMARY KEY (order_id, cartitem_id),
        FOREIGN KEY (order_id) REFERENCES orders (id),
        FOREIGN KEY (cartitem_id) REFERENCES cart_item (id)
);