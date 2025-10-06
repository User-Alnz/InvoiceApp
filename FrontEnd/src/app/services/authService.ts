import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, catchError, Observable, of, tap } from 'rxjs';
import { ApiResponse, registerRequest, loginRequest} from './auth.models';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root' //make it singleton class available everywhere.
})
export class AuthService 
{

  private url = environment.urlAuthDomain;

  private isLoggedInSubject = new BehaviorSubject<boolean>(false); //Rxjs subject/state can change in pipe()
  isLoggedIn$ = this.isLoggedInSubject.asObservable(); //observable read-only.

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
    return this.http.post<ApiResponse>(`${this.url}/login`, request,
      { withCredentials: true } // imp allows HttpOnly Set-Cookie
    ).pipe(
      
      tap((response : ApiResponse)=>
      {
        if(response.status === 'success' && response.code === 200)
        this.isLoggedInSubject.next(true);

      }),
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
