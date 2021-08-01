CREATE TABLE product (id serial primary key, description varchar, quantity integer);
CREATE TABLE product_event (id serial primary key, type varchar, source jsonb, occurred_on timestamptz);
