/*
    User - Domain
*/

CREATE TABLE user_account (
    id SERIAL PRIMARY KEY,
    firstname VARCHAR(64) NOT NULL,
    lastname VARCHAR(64) NOT NULL,
    email VARCHAR(254) NOT NULL UNIQUE,
    hashed_password VARCHAR(255) NOT NULL
);

CREATE TABLE user_company ( 
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES user_account(id)  -- FK to user_account
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    name VARCHAR(128) NOT NULL,
    address VARCHAR(255) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(64) NOT NULL,
    tel VARCHAR(20),
    email VARCHAR(254),
    legal_status VARCHAR(64) NOT NULL,            -- e.g., SARL, SAS, SA...
    share_capital DECIMAL(15,2) NOT NULL,         -- numeric with decimals, allows big values
    siren CHAR(9) NOT NULL,                       -- French SIREN = 9 digits
    siret CHAR(14) NOT NULL,                      -- French SIRET = 14 digits
    rcs VARCHAR(64) NOT NULL,                     -- registry of commerce info
    tva_number VARCHAR(32) NOT NULL,              -- EU VAT number format varies
    website_url VARCHAR(255)
);

CREATE TABLE company_client (
    id SERIAL PRIMARY KEY,
    company_id INT NOT NULL REFERENCES user_company(id) -- FK to the user_company
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    name VARCHAR(128) NOT NULL,
    address VARCHAR(255) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(64) NOT NULL,
    tel VARCHAR(20),
    email VARCHAR(254)
);