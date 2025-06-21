create table users
(
    id       bigserial primary key,
    login    varchar(55),
    password text
);