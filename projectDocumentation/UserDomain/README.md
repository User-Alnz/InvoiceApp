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
---
