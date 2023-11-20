create table posts
(
    id          bigint primary key auto_increment,
    title       varchar(255) not null,
    description text         not null,
    content     text         not null,
    unique UQ_Posts_Title (title)
);