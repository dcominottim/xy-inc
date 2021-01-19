drop table if exists point_of_interest;

create table point_of_interest
(
    id uuid not null
        constraint point_of_interest_pkey
            primary key,
    x integer not null,
    y integer not null,
    name varchar(255)
        constraint point_of_interest_name_akey
            unique
);


