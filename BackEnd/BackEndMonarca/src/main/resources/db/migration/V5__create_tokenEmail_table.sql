create table password_reset_token(
    id bigint not null auto_increment,
    token varchar(255) not null unique,
    user_id bigint not null,
    expiry_date datetime not null,
    primary key (id),
    foreign key (user_id) references user(id)
);