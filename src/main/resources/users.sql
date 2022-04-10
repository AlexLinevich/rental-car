create table users
(
	id serial not null
		constraint users_pkey
			primary key,
	first_name varchar(128) not null,
	last_name varchar(128) not null,
	email varchar(256) not null
		constraint users_email_key
			unique,
	password varchar(128) not null,
	role varchar(32) not null
);

