create table locations
(
    id        bigserial primary key,
    name      varchar(100),
    user_id   bigint references users (id),
    latitude  decimal,
    longitude decimal
);