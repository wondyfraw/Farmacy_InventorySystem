CREATE TABLE myfms.expense
  (expense_code serial NOT null,
   description character varying(200),
   amount double precision,
   user_name character varying(100),
   expense_date Date,
   CONSTRAINT expense_code_pf PRIMARY KEY(expense_code)
  )
  WITH(
   OIDS=FALSE
  );
  ALTER TABLE myfms.expense
   OWNER TO postgres;
   GRANT ALL ON TABLE myfms.expense TO postgres;