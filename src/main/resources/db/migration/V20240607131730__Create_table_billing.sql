CREATE TABLE billing
(
    id bigserial,
    description text NOT NULL,
    situation character varying(50) NOT NULL,
    expiration_date date NOT NULL,
    payment_date date NOT NULL,
    payment_value numeric(12,2) NOT NULL,
    CONSTRAINT billing_pkey PRIMARY KEY (id)
);