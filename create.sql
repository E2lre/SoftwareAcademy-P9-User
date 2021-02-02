--create table patient (id bigint not null auto_increment, address varchar(200), birthdate date, firstname varchar(100), lastname varchar(100), phone varchar(12), sex varchar(1), primary key (id)) engine=InnoDB
--create table user (username varchar(100) not null, pwd varchar(100), primary key (username)) engine=InnoDB
--create table user_roles (user_username varchar(100) not null, roles integer) engine=InnoDB
--alter table user_roles add constraint FK1misndtpfm9hx3ttvixdus8d1 foreign key (user_username) references user (username)
create table patient (id bigint not null auto_increment, address varchar(200), birthdate date, firstname varchar(100), lastname varchar(100), phone varchar(12), sex varchar(1), primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, pwd varchar(100), username varchar(100), primary key (id)) engine=InnoDB
create table user_roles (user_id bigint not null, roles integer) engine=InnoDB
alter table user add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username)
alter table user_roles add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references user (id)
