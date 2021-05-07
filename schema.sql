CREATE TABLE IF NOT EXISTS version (
 rev text NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
 id INTEGER,
 username text NOT NULL,
 password text NOT NULL,
 role text NOT NULL
);