create table posts
(
    id          bigint auto_increment,
    title       varchar(255) not null,
    description text         not null,
    content     text         not null,
    constraint PK_Posts primary key (id),
    constraint UQ_Posts_Title unique (title)
);