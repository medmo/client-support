
drop table if exists clients;
create table if not exists clients
(
    id             serial primary key,
    code           text not null unique,
    name           text not null,
    email          text not null,
    phone_number   text not null,
    address        text not null,
);)
