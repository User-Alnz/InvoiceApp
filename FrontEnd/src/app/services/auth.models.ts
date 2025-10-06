export interface registerRequest 
{
    firstName: string; 
    lastName: string; 
    email: string; 
    password: string;
}

export interface loginRequest
{
    email: string;
    password: string;
}

//This is generic reponse of api invalid or valid
export interface ApiResponse 
{
  status: string; // "error"
  code: number;   // 200, 400, 409, etc
  data: string;   // "User created" | "Email already in used" | "invalid credentials" etc...
}
