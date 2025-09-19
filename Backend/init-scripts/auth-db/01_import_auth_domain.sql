-- if need to re-import schema. not try creating new type
DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'authorization_enum') THEN
        CREATE TYPE authorization_enum AS ENUM ('USER', 'ADMIN');
    END IF;
END$$;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role authorization_enum NOT NULL
);