-- Table: myfms.expense

-- DROP TABLE myfms.expense;

CREATE TABLE myfms.expense
(
  expense_code serial NOT NULL,
  description character varying(200),
  amount double precision,
  expense_date date,
  user_name character varying(100),
  invoive_number character varying(100),
  file_name character varying(255),
  mim_type character varying(255),
  data_file bytea,
  CONSTRAINT expense_code_pf PRIMARY KEY (expense_code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE myfms.expense OWNER TO postgres;
GRANT ALL ON TABLE myfms.expense TO postgres;

