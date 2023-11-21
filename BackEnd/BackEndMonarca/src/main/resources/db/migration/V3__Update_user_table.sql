ALTER TABLE user ADD disabled tinyint NOT NULL;
ALTER TABLE user ADD locked tinyint NOT NULL;

UPDATE user SET disabled = 0;
UPDATE user SET locked = 0;