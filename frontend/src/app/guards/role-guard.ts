import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export class RoleGuard {
  static hasRole(requiredRole: string): CanActivateFn {
    return () => {
      const authService = inject(AuthService);
      const router = inject(Router);

      if (authService.hasRole(requiredRole) || authService.hasRole(`ROLE_${requiredRole}`)) {
        return true;
      }

      router.navigate(['/dashboard']);
      return false;
    };
  }
}
