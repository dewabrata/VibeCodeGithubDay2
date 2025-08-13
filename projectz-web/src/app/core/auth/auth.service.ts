import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, tap, map, firstValueFrom, catchError, throwError } from 'rxjs';
import { LoginRequest, AuthResponse, UserInfo, ApiResponse } from '../models/dashboard.model';
import { HttpErrorResponse } from '../models/api.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/v1';
  private tokenKey = 'projectz_token';
  private currentUserSubject = new BehaviorSubject<UserInfo | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    // Check if token exists on startup
    this.loadCurrentUser();
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/auth/login`, credentials)
      .pipe(
        tap(response => {
          this.setToken(response.data.accessToken);
          this.currentUserSubject.next(response.data.userInfo);
        })
      );
  }

  async loginAsync(username: string, password: string): Promise<AuthResponse> {
    const credentials: LoginRequest = { username, password };

    try {
      return await firstValueFrom(
        this.login(credentials).pipe(
          catchError((error: HttpErrorResponse) => {
            const errorMessage = error.error?.message || 'Login failed';
            return throwError(() => new Error(errorMessage));
          })
        )
      );
    } catch (error) {
      throw error;
    }
  }

  getCurrentUser(): Observable<UserInfo> {
    return this.http.get<ApiResponse<UserInfo>>(`${this.baseUrl}/auth/me`)
      .pipe(
        map(response => response.data),
        tap(user => this.currentUserSubject.next(user))
      );
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.currentUserSubject.next(null);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  private loadCurrentUser(): void {
    if (this.isAuthenticated()) {
      this.getCurrentUser().subscribe({
        error: () => this.logout() // If token is invalid, logout
      });
    }
  }
}
