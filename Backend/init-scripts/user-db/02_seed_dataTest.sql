
/*
    Seed data for user-db

    -> fake data to test import
*/

-- Insert in user_account table
INSERT INTO user_account (firstname, lastname, email, hashed_password)
VALUES ('John', 'Doe', 'john.doe@example.com', 'hashedpassword123');

-- Insert in user_company table
INSERT INTO user_company (
    user_id,
    name,
    address,
    postal_code,
    country,
    tel,
    email,
    legal_status,
    share_capital,
    siren,
    siret,
    rcs,
    tva_number,
    website_url
)
VALUES (
    1,                              -- user_id references John Doe
    'fakeCorp',                     -- company name
    '123 Main St',                   -- address
    '75001',                         -- postal code
    'France',                        -- country
    '+33 6 00 00 00 00',             -- tel
    'contact@fakeCompany.fr',           -- email
    'SARL',                          -- legal status
    100000.00,                       -- share capital
    '123456789',                      -- SIREN
    '12345678901234',                 -- SIRET
    'RCS Paris B 123 456 789',       -- RCS
    'FR12345678901',                  -- TVA
    'https://www.fakeCompany.fr'        -- website
);

-- Insert in company_client table
INSERT INTO company_client (
    company_id,
    name,
    address,
    postal_code,
    country,
    tel,
    email
)
VALUES
    (1, 'Client A', '10 Rue de Client', '75002', 'France', '+33 1 11 22 33 44', 'clientA@example.com'),
    (1, 'Client B', '20 Avenue des Clients', '75003', 'France', '+33 1 55 66 77 88', 'clientB@example.com');
