CREATE TABLE myfms.invoice
(
  invoice_num serial NOT NULL,
  id_sales integer,
  cunstomer_name character varying(100),
  dates timestamp without time zone,
  CONSTRAINT invoice_num_pk PRIMARY KEY(invoice_num),
  CONSTRAINT invoice_id_sales_fk FOREIGN KEY(id_sales)
      REFERENCES myfms.sales(id_sales) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH(
   OIDS=FALSE
);
ALTER TABLE myfms.invoice 
  OWNER TO postgres;
  GRANT ALL ON TABLE myfms.invoice TO postgres;