
export interface ApiResponse<T>
{
  status: string; // "error"
  code: number;   // 200, 400, 409, etc
  data: T;   // ex => Company
}

export interface Company 
{
  id:number;
  name: string;
  address: string;
  postalCode: string;
  country: string;
  tel: string;
  email: string;
  legalStatus: string;
  shareCapital: number;
  siren: string;
  siret: string;
  rcs: string;
  tvaNumber: string;
  websiteUrl: string;
}