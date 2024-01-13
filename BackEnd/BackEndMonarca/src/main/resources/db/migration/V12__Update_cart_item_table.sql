-- src/main/resources/db/migration/V11__add_is_active_to_cart_item.sql
ALTER TABLE cart_item ADD COLUMN is_active BOOLEAN DEFAULT TRUE;