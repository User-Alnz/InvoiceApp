import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment.development';
import { Observable } from 'rxjs';
import { ApiResponse, Company } from './company.models';

@Injectable({
  providedIn: 'root'
})
export class CompanyService 
{

  private url = environment.urlCompanyDomain;

  constructor(private http : HttpClient){}


  getCompany() : Observable<ApiResponse<Company>>
  {
    return this.http.get<ApiResponse<Company>>(
      `${this.url}`,
      { withCredentials: true }); //send cookies in JWT in.
  }

  updateCompany(companyId:number, company : Company) : Observable<ApiResponse<Company>>
  {
    return this.http.put<ApiResponse<Company>>(
      `${this.url}/${companyId}`,
      company,
      { withCredentials: true });
  }

  createCompany(company : Company) : Observable<ApiResponse<Company>>
  {
    return this.http.post<ApiResponse<Company>>(
        `${this.url}`,
        company,
        { withCredentials: true });
  }
}
