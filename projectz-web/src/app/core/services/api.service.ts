import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { ApiResponse, PaginatedResponse } from '../models/dashboard.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8080/api/v1';

  constructor(private http: HttpClient) {}

  // Generic CRUD operations
  getAll<T>(endpoint: string, params?: any): Observable<PaginatedResponse<T>> {
    let httpParams = new HttpParams();
    if (params) {
      Object.keys(params).forEach(key => {
        if (params[key] !== null && params[key] !== undefined) {
          httpParams = httpParams.append(key, params[key].toString());
        }
      });
    }

    return this.http.get<PaginatedResponse<T>>(`${this.baseUrl}/${endpoint}`, { params: httpParams });
  }

  getById<T>(endpoint: string, id: number): Observable<T> {
    return this.http.get<ApiResponse<T>>(`${this.baseUrl}/${endpoint}/${id}`)
      .pipe(map(response => response.data));
  }

  create<T>(endpoint: string, data: any): Observable<T> {
    return this.http.post<ApiResponse<T>>(`${this.baseUrl}/${endpoint}`, data)
      .pipe(map(response => response.data));
  }

  update<T>(endpoint: string, id: number, data: any): Observable<T> {
    return this.http.put<ApiResponse<T>>(`${this.baseUrl}/${endpoint}/${id}`, data)
      .pipe(map(response => response.data));
  }

  delete<T>(endpoint: string, id: number): Observable<T> {
    return this.http.delete<ApiResponse<T>>(`${this.baseUrl}/${endpoint}/${id}`)
      .pipe(map(response => response.data));
  }

  // Custom endpoints
  getMetrics(endpoint: string): Observable<any> {
    return this.http.get<ApiResponse<any>>(`${this.baseUrl}/${endpoint}`)
      .pipe(map(response => response.data));
  }
}
