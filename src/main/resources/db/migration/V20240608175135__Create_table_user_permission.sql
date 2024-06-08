CREATE TABLE user_permission
(
    id_user bigserial,
    id_permission bigserial,

    CONSTRAINT users_permission_pkey PRIMARY KEY (id_user, id_permission),
    CONSTRAINT fk_users FOREIGN KEY (id_user) REFERENCES users(id),
    CONSTRAINT fk_permission FOREIGN KEY (id_permission) REFERENCES permission(id)
);