CREATE TABLE myfms.farmacist
(
  id_farmacist serial NOT NULL,
  specilization character varying(255),
  farmacist_name character varying(255),
  address character varying(255),
  farmacist_fee double precision NOT NULL,
  phone_number character varying(12),
  email_address character varying(255),
  "password" character varying(255),
  creation_date date NOT NULL,
  updated_date date,
  CONSTRAINT id_farmacist_pk PRIMARY KEY (id_farmacist)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE myfms.farmacist OWNER TO postgres;
GRANT ALL ON TABLE myfms.farmacist TO postgres;