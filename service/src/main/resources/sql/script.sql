CREATE DATABASE new_rental_car;

CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name  VARCHAR(128) NOT NULL,
    email      VARCHAR(256) NOT NULL UNIQUE,
    password   VARCHAR(128) NOT NULL,
    role       VARCHAR(32)  not null
);

CREATE TABLE  IF NOT EXISTS client_data
(
    id                SERIAL PRIMARY KEY,
    user_id           INT REFERENCES users (id) NOT NULL UNIQUE,
    driver_licence_no VARCHAR(32)               NOT NULL UNIQUE,
    dl_expiration_day DATE                      NOT NULL,
    credit_amount     NUMERIC(8, 2)             NOT NULL
);

CREATE TABLE  IF NOT EXISTS car_category
(
    id        SERIAL PRIMARY KEY,
    category  VARCHAR(128)  NOT NULL UNIQUE,
    day_price NUMERIC(8, 2) NOT NULL
);

CREATE TABLE  IF NOT EXISTS car
(
    id              SERIAL PRIMARY KEY,
    model           VARCHAR(32)                      NOT NULL,
    car_category_id INT REFERENCES car_category (id) NOT NULL,
    colour          VARCHAR(32)                      NOT NULL,
    seats_quantity  INT                              NOT NULL,
    image           VARCHAR(128)                     NOT NULL
);

CREATE TABLE  IF NOT EXISTS orders
(
    id         SERIAL PRIMARY KEY,
    user_id    INT REFERENCES users (id) NOT NULL,
    car_id     INT REFERENCES car (id)   NOT NULL,
    begin_time TIMESTAMP                 NOT NULL,
    end_time   TIMESTAMP                 NOT NULL,
    status     VARCHAR(32)               NOT NULL,
    message    TEXT
);

CREATE TABLE  IF NOT EXISTS rental_time
(
    id         SERIAL PRIMARY KEY,
    car_id     INT REFERENCES car (id)    NOT NULL,
    begin_time TIMESTAMP                  NOT NULL,
    end_time   TIMESTAMP                  NOT NULL,
    order_id   INT REFERENCES orders (id) NOT NULL
);

