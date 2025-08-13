import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserInfo } from '../../../core/models/dashboard.model';
import { FIGMA_ASSETS } from '../../../core/constants/figma-assets';
import { AuthService } from '../../../core/auth/auth.service';

@Component({
  selector: 'app-header',
  imports: [CommonModule],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {
  @Input() currentUser: UserInfo | null = null;

  assets = FIGMA_ASSETS;

  constructor(private authService: AuthService) {}

  logout() {
    this.authService.logout();
    // Navigate to login page would be handled by routing
  }

  getUserAvatar(): string {
    return this.currentUser?.profileImage || this.assets.USER_AVATAR;
  }
}
