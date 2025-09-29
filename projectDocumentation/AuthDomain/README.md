# Auth Services

The **Auth domain** has the main role of **issuing JWTs and ensuring user registration** in the database.  
Other domains use the JWT (via `Authorization header`) to authorize requests.

Use roles as well as tenantId on **JWT filterchain over HTTP request headers in other domain**.

JWT contains both **roles** and **tenantId**:

```json
{
  "roles": ["ADMIN"],
  "tenantId": "889180e9-5801-47cb-ac6a-91178d7c5add"
}
```
- use tenantId to define datascope of user group

- use roles to protect CRUD operations in other domains

-  now there is only "USER | ADMIN" roles.


### Register a user


**POST** `/auth/register`

To create user follow requirements below.

| Parameter      | Type     | Description                               |
| :------------- | :------- | :---------------------------------------- |
| `firstName`    | `String` | **Required**.                             |
| `lastName`    | `String` | **Required**.                              |
| `password`     | `String` | **Required**.  min size 8                 |


#### Request Body
```json
{
    "firstName" : "John",
    "lastName" : "Doe",
    "email" : "test@gmail.com", 
    "password" : "testinvoice",
}
```

#### Success Response
```json
{
    "status": "success",
    "code": 200,
    "data": "User created"
}
```

#### Error Response
```json
{
    "password": "password is minimum 8 and max 128 characters length"
}
```

### /Register diagram flow : 

```mermaid
sequenceDiagram
    participant Client
    participant AuthController
    participant UserInfoService
    participant UserInfo
    participant PasswordEncoder
    participant UserRepository as DB

    Client->>AuthController: POST /register (email, password, name, ...)
    AuthController->>UserInfoService: addUser(request)

    UserInfoService->>UserInfo: new UserInfo(request)
    UserInfo-->>PasswordEncoder: setPassword(encoder.encode(request.getPassword()));

    PasswordEncoder-->>UserInfo: hashedPassword
    UserInfo->>UserRepository: repository.save(User(email, hashedPassword, name, ...)

    UserRepository->>UserInfoService: "User Created"
    UserInfoService ->>AuthController: response

    AuthController->>Client: 200  ResponsePattern<response>
```

### Login as already registerd user

**POST** `/auth/login`

To issue JWT token. You need to send email and password.
If any of those does not match with database, backend retuns 401 invalid crendetials.


#### Request Body
```json
{
    "email": "test@gmail.com",
    "password" :"testinvoice" 
}         
```
#### Error Response
```json
{
    "status": "error",
    "code": 401,
    "data": "Invalid credentials"
}
```
#### Success Response
```text
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJBRE1JTiJdLCJ0ZW5hbnRJZCI6Ijg4OTE4MGU5LTU4MDEtNDdjYi1hYzZhLTkxMTc4ZDdjNWFkZCIsInN1YiI6InRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzU5MTQ5NDI4LCJleHAiOjE3NTkxNTEyMjh9.BFVCipkwRHXyhgS1T3pkJEc5vSp74KRej68-IL9u6og
```

### /Login diagram flow :

```mermaid
sequenceDiagram
    participant Client
    participant AuthController
    participant JWTService
    participant AuthenticationManager
    participant DaoAuthenticationProvider
    participant UserInfoService as UserDetailsService
    participant UserInfoDetails as UserDetails
    participant UserRepository as DB

    Client->>AuthController: POST /login (email, password)
    AuthController->>AuthenticationManager: new UsernamePasswordToken
    AuthenticationManager->>DaoAuthenticationProvider: authManager.authenticate(...)
    DaoAuthenticationProvider->>UserInfoService: loadUserByUsername(email)
    UserInfoService->>UserRepository: findByEmail(email)
    UserRepository-->>UserInfoDetails: User
    UserInfoDetails-->>UserInfoService:custom(UserDetails)+tenantID
    UserInfoService-->>DaoAuthenticationProvider: UserDetails

    alt ✅ Credentials valid
        DaoAuthenticationProvider-->>AuthenticationManager: Authentication object
        AuthenticationManager-->>AuthController: Success
        AuthController->>JWTService:generateToken()
        JWTService ->> AuthController: Token
        AuthController->>Client: JWT
    else ❌ Invalid credentials
        DaoAuthenticationProvider-->>AuthenticationManager: Bad credentials exception
        AuthenticationManager-->>AuthController: Failure
        AuthController-->>Client: 401 Invalid Credentials
    end
```