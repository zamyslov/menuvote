DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM menus_dishes;
DELETE FROM votes;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', '$2a$10$DLVDKA7WrLvKwdcQy5HdjepOy74gIGQlkh159EqEys3bOtUE3aOHK'), --100000
  ('User1', 'user1@yandex.ru', 'password'), --100001
  ('User2', 'user2@yandex.ru', 'password'), --100002
  ('User3', 'user3@yandex.ru', 'password'), --100003
  ('Admin', 'admin@gmail.com', '$2a$10$TjHDJyLWPR5umEHZs4Vame7V98jz4FJvvk63uOXm6YF0s4PeJMCjy'); --100004

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_USER', 100002),
  ('ROLE_USER', 100003),
  ('ROLE_ADMIN', 100004);

INSERT INTO restaurants (name, address) VALUES
  ('Restaurant1', 'Sumskaya, 20'), --100005
  ('Restaurant2', 'Nauki, 30'); --100006

INSERT INTO dishes (name) VALUES
  ('Meat'), --100007
  ('Juice'), --100008
  ('Fish'); --100009

INSERT INTO menus (restaurant_id, date) VALUES
  (100005, '2017-12-20'), --100010
  (100006, '2017-12-20'), --100011
  (100005, '2017-12-21'), --100012
  (100006, '2017-12-21'); --100013

  INSERT INTO menus_dishes (menu_id, dish_id, price) VALUES
  (100010, 100007, 20.0), --100014
  (100010, 100008, 5.5),  --100015
  (100011, 100008, 5.5),  --100016
  (100011, 100009, 40.5), --100017
  (100012, 100007, 15.5), --100018
  (100013, 100009, 25.5); --100019

INSERT INTO votes (USER_ID, MENU_ID, date) VALUES
  (100000, 100010, '2017-12-20'), --100020
  (100002, 100010, '2017-12-20'), --100021
  (100004, 100010, '2017-12-20'), --100022
  (100001, 100011, '2017-12-20'), --100023
  (100002, 100012, '2017-12-21'), --100024
  (100004, 100012, '2017-12-21'); --100025
