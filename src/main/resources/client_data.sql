create table client_data
(
	id serial not null
		constraint client_data_pkey
			primary key,
	user_id integer not null
		constraint client_data_user_id_fkey
			references users,
	driver_licence_no varchar(32) not null
		constraint client_data_driver_licence_no_key
			unique,
	dl_expiration_day date not null,
	credit_amount numeric(8,2) not null
);

