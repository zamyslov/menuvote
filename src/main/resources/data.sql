DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM votes;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'), //100000
  ('User1', 'user1@yandex.ru', 'password'), //100001
  ('User2', 'user2@yandex.ru', 'password'), //100002
  ('Admin', 'admin@gmail.com', 'admin'); //100003

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_USER', 100002),
  ('ROLE_USER', 100003),
  ('ROLE_ADMIN', 100003);

-- INSERT INTO restaurants (name, address) VALUES
--   ('Restaurant1', 'Sumskaya, 20'), //100004
--   ('Restaurant2', 'Nauki, 30'); //100005
--
-- INSERT INTO dishes (name, price) VALUES
--   ('Meat', 20.0), //100006
--   ('Juice', 5.5), //100007
--   ('Fish', 25.0); //100008
--
-- INSERT INTO menus (restaurant_id, dish_id, menu_date) VALUES
--   (100004, 100007, '2017-12-20'), //100009
--   (100004, 100008, '2017-12-20'), //100010
--   (100005, 100006, '2017-12-20'), //100011
--   (100005, 100007, '2017-12-20'); //100012
--
-- INSERT INTO votes (USER_ID, MENU_ID, VOTE_DATE) VALUES
--   (100000, 100009, '2017-12-20'), //100013
--   (100002, 100009, '2017-12-20'), //100014
--   (100003, 100009, '2017-12-20'), //100015
--   (100001, 100010, '2017-12-20'); //100016
