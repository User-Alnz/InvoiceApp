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
      { withCredentials: true }) //send cookies in JWT in.
  }
}
