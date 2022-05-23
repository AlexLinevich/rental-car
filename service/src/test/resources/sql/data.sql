INSERT INTO users (first_name, last_name, email, password, role)
VALUES ('Bob', 'Robson', 'test1@tut.by', 'test1', 'ADMIN'),
       ('Ivan', 'Ivanov', 'test2@tut.by', 'test2', 'CLIENT'),
       ('Alex', 'Semenov', 'test3@tut.by', 'test3', 'CLIENT');

INSERT INTO client_data (user_id, driver_licence_no, dl_expiration_day, credit_amount)
VALUES ((SELECT id FROM users WHERE last_name = 'Ivanov'), 'PB101121', '2025-8-20', 1000.0),
       ((SELECT id FROM users WHERE last_name = 'Semenov'), 'PB104466', '2028-1-30', 2000.0);

INSERT INTO car_category (category, day_price)
VALUES ('MIDDLE SUV', 50.0),
       ('LARGE SUV', 70.0),
       ('LARGE SEDAN', 60.0);

INSERT INTO car (model, car_category_id, colour, seats_quantity, image)
VALUES ('TOYOTA CAMRY', (SELECT id FROM car_category WHERE category = 'LARGE SEDAN'), 'WHITE', 5, 'IMAGE1'),
       ('MAZDA 6', (SELECT id FROM car_category WHERE category = 'LARGE SEDAN'), 'BLACK', 5, 'IMAGE2'),
       ('TOYOTA LANDCRUISER', (SELECT id FROM car_category WHERE category = 'LARGE SUV'), 'WHITE', 7, 'IMAGE3'),
       ('MAZDA CX-9', (SELECT id FROM car_category WHERE category = 'LARGE SUV'), 'WHITE', 7, 'IMAGE4'),
       ('TOYOTA RAV4', (SELECT id FROM car_category WHERE category = 'MIDDLE SUV'), 'BLACK', 5, 'IMAGE5'),
       ('MAZDA CX-5', (SELECT id FROM car_category WHERE category = 'MIDDLE SUV'), 'RED', 5, 'IMAGE6');

INSERT INTO orders (user_id, car_id, begin_time, end_time, status)
VALUES ((SELECT id FROM users WHERE last_name = 'Ivanov'), (SELECT id FROM car WHERE model = 'TOYOTA RAV4'),
        '2020-1-25 12:0', '2020-1-29 18:0', 'ACCEPTED'),
       ((SELECT id FROM users WHERE last_name = 'Robson'), (SELECT id FROM car WHERE model = 'MAZDA CX-9'),
        '2020-2-25 12:0', '2020-2-28 12:0', 'ACCEPTED'),
       ((SELECT id FROM users WHERE last_name = 'Semenov'), (SELECT id FROM car WHERE model = 'MAZDA CX-9'),
        '2020-5-25 12:0', '2020-5-28 12:0', 'ACCEPTED'),
       ((SELECT id FROM users WHERE last_name = 'Semenov'), (SELECT id FROM car WHERE model = 'MAZDA 6'),
        '2020-3-25 12:0', '2020-3-28 12:0', 'CANCELED');

INSERT INTO rental_time (car_id, begin_time, end_time, order_id)
VALUES ((SELECT id FROM car WHERE model = 'TOYOTA RAV4'), '2020-1-25 12:0', '2020-1-29 18:0',
        (SELECT id
         FROM orders
         WHERE orders.car_id = (SELECT id FROM car WHERE model = 'TOYOTA RAV4')
           AND orders.user_id = (SELECT id FROM users WHERE last_name = 'Ivanov'))),
       ((SELECT id FROM car WHERE model = 'MAZDA CX-9'), '2020-2-25 12:0', '2020-2-28 12:0',
        (SELECT id
         FROM orders
         WHERE orders.car_id = (SELECT id FROM car WHERE model = 'MAZDA CX-9')
           AND orders.user_id = (SELECT id FROM users WHERE last_name = 'Robson'))),
       ((SELECT id FROM car WHERE model = 'MAZDA CX-9'), '2020-5-25 12:0', '2020-5-28 12:0',
        (SELECT id
         FROM orders
         WHERE orders.car_id = (SELECT id FROM car WHERE model = 'MAZDA CX-9')
           AND orders.user_id = (SELECT id FROM users WHERE last_name = 'Semenov'))),
       ((SELECT id FROM car WHERE model = 'MAZDA 6'), '2020-3-25 12:0', '2020-3-28 12:0',
        (SELECT id
         FROM orders
         WHERE orders.car_id = (SELECT id FROM car WHERE model = 'MAZDA 6')
           AND orders.user_id = (SELECT id FROM users WHERE last_name = 'Semenov')));