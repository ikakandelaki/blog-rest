create table comments
(
    id      bigint auto_increment,
    name    varchar(255) not null,
    email   varchar(255) not null,
    body    text         not null,
    post_id bigint       not null,
    constraint PK_Comments primary key (id),
    foreign key (post_id) references posts (id)
);