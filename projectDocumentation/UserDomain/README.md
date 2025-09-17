# 📌 Client API Documentation

This document describes the **Endpoints to access data from userDomain** of the InvoiceApp backend or deployed as `user-service`.  


Base URL (default):  

http://localhost:8080/api/client


---

## 🔹 Client service endpoint - /api/client

### 1. **Create client**

**POST** `/api/client`

Create a new client associated with an existing company.

Pass "companyId": 1, to register client to related company

| Parameter | Type   | Description                       |
| :-------- | :-------| :-------------------------------- |
|`companyId`| `Long` | **Required**.  in body request |

#### Request Body
```json
{
  "companyId": 1, # /!\ -> pass the companyId in body request
  "name": "ACME Corp",
  "address": "123 Main St",
  "postalCode": "75001",
  "country": "France",
  "tel": "+33 6 12 34 56 78",
  "email": "contact@acme.com"
}
```

#### Request Response
```json
{
  "status": "success",
  "code": 200,
  "data": {
    "id": 10,
    "name": "ACME Corp"
  }
}
```

#### Error Response (example when company not found)
```json
{
  "status": "error",
  "code": 404,
  "message": "Company not found"
}
```

### 2. **Get client by id**


**GET** `/api/client/{id}`

Retrieve a client by its id.


#### Success Response
```json
{
    "status": "success",
    "code": 200,
    "data": {
        "id": 2,
        "name": "Client B",
        "address": "20 Avenue des Clients",
        "postalCode": "75003",
        "country": "France",
        "tel": "+33 1 55 66 77 88",
        "email": "clientB@example.com"
    }
}
```

#### Error Response (example when company not found)
```json
{
    "status": "error",
    "code": 404,
    "data": "Client not found with id: 1"
}
```



### 3. **Delete Client**

**DELETE** `/api/client/{id}`

Delete a client by its ID.


#### Success Response

```json
{
    "status": "success",
    "code": 200,
    "data": "Client deleted"
}
```

#### Error Response (example when client not found)
```json
{
  "status": "error",
  "code": 404,
  "message": "Client not found with id: 10"
}
```

### 4. **Delete Company**

**DELETE** `/api/company/{id}`

Delete a company by its ID.

This also delete all clients linked by foreign key to the company id. They'll get deleted on cascade.

#### Success Response
```json
{
    "status": "success",
    "code": 200,
    "data": "Company deleted"
}
```

#### Error Response (example when company not found or already deleted)
```json
{
    "status": "error",
    "code": 404,
    "data": "Company not found with id: 10"
}
```

### 5. **update Company**

**PUT** `/api/company/{id}`

update company info. 

| Parameter      | Type     | Description                               |
| :------------- | :------- | :---------------------------------------- |
| `name`         | `String` | **Required**. Company name                |
| `address`      | `String` | **Required**. Company address             |
| `postalCode`   | `String` | **Required**. Postal code                 |
| `country`      | `String` | **Required**. Country                     |
| `tel`          | `String` | Phone number                              |
| `email`        | `String` | Company email                             |
| `legalStatus`  | `String` | **Required**. Legal form (e.g. SARL, SAS) |
| `shareCapital` | `Double` | **Required**. Share capital               |
| `siren`        | `String` | **Required**. SIREN number (9 digits)     |
| `siret`        | `String` | **Required**. SIRET number (14 digits)    |
| `rcs`          | `String` | **Required**. RCS registration number     |
| `tvaNumber`    | `String` | **Required**. VAT number                  |
| `websiteUrl`   | `String` | Company website URL (optional)            |

#### Request Body
```json
{
  "name": "TechCorp",
  "address": "10 Rue de Paris",
  "postalCode": "75001",
  "country": "France",
  "tel": "+33 1 23 45 67 89",
  "email": "contact@techcorp.com",
  "legalStatus": "SAS",
  "shareCapital": 100000,
  "siren": "123456789",
  "siret": "12345678900010",
  "rcs": "RCS Paris 123 456 789",
  "tvaNumber": "FR123456789",
  "websiteUrl": "https://techcorp.com"
}
```

#### Success Response
```json
{
    "status": "success",
    "code": 200,
    "data": {
        "id": 17,
        "name": "FakeCorpo",
        "address": "123 Main Street",
        "postalCode": "75051",
        "country": "France",
        "tel": "+33123456789",
        "email": "contact@acme.com",
        "legalStatus": "SARL",
        "shareCapital": 50000.0,
        "siren": "123456789",
        "siret": "12345678900012",
        "rcs": "RCS Paris B 123 456 789",
        "tvaNumber": "FR12345678901",
        "websiteUrl": "https://www.acme.com"
    }
}
```

#### Missing Required Parameter
```json
{
    "country": "Country is required"
}
```

#### Error Response (example when company not found)
```json
{
    "status": "error",
    "code": 404,
    "data": "Company id not found"
}
```