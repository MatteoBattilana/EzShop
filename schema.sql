CREATE TABLE IF NOT EXISTS version (
 rev text NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
 id INTEGER,
 username text NOT NULL,
 password text NOT NULL,
 role text NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
 id INTEGER,
 quantity INTEGER,
 balance_id INTEGER,
 product_code text NOT NULL,
 status text NOT NULL,
 price_per_unit DOUBLE
);

CREATE TABLE IF NOT EXISTS balance_operations (
 id INTEGER,
 date_op DATE,
 money DOUBLE,
 type text NOT NULL,
 status text NOT NULL
);

CREATE TABLE IF NOT EXISTS account_book (
    balance DOUBLE
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