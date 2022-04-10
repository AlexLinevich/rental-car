create table car_category
(
	id serial not null
		constraint car_category_pkey
			primary key,
	category varchar(128) not null
		constraint car_category_category_key
			unique,
	day_price numeric(8,2) not null
);

