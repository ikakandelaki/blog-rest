create table users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    constraint Users_Roles_User_Id_Role_Id unique (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);