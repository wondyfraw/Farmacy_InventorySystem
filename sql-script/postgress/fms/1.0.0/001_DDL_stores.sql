-- Table: myfms.store

-- DROP TABLE myfms.store;

CREATE TABLE myfms.store
(
  id_store serial NOT NULL,
  drugname character varying(255),
  quantity_in_box integer,
  quantity_per_box_per_unit integer,
  quantity_per_unit_per_tab integer,
  pack_type character varying(50),
  pack_unit character varying(50),
  unit character varying(100),
  brand character varying(255),
  batch_number character varying(100),
  weight character varying(255),
  registration_date date NOT NULL DEFAULT '2000-01-01'::date,
  manufactury_date date NOT NULL DEFAULT '2000-01-01'::date,
  expire_date date NOT NULL DEFAULT '2000-01-01'::date,
  unit_price integer NOT NULL,
  sales_price integer NOT NULL,
  tatal_price integer NOT NULL,
  modify_date date,
  deleted_status character varying(100),
  CONSTRAINT id_store_pk PRIMARY KEY (id_store)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE myfms.store OWNER TO postgres;
GRANT ALL ON TABLE myfms.store TO postgres;
