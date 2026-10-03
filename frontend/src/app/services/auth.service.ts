import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
import { environment } from '../../environments/environment';

export interface LoginRequest {
  email?: string;
  username?: string;
  password?: string;
  motDePasse?: string;
}

export interface RegisterRequest {
  nom?: string;
  prenom?: string;
  email?: string;
  telephone?: string;
  password?: string;
  motDePasse?: string;
  role?: string;
}

export interface AuthResponse {
  token: string;
}

export interface UserTokenPayload {
  sub: string;
  role?: string;
  roles?: string[];
  exp?: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<AuthResponse> {

    const payload = {
      ...credentials,
      password: credentials.password || credentials.motDePasse
    };
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, payload).pipe(
      tap(response => {
        if (response && response.token) {
          localStorage.setItem('token', response.token);
        }
      })
    );
  }

  register(data: RegisterRequest): Observable<AuthResponse> {

    const payload = {
      ...data,
      password: data.password || data.motDePasse
    };
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, payload);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;
    const user = this.getUserInfo();
    if (!user || !user.exp) return true;
    return user.exp * 1000 > Date.now();
  }

  getUserInfo(): UserTokenPayload | null {
    const token = this.getToken();
    if (!token) return null;
    try {
      return jwtDecode<UserTokenPayload>(token);
    } catch {
      return null;
    }
  }

  hasRole(requiredRole: string): boolean {
    const user = this.getUserInfo();
    if (!user) return false;

    if (user.role) {
      return user.role === requiredRole || user.role === `ROLE_${requiredRole}`;
    }
    if (user.roles) {
      return user.roles.includes(requiredRole) || user.roles.includes(`ROLE_${requiredRole}`);
    }
    return false;
  }

  logout(): void {
    localStorage.removeItem('token');
  }
}
