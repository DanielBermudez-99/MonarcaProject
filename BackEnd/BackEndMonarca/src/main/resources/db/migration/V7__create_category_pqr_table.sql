CREATE TABLE pqr (
    id BIGINT PRIMARY KEY not null AUTO_INCREMENT,
    name VARCHAR(255) not null,
    description VARCHAR(255) not null,
    date_register DATE  not null,
    date_solution DATE not null,
    deadline DATE not null,
    status VARCHAR(255) not null,
    priority VARCHAR(255) not null,
    user_id BIGINT ,
    FOREIGN KEY (user_id) REFERENCES user(id)
);