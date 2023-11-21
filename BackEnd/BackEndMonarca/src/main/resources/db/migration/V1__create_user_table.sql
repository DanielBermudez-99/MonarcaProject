create table user(
 id bigint not null auto_increment,
 name varchar(30) not null,
 lastname varchar(30) not null,
 username varchar(50) not null,
 password varchar(300) not null,
 email varchar(50) not null,
 phone varchar(15) not null,
 location varchar(50) not null,
 complement varchar(50) not null,
 address varchar(50) not null,
 career varchar(20),
 street varchar(20),
 number varchar(20),
 date_register date not null,
 role_user varchar(15) not null,
 primary key(id)
)