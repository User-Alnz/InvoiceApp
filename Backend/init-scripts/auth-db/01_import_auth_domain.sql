-- Enable pgcrypto extension for UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Tenants table (split concerns, extensible later)
CREATE TABLE tenants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid()
    -- more fields can be added to qualify user in DB and its group of users
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES tenants(id), -- user must belong to a tenant
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(254) NOT NULL UNIQUE, --unique email per user
    password VARCHAR(128) NOT NULL,
    role VARCHAR(50) NOT NULL
);