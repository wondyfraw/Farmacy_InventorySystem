/*Quantity_box = quantity in box
 * Quantity_per_pack = total strip/bottle... inside box
 * Quantity_per_unit = quntity per strip/bottle ...
 * Quantit_per_pack_per_unit = Total number of tab, totla ml in bottle or ampule 
*/
CREATE TABLE myfms.dispensary
(
  id_dispensary serial NOT NULL,
  id_store integer,
  quantity_in_box integer DEFAULT 0,
  quantity_per_unit integer NOT NULL DEFAULT 0,
  quantity_per_pack_unit integer DEFAULT 0,
  quantity_per_pack integer DEFAULT 0,
  dispensary_date date NOT NULL DEFAULT '2000-01-01'::date,
  CONSTRAINT id_convenzione_pk PRIMARY KEY (id_dispensary),
  CONSTRAINT dispensary_myfms_store_fk FOREIGN KEY (id_store)
      REFERENCES myfms.store (id_store) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE myfms.dispensary
  OWNER TO postgres;
GRANT ALL ON TABLE myfms.dispensary TO postgres;