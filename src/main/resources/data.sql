DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM menus_dishes;
DELETE FROM votes;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'), --100000
  ('User1', 'user1@yandex.ru', 'password'), --100001
  ('User2', 'user2@yandex.ru', 'password'), --100002
  ('User3', 'user3@yandex.ru', 'password'), --100003
  ('Admin', 'admin@gmail.com', 'admin'); --100004

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
  (100006, '2017-12-20'); --100011

  INSERT INTO menus_dishes (menu_id, dish_id, price) VALUES
  (100010, 100007, 20.0), --100012
  (100010, 100008, 5.5),  --100013
  (100011, 100008, 5.5),  --100014
  (100011, 100009, 40.5); --100015

INSERT INTO votes (USER_ID, MENU_ID, date) VALUES
  (100000, 100010, '2017-12-20'), --100016
  (100002, 100010, '2017-12-20'), --100017
  (100004, 100010, '2017-12-20'), --100018
  (100001, 100011, '2017-12-20'); --100019
