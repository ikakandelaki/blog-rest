create table users
(
    id       bigint auto_increment,
    name     varchar(255),
    username varchar(255) not null,
    email    varchar(255) not null,
    password varchar(255) not null,
    constraint PK_Users primary key (id),
    constraint UQ_Users_Username unique (username),
    constraint UQ_Users_Email unique (email)
);