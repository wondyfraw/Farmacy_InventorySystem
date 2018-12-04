-- Table: myfms.sales

-- DROP TABLE myfms.sales;

CREATE TABLE myfms.sales
(
  id_sales serial NOT NULL,
  id_dispensary integer NOT NULL,
  quantity integer NOT NULL,
  weight character varying(255),
  brand character varying(255),
  batch_number character varying(255),
  registration_date date,
  unit_price double precision NOT NULL,
  total_price double precision NOT NULL,
  dose integer,
  sales_person character varying(100),
  drug_name character varying(100),
  unit character varying(100),
  pack_unit character varying(255),
  CONSTRAINT id_sales_pk PRIMARY KEY (id_sales),
  CONSTRAINT sales_myfms_dispensary_fk FOREIGN KEY (id_dispensary)
      REFERENCES myfms.dispensary (id_dispensary) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE myfms.sales OWNER TO postgres;
GRANT ALL ON TABLE myfms.sales TO postgres;


/////////////////////////////
ALTER TABLE myfms.sales ADD CONSTRAINT sales_myfms_dispensary_fk FOREIGN KEY (id_dispensary) REFERENCES myfms.dispensary (id_dispensary) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
