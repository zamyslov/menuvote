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
  ('Admin', 'admin@gmail.com', 'admin'); --100003

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_USER', 100002),
  ('ROLE_USER', 100003),
  ('ROLE_ADMIN', 100003);

INSERT INTO restaurants (name, address) VALUES
  ('Restaurant1', 'Sumskaya, 20'), --100004
  ('Restaurant2', 'Nauki, 30'); --100005

INSERT INTO dishes (name) VALUES
  ('Meat'), --100006
  ('Juice'), --100007
  ('Fish'); --100008

INSERT INTO menus (restaurant_id, date) VALUES
  (100004, '2017-12-20'), --100009
  (100005, '2017-12-20'); --100010

  INSERT INTO menus_dishes (menu_id, dish_id, price) VALUES
  (100009, 100006, 20.0), --100011
  (100009, 100007, 5.5),  --100012
  (100010, 100007, 5.5),  --100013
  (100010, 100008, 40.5); --100014

INSERT INTO votes (USER_ID, MENU_ID) VALUES
  (100000, 100009), --100015
  (100002, 100009), --100016
  (100003, 100009), --100017
  (100001, 100010); --100018
