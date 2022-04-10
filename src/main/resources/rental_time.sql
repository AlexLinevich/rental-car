create table rental_time
(
	id serial not null
		constraint rental_time_pkey
			primary key,
	car_id integer not null
		constraint rental_time_car_id_fkey
			references car,
	begin_time timestamp not null,
	end_time timestamp not null,
	order_id integer not null
		constraint rental_time_order_id_fkey
			references orders
);

