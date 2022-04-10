create table orders
(
	id serial not null
		constraint orders_pkey
			primary key,
	user_id integer not null
		constraint orders_user_id_fkey
			references users,
	car_id integer not null
		constraint orders_car_id_fkey
			references car,
	begin_time timestamp not null,
	end_time timestamp not null,
	status varchar(32) not null,
	message text
);

