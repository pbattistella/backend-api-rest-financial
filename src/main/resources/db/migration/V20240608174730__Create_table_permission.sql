CREATE TABLE permission
(
    id bigserial,
    description character varying(50) NOT NULL,
    CONSTRAINT permission_pkey PRIMARY KEY (id)
);