create table categories
(
    id          bigint auto_increment,
    name        varchar(255) not null,
    description varchar(255),
    constraint PK_Categories primary key (id),
    constraint UQ_Categories_Name unique (name)
);