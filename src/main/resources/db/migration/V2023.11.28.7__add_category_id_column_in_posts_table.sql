alter table posts
    add category_id bigint,
    add constraint foreign key(category_id) references categories(id);