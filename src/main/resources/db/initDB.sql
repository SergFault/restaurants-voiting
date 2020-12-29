DROP TABLE IF EXISTS dishes CASCADE;
DROP TABLE IF EXISTS restaurants CASCADE;
DROP TABLE IF EXISTS votes CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP SEQUENCE IF EXISTS global_seq CASCADE;

CREATE SEQUENCE global_seq START WITH 10000;

create table restaurants
(
    id   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name varchar(255) not null
);

create table dishes
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    rest_id  bigint       not null,
    price     integer      not null,
    dish_name varchar(255) not null,
    FOREIGN KEY (rest_id ) REFERENCES restaurants (id) ON DELETE CASCADE
);


create table users
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name varchar(255) not null,
    email      varchar(255)                      not null,
    CONSTRAINT email_idx UNIQUE (email),
    enabled    boolean             default true  not null,
    password   varchar(255)                      not null,
    registered timestamp           default now() not null
);

create table user_roles
(
    user_id   INTEGER not null,
    role varchar(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

create table votes
(
    id           INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    dateTime timestamp           default now() not null,
    rating       integer                           not null,
    rest_id      bigint                            not null,
    user_id      bigint                            not null,
    FOREIGN KEY (rest_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE

);
