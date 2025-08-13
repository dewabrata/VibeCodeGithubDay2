// Dashboard Models
export interface DashboardMetrics {
  activeProducts?: number;
  discontinuedProducts?: number;
  documentsInReview?: number;
  pendingWork?: number;
  inProgressWork?: number;
  completedWork?: number;
  activityData?: ChartData[];
  salesData?: SalesData[];
  staffPerformance?: StaffPerformance[];
}

export interface MetricCard {
  title: string;
  value: number;
  icon?: MetricCardIcon;
  trend?: TrendData;
  trendLabel?: string;
  hasChart?: boolean;
  chartData?: ChartData[];
}

export interface MetricCardIcon {
  type: 'package' | 'package-off' | 'file-chart';
  color: string;
}

export interface TrendData {
  value: number;
  direction: 'up' | 'down';
}

export interface ChartData {
  date: string;
  value: number;
}

export interface SalesData {
  product: string;
  quantity: number;
  color: string;
}

export interface StaffPerformance {
  name: string;
  tasks: number;
}

export interface UserInfo {
  id?: number;
  username?: string;
  namaLengkap?: string;
  email?: string;
  profileImage?: string;
  akses?: string;
}

// API Response Models
export interface ApiResponse<T> {
  data: T;
  message: string;
  timestamp?: string;
}

export interface PaginatedResponse<T> {
  data: T[];
  page: number;
  size: number;
  total: number;
  message: string;
  timestamp?: string;
}

export interface AuthResponse {
  data: {
    accessToken: string;
    refreshToken: string;
    userInfo: UserInfo;
  };
  message: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}
