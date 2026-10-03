import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login';
import { RegisterComponent } from './components/register/register';
import { DashboardComponent } from './components/dashboard/dashboard';
import { ExamenFormComponent } from './components/examen-form/examen-form';
import { authGuard } from './guards/auth-guard';
import { RoleGuard } from './guards/role-guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [authGuard]
  },
  {
    path: 'examens/nouveau',
    component: ExamenFormComponent,
    canActivate: [authGuard, RoleGuard.hasRole('ADMIN')]
  },
  { path: '**', redirectTo: 'login' }
];
