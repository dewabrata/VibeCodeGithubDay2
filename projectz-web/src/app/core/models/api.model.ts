// Base API Response Types
export interface ApiResponse<T = any> {
  success: boolean;
  message: string;
  data: T;
  errors?: string[];
  timestamp?: string;
}

// Error Response Types
export interface ApiError {
  code: string;
  message: string;
  field?: string;
  value?: any;
}

export interface ErrorResponse {
  success: false;
  message: string;
  errors: ApiError[];
  timestamp: string;
}

// HTTP Error Types
export interface HttpErrorResponse {
  error: ErrorResponse;
  status: number;
  statusText: string;
  url: string | null;
}
