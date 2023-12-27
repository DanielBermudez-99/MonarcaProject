ALTER TABLE product
ADD COLUMN category_id BIGINT,
ADD CONSTRAINT fk_product_category
FOREIGN KEY (category_id)
REFERENCES category(id);