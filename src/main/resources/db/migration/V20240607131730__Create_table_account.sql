CREATE TABLE account
(
    id bigserial,
    description text NOT NULL,
    account_type character varying(20) NOT NULL,
    status character varying(50) NOT NULL,
    expiration_date date NOT NULL,
    payment_date date NOT NULL,
    payment_value numeric(12,2) NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (id)
);