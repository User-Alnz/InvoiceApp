import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './authService';
import { ApiResponse } from './auth.models';
import { environment } from '../../environments/environment.development';

describe('Auth', () => 
{

  let service: AuthService;
  let http : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
       providers: [  
        provideHttpClient(),
        provideHttpClientTesting(),
        AuthService
      ]
    });

    service = TestBed.inject(AuthService);
    http = TestBed.inject(HttpTestingController);  // used for expect/flush requests
  });


  afterEach(() => {
    http.verify(); // ensure no unexpected requests remain
  });

  it('service should be created', () => {
    expect(service).toBeTruthy();
  });

  /* 
    TEST - 1 => login()
    Verify if private isLoggedInSubject = new BehaviorSubject<boolean>(false); got well updated from login
  */
  it('login() should POST and set isLoggedIn$', (done) => 
  {
    const mockResponse: ApiResponse = {
      status: 'success',
      code: 200,
      data: 'Login successful'
    };

    service.login({ email: 'test@gmail.com', password: 'Password123@' }).subscribe(res => 
    {
      expect(res.status).toBe('success');
      expect(res.code).toBe(200);
      
      // check observable state
      service.isLoggedIn$.subscribe(v => {
        expect(v).toBeTrue();
        done();
      });

    });

    const req = http.expectOne(`${environment.urlAuthDomain}/login`);
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);

  });

  /* 
    TEST - 2 => login()

    verify correct error response 
  */
  it('login() should handle 401 error', (done) => {
    service.login({ email: 'x', password: 'y' }).subscribe(res => {
      expect(res.status).toBe('error');
      expect(res.code).toBe(401);
      done();
    });

    const req = http.expectOne(`${environment.urlAuthDomain}/login`);
    req.flush({ data: 'Invalid credentials' }, { status: 401, statusText: 'Unauthorized' });
  });

  });
