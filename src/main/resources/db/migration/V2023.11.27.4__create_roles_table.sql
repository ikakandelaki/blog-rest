create table roles
(
    id   bigint auto_increment,
    name varchar(255) not null,
    constraint PK_Roles primary key (id),
    constraint UQ_Roles_Name unique (name)
);