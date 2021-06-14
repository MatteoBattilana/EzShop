CREATE TABLE IF NOT EXISTS version (
 rev text NOT NULL
);
DELETE FROM version;
INSERT INTO version (rev) VALUES ('1.0');

CREATE TABLE IF NOT EXISTS users (
 id INTEGER,
 username text NOT NULL,
 password text NOT NULL,
 role text NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
 product_type_id INTEGER,
 rfid text
);

CREATE TABLE IF NOT EXISTS transaction_product_rfid (
 id_sale INTEGER,
 id_product INTEGER,
 rfid text
);


CREATE TABLE IF NOT EXISTS order_operation (
 id INTEGER,
 date_op DATE,
 status text NOT NULL,
 quantity INTEGER,
 product_code text NOT NULL,
 order_status text NOT NULL,
 price_per_unit DOUBLE
);

CREATE TABLE IF NOT EXISTS customer (
 id INTEGER,
 name text NOT NULL,
 card text
);

CREATE TABLE IF NOT EXISTS customer_card (
 id text NOT NULL,
 points integer NOT NULL
);

CREATE TABLE IF NOT EXISTS balance_operation (
 id INTEGER,
 date_op DATE,
 money DOUBLE,
 type text NOT NULL,
 status text NOT NULL
);

CREATE TABLE IF NOT EXISTS product_type (
    id INTEGER,
    location text,
    quantity INTEGER NOT NULL,
    note text NOT NULL,
    description text NOT NULL,
    barcode text NOT NULL,
    price DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS sale_transaction (
    id INTEGER,
    discount DOUBLE NOT NULL,
    transaction_status text NOT NULL,
    date_op DATE,
    type text NOT NULL,
    status text NOT NULL
);


CREATE TABLE IF NOT EXISTS return_transaction (
 id INTEGER,
 date_op DATE,
 money DOUBLE,
 type text NOT NULL,
 status text NOT NULL,
 id_product INTEGER NOT NULL,
 amount INTEGER NOT NULL,
 id_sale INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction_product (
    id_sale INTEGER NOT NULL,
    id_product INTEGER NOT NULL,
    discount DOUBLE NOT NULL,
    price DOUBLE NOT NULL,
    quantity INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS balance (
    money DOUBLE NOT NULL
);