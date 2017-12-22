DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_ADMIN', 100001);

INSERT INTO restaurants (name, address) VALUES
  ('Restaurant1', 'Sumskaya, 20'),
  ('Restaurant2', 'Nauki, 30');

INSERT INTO dishes (name, price) VALUES
  ('Meat', 20.0),
  ('Juice', 5.5),
  ('Fish', 25.0);
