CREATE TABLE myfms.purchase
(
  id_purchase serial NOT NULL,
  id_store integer,
  quantity_in_box integer,
  registration_date date NOT NULL DEFAULT '2000-01-01'::date, 
  tatal_price integer NOT NULL,
  CONSTRAINT id_purchase_pk PRIMARY KEY (id_purchase),
  CONSTRAINT purchase_myfms_store_fk FOREIGN KEY (id_store)
      REFERENCES myfms.store (id_store) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE myfms.purchase
  OWNER TO postgres;
GRANT ALL ON TABLE myfms.purchase TO postgres;