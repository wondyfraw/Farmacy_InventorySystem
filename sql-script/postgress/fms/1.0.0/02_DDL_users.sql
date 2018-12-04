-- Table: myfms.users

-- DROP TABLE myfms.users;

CREATE TABLE myfms.users
(
  id_user serial NOT NULL,
  full_name character varying(255),
  address character varying(255),
  email_address character varying(255),
  "password" character varying(255),
  registration_date date,
  updated_date date,
  user_type character varying(50),
  CONSTRAINT id_user_pk PRIMARY KEY (id_user)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE myfms.users OWNER TO postgres;
GRANT ALL ON TABLE myfms.users TO postgres;