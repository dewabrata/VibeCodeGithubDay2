import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class LoginComponent {
  credentials = {
    username: '',
    password: ''
  };

  loading = false;
  error = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  async onSubmit() {
    if (!this.credentials.username || !this.credentials.password) {
      this.error = 'Username dan password wajib diisi';
      return;
    }

    this.loading = true;
    this.error = '';

    try {
      await this.authService.loginAsync(this.credentials.username, this.credentials.password);
      this.router.navigate(['/dashboard']);
    } catch (error: any) {
      this.error = error.message || 'Login gagal. Silakan coba lagi.';
    } finally {
      this.loading = false;
    }
  }
}
