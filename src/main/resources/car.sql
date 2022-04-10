create table car
(
	id serial not null
		constraint car_pkey
			primary key,
	model varchar(32) not null,
	car_category_id integer not null
		constraint car_car_category_id_fkey
			references car_category,
	colour varchar(32) not null,
	seats_quantity integer not null
);

