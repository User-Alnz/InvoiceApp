/*
    Company - Domain
*/

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE company ( 
    id SERIAL PRIMARY KEY,
    tenant_id UUID NOT NULL,    -- /!\ tenant_id from JWT in all request. ensure boundaries, data consitency and scope
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

-- Optimization 
CREATE INDEX idx_company_tenant ON company (tenant_id); -- use index to faster search by tenant_id
CREATE INDEX idx_company_tenant_name ON company (tenant_id, name); -- use index to faster search by tenant_id & compapny name

CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    company_id INT NOT NULL REFERENCES company(id) -- FK to the user_company
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    name VARCHAR(128) NOT NULL,
    address VARCHAR(255) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(64) NOT NULL,
    tel VARCHAR(20),
    email VARCHAR(254)
);