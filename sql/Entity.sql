-- CREATE DATABASE naina_entity ;

CREATE TABLE people (
    id SERIAL PRIMARY KEY ,
    name VARCHAR(255) ,
    ok BOOLEAN ,
    dtn TIMESTAMP 
);
comment on column people.name is 'label';


CREATE TABLE car (
    id SERIAL PRIMARY KEY ,
    imma VARCHAR(255) ,
    color VARCHAR(255) ,
    kilometrage NUMERIC(10)
);
comment on column car.imma is 'label';


comment on column people.name is 'fd';
comment on column people.ok is 'po';
comment on column car.color is 'fdsdfs';
comment on column car.kilometrage is 'dsd';
comment on column car.imma is 'test';

-- Look the comment in the columns of  table 
SELECT a.attname AS column_name,
       pd.description AS column_description
FROM pg_attribute a
JOIN pg_class c ON a.attrelid = c.oid
JOIN pg_description pd ON pd.objoid = c.oid AND pd.objsubid = a.attnum
WHERE c.relname = 'your_table_name';
-- Look the comment in the columns of  table 
SELECT d.description  
FROM pg_catalog.pg_description d  
JOIN pg_catalog.pg_class c ON d.objoid = c.oid  
JOIN pg_catalog.pg_attribute a ON c.oid = a.attrelid  
WHERE c.relname = "your_table_name"
AND a.attname = "your_column_name"


CREATE TABLE people_car(
    id SERIAL PRIMARY KEY ,
    id_people INT REFERENCES people (id) ON DELETE CASCADE,
    id_car INT REFERENCES car (id) ON DELETE CASCADE ,
    date TIMESTAMP NOT NULL
);

CREATE TABLE test(
    id SERIAL PRIMARY KEY,
    name VARCHAR 
);