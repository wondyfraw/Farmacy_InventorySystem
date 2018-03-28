CREATE TABLE myfms.users_log
(
  id_users_log serial NOT NULL,
  id_user integer,
  usename character varying(255),
  user_ip character varying(255),
  login_time date,
  logout_time date,
  CONSTRAINT id_users_log_pk PRIMARY KEY (id_users_log),
  CONSTRAINT users_id_user_fk FOREIGN KEY (id_user)
      REFERENCES myfms.users(id_user) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE myfms.users_log
  OWNER TO postgres;
GRANT ALL ON TABLE myfms.users_log TO postgres;