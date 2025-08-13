import { Injectable } from '@angular/core';
import { Observable, forkJoin, map, of } from 'rxjs';
import { ApiService } from '../../core/services/api.service';
import { DashboardMetrics, ChartData, SalesData, StaffPerformance } from '../../core/models/dashboard.model';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private apiService: ApiService) {}

  getDashboardMetrics(): Observable<DashboardMetrics> {
    return forkJoin({
      products: this.getProductMetrics(),
      workItems: this.getWorkItemMetrics(),
      documents: this.getDocumentMetrics(),
      activityData: this.getActivityChartData(),
      salesData: this.getSalesData(),
      staffPerformance: this.getStaffPerformance()
    }).pipe(
      map(data => ({
        activeProducts: data.products.active,
        discontinuedProducts: data.products.discontinued,
        documentsInReview: data.documents.inReview,
        pendingWork: data.workItems.pending,
        inProgressWork: data.workItems.inProgress,
        completedWork: data.workItems.completed,
        activityData: data.activityData,
        salesData: data.salesData,
        staffPerformance: data.staffPerformance
      }))
    );
  }

  private getProductMetrics(): Observable<any> {
    // For now, return mock data. In real implementation, this would call backend APIs
    return of({
      active: 20,
      discontinued: 3
    });
  }

  private getWorkItemMetrics(): Observable<any> {
    // Mock data for work items
    return of({
      pending: 5,
      inProgress: 5,
      completed: 5
    });
  }

  private getDocumentMetrics(): Observable<any> {
    // Mock data for documents
    return of({
      inReview: 1
    });
  }

  private getActivityChartData(): Observable<ChartData[]> {
    // Mock activity data for 7 days
    return of([
      { date: 'Mon', value: 30 },
      { date: 'Tue', value: 40 },
      { date: 'Wed', value: 35 },
      { date: 'Thu', value: 42 },
      { date: 'Fri', value: 38 },
      { date: 'Sat', value: 45 },
      { date: 'Sun', value: 40 }
    ]);
  }

  private getSalesData(): Observable<SalesData[]> {
    // Mock sales data
    return of([
      { product: 'SPO2 Simulator', quantity: 56, color: '#fef0c7' },
      { product: 'Digital Spirometer', quantity: 34, color: '#fec84b' },
      { product: 'Oxygen Concentrator', quantity: 10, color: '#444ce7' }
    ]);
  }

  private getStaffPerformance(): Observable<StaffPerformance[]> {
    // Mock staff performance data
    return of([
      { name: 'Olivia', tasks: 30 },
      { name: 'SPV Putri', tasks: 29 },
      { name: 'Budia', tasks: 25 }
    ]);
  }

  // Method to get real data from backend (for future implementation)
  getProductsFromApi(): Observable<any> {
    return forkJoin({
      activeProducts: this.apiService.getAll('products', { status: 'active' }),
      discontinuedProducts: this.apiService.getAll('products', { status: 'discontinued' })
    });
  }

  getAuditData(type: string): Observable<any> {
    return this.apiService.getAll(`audits/${type}`);
  }
}
