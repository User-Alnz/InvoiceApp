import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, of } from 'rxjs';
import { ApiResponse, registerRequest, loginRequest} from './auth.models';
import { environment } from 'environments/environment.development';

@Injectable({
  providedIn: 'root' //make it singleton class available everywhere.
})
export class AuthService 
{

  private url = environment.urlAuthDomain;

  constructor(private http: HttpClient){}

  register(request: registerRequest ): Observable<ApiResponse>
  {
    return this.http.post<ApiResponse>(`${this.url}/register`, request).pipe(
      
      //pipe intercepts error if one is met.
      catchError((err) => {
      
      //build response based on API model.
      const errorResponse: ApiResponse = 
      {
        status: 'error',
        code: err.status || 500,
        data: err.error?.data || 'Unexpected error'
      };

      return of(errorResponse);

    }));
  }

  login(request : loginRequest): Observable<ApiResponse>
  {
    return this.http.post<ApiResponse>(`${this.url}/login`, request).pipe(
      

      catchError((err) => {
      
      const errorResponse: ApiResponse = 
      {
        status: 'error',
        code: err.status || 500,
        data: err.error?.data || 'Unexpected error'
      };

      return of(errorResponse);
    }));
  }


  
}
