DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS menus_dishes;
DROP TABLE IF EXISTS votes;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
  id               INTEGER default global_seq.nextval primary key,
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL,
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id          INTEGER                 NOT NULL,
  role             VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
  id               INTEGER default global_seq.nextval primary key,
  name             VARCHAR                 NOT NULL,
  address          VARCHAR                 NOT NULL,
);
CREATE UNIQUE INDEX restaurants_unique_name_idx ON restaurants (name);

CREATE TABLE dishes
(
  id               INTEGER default global_seq.nextval primary key,
  name             VARCHAR                 NOT NULL,
);
CREATE UNIQUE INDEX dishes_unique_name_idx ON dishes (name);

CREATE TABLE menus
(
  id               INTEGER default global_seq.nextval primary key,
  restaurant_id    INTEGER                 NOT NULL,
  date             DATE                    NOT NULL,
  --CONSTRAINT menus_idx UNIQUE (restaurant_id, date),
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
);
CREATE UNIQUE INDEX menus_idx ON menus (restaurant_id, date);

CREATE TABLE menus_dishes
(
  id               INTEGER default global_seq.nextval primary key,
  menu_id          INTEGER                 NOT NULL,
  dish_id          INTEGER                 NOT NULL,
  price            DOUBLE                  NOT NULL,
  --CONSTRAINT menus_idx UNIQUE (dish_id, menu_id),
  FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE,
  FOREIGN KEY (dish_id) REFERENCES dishes (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX menus_dishes_idx ON menus_dishes (menu_id, dish_id);

CREATE TABLE votes
(
  id               INTEGER default global_seq.nextval primary key,
  user_id          INTEGER                 NOT NULL,
  menu_id          INTEGER                 NOT NULL,
  date             DATE                    NOT NULL,
--  CONSTRAINT votes_idx UNIQUE (user_id, menu_id),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX votes_idx ON votes (user_id, date);
