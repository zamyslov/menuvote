DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS menus;
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
  price            DOUBLE                  NOT NULL,
);
CREATE UNIQUE INDEX dishes_unique_name_idx ON dishes (name);

CREATE TABLE menus
(
  id               INTEGER default global_seq.nextval primary key,
  restaurant_id    INTEGER                 NOT NULL,
  dish_id          INTEGER                 NOT NULL,
  menu_date        DATE                    NOT NULL,
  CONSTRAINT menus_idx UNIQUE (restaurant_id, dish_id, menu_date),
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
  FOREIGN KEY (dish_id) REFERENCES dishes (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
  id               INTEGER default global_seq.nextval primary key,
  user_id          INTEGER                 NOT NULL,
  menu_id          INTEGER                 NOT NULL,
  vote_date        DATE DEFAULT now() NOT NULL,
  CONSTRAINT votes_idx UNIQUE (user_id, vote_date),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE
);
