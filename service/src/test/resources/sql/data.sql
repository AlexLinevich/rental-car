INSERT INTO users (id, first_name, last_name, email, password, role)
VALUES (1, 'Bob', 'Robson', 'test1@tut.by', 'test1', 'ADMIN'),
       (2, 'Ivan', 'Ivanov', 'test2@tut.by', 'test2', 'CLIENT'),
       (3, 'Alex', 'Semenov', 'test3@tut.by', 'test3', 'CLIENT');
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO client_data (id, user_id, driver_licence_no, dl_expiration_day, credit_amount)
VALUES (1, (SELECT id FROM users WHERE last_name = 'Ivanov'), 'PB101121', '2025-8-20', 1000.0),
       (2, (SELECT id FROM users WHERE last_name = 'Semenov'), 'PB104466', '2028-1-30', 2000.0);
SELECT SETVAL('client_data_id_seq', (SELECT MAX(id) FROM client_data));

INSERT INTO car_category (id, category, day_price)
VALUES (1, 'MIDDLE SUV', 50.0),
       (2, 'LARGE SUV', 70.0),
       (3, 'LARGE SEDAN', 60.0);
SELECT SETVAL('car_category_id_seq', (SELECT MAX(id) FROM car_category));

INSERT INTO car (id, model, car_category_id, colour, seats_quantity, image)
VALUES (1, 'TOYOTA CAMRY', (SELECT id FROM car_category WHERE category = 'LARGE SEDAN'), 'WHITE', 5, 'IMAGE1'),
       (2, 'MAZDA 6', (SELECT id FROM car_category WHERE category = 'LARGE SEDAN'), 'BLACK', 5, 'IMAGE2'),
       (3, 'TOYOTA LANDCRUISER', (SELECT id FROM car_category WHERE category = 'LARGE SUV'), 'WHITE', 7, 'IMAGE3'),
       (4, 'MAZDA CX-9', (SELECT id FROM car_category WHERE category = 'LARGE SUV'), 'WHITE', 7, 'IMAGE4'),
       (5, 'TOYOTA RAV4', (SELECT id FROM car_category WHERE category = 'MIDDLE SUV'), 'BLACK', 5, 'IMAGE5'),
       (6, 'MAZDA CX-5', (SELECT id FROM car_category WHERE category = 'MIDDLE SUV'), 'RED', 5, 'IMAGE6'),
       (7, 'MAZDA 6', (SELECT id FROM car_category WHERE category = 'LARGE SEDAN'), 'WHITE', 5, 'IMAGE2');
SELECT SETVAL('car_id_seq', (SELECT MAX(id) FROM car));

INSERT INTO orders (id, user_id, car_id, begin_time, end_time, status)
VALUES (1, (SELECT id FROM users WHERE last_name = 'Ivanov'), 5,
        '2020-1-25 12:0', '2020-1-29 18:0', 'ACCEPTED'),
       (2, (SELECT id FROM users WHERE last_name = 'Robson'), 4,
        '2020-2-25 12:0', '2020-2-28 12:0', 'ACCEPTED'),
       (3, (SELECT id FROM users WHERE last_name = 'Semenov'), 4,
        '2020-5-25 12:0', '2020-5-28 12:0', 'ACCEPTED'),
       (4, (SELECT id FROM users WHERE last_name = 'Semenov'), 2,
        '2020-3-25 12:0', '2020-3-28 12:0', 'CANCELED');
SELECT SETVAL('orders_id_seq', (SELECT MAX(id) FROM orders));

INSERT INTO rental_time (id, car_id, begin_time, end_time, order_id)
VALUES (1, 5, '2020-1-25 12:0', '2020-1-29 18:0',
        (SELECT id
         FROM orders
         WHERE orders.car_id = 5
           AND orders.user_id = (SELECT id FROM users WHERE last_name = 'Ivanov'))),
       (2, 4, '2020-2-25 12:0', '2020-2-28 12:0',
        (SELECT id
         FROM orders
         WHERE orders.car_id = 4
           AND orders.user_id = (SELECT id FROM users WHERE last_name = 'Robson'))),
       (3, 4, '2020-5-25 12:0', '2020-5-28 12:0',
        (SELECT id
         FROM orders
         WHERE orders.car_id = 4
           AND orders.user_id = (SELECT id FROM users WHERE last_name = 'Semenov'))),
       (4, 2, '2020-3-25 12:0', '2020-3-28 12:0',
        (SELECT id
         FROM orders
         WHERE orders.car_id = 2
           AND orders.user_id = (SELECT id FROM users WHERE last_name = 'Semenov')));
SELECT SETVAL('rental_time_id_seq', (SELECT MAX(id) FROM rental_time));
