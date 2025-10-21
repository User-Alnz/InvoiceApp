import { Injectable } from '@angular/core';
import { environment } from 'environments/environment.development';
import { Observable } from 'rxjs';
import { ApiResponse, Pagination } from './client.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClientService 
{
  
  private url = environment.urlCompanyDomain;

  constructor(private http : HttpClient){}

  getClient(companyId:number, currentPage:number): Observable<ApiResponse<Pagination>>
  {
    return this.http.get<ApiResponse<Pagination>>(
      `${this.url}/${companyId}/client?page=${currentPage}`,
      {withCredentials:true}
    );
  }

}
