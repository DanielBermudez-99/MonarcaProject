create table product(
  id bigint not null auto_increment,
  name VARCHAR(150) NOT NULL,
  description VARCHAR(200) NOT NULL,
  stock INT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  image BLOB NOT NULL,
  warranty VARCHAR(255) NOT NULL,
  brand VARCHAR(255) NOT NULL,
  size VARCHAR(255) NOT NULL,
  color VARCHAR(255) NOT NULL,
  type VARCHAR(255) NOT NULL,
  disabled tinyint NOT NULL DEFAULT FALSE,
  locked tinyint NOT NULL DEFAULT FALSE,
  primary key (id)
)
