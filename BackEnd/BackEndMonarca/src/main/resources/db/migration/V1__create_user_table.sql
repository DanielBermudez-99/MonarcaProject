create table user(
 id bigint not null auto_increment,
 name varchar(30),
 lastname varchar(30),
 username varchar(50) not null,
 password varchar(300) not null,
 email varchar(50),
 phone varchar(15),
 location varchar(50),
 complement varchar(50),
 address varchar(50),
 career varchar(20),
 street varchar(20),
 number varchar(20),
 date_register date,
 role_user varchar(15),
 primary key(id)
)