CREATE TABLE users
(
    id bigserial,
    user_name character varying(100) NOT NULL,
    full_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    account_non_expired BOOLEAN,
    account_non_locked BOOLEAN,
    credentials_non_expired BOOLEAN,
    enabled BOOLEAN,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    UNIQUE (user_name)
);