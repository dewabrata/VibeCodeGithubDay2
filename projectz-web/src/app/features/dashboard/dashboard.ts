import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable, combineLatest } from 'rxjs';
import { DashboardService } from './dashboard.service';
import { AuthService } from '../../core/auth/auth.service';
import { DashboardMetrics, UserInfo, MetricCard } from '../../core/models/dashboard.model';
import { FIGMA_ASSETS } from '../../core/constants/figma-assets';

// Import components
import { Sidebar } from '../../shared/components/sidebar/sidebar';
import { Header } from '../../shared/components/header/header';
import { MetricCard as MetricCardComponent } from '../../shared/components/metric-card/metric-card';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, Sidebar, Header, MetricCardComponent],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})
export class Dashboard implements OnInit {
  currentUser: UserInfo | null = {
    id: 1,
    username: 'demo_user',
    namaLengkap: 'Demo User',
    email: 'demo@projectz.com',
    akses: 'admin'
  };
  dashboardData: DashboardMetrics = {};
  isLoading = false; // Set to false to show content immediately
  assets = FIGMA_ASSETS;

  // Metric cards data based on Figma design
  metricCards: MetricCard[] = [];

  constructor(
    private dashboardService: DashboardService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.loadDummyData();
  }

  loadDummyData() {
    // Load dummy data immediately without API calls
    this.dashboardData = {
      activeProducts: 20,
      discontinuedProducts: 3,
      documentsInReview: 1,
      pendingWork: 5,
      inProgressWork: 5,
      completedWork: 5
    };
    this.setupMetricCards();
  }

  private setupMetricCards() {
    this.metricCards = [
      // Row 1: Product metrics
      {
        title: 'Jumlah Produk Aktif',
        value: this.dashboardData.activeProducts || 20,
        icon: { type: 'package', color: '#039855' }
      },
      {
        title: 'Jumlah Produk Discontinue',
        value: this.dashboardData.discontinuedProducts || 3,
        icon: { type: 'package-off', color: '#dc6803' }
      },
      {
        title: 'Jumlah Dokumen dalam review',
        value: this.dashboardData.documentsInReview || 1,
        icon: { type: 'file-chart', color: '#7f56d9' }
      },
      // Row 2: Work items with trends
      {
        title: 'Jumlah Pekerjaan yang Belum Dikerjakan',
        value: this.dashboardData.pendingWork || 5,
        trend: { value: 20, direction: 'up' },
        trendLabel: 'Sebulan Terakhir',
        hasChart: true
      },
      {
        title: 'Jumlah Pekerjaan Sedang Dikerjakan',
        value: this.dashboardData.inProgressWork || 5,
        trend: { value: 20, direction: 'up' },
        trendLabel: 'Sebulan Terakhir',
        hasChart: true
      },
      {
        title: 'Jumlah Pekerjaan Selesai',
        value: this.dashboardData.completedWork || 5,
        trend: { value: 20, direction: 'up' },
        trendLabel: 'Sebulan Terakhir',
        hasChart: true
      }
    ];
  }
}
