import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '@app/services/authService';
import { inject } from '@angular/core';
import { map } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => 
{
  const auth = inject(AuthService);
  const router = inject(Router);

  return auth.isLoggedIn$.pipe(
    map(isLoggedIn => {
    return isLoggedIn ? true : router.createUrlTree(['/']);
  })
  );
};
