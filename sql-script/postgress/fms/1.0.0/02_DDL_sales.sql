CREATE TABLE myfms.sales
(
  id_sales serial NOT NULL,
  id_dispensary integer,
  id_user integer,
  dosage integer,
  quantity integer NOT NULL,
  weight character varying(255),
  brand character varying(255),
  batch_number character varying(255),
  registration_date date,
  unit_price double precision NOT NULL,
  total_price double precision NOT NULL,
  CONSTRAINT id_sales_pk PRIMARY KEY (id_sales),
  CONSTRAINT sales_id_dispensary_fk FOREIGN KEY (id_dispensary)
      REFERENCES myfms.dispensary(id_dispensary) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT sales_id_user_fk FOREIGN KEY ( id_user)
      REFERENCES myfms.users( id_user) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE myfms.sales
  OWNER TO postgres;
GRANT ALL ON TABLE myfms.sales TO postgres;