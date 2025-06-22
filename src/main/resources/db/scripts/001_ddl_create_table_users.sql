create table users
(
    id       bigserial primary key,
    login    varchar(55) unique not null,
    password varchar(200) not null
);