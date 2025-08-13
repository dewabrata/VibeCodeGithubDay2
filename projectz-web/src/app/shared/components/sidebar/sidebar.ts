import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FIGMA_ASSETS } from '../../../core/constants/figma-assets';

interface MenuItem {
  label: string;
  path: string;
  icon: string;
  active?: boolean;
}

@Component({
  selector: 'app-sidebar',
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.scss'
})
export class Sidebar implements OnInit {
  assets = FIGMA_ASSETS;

  menuItems: MenuItem[] = [
    { label: 'Dashboard', path: '/dashboard', icon: 'dashboard', active: true },
    { label: 'Master Produk', path: '/products', icon: 'package' },
    { label: 'Draft Dokumen', path: '/documents', icon: 'file' },
    { label: 'Master Dokumen', path: '/master-documents', icon: 'folder' },
    { label: 'Daftar Pekerjaan', path: '/tasks', icon: 'tasks' }
  ];

  ngOnInit() {
    // Load menu items based on user permissions
    this.loadMenuItems();
  }

  private loadMenuItems() {
    // In real implementation, this would fetch menu items from backend
    // based on user role and permissions (MapAksesMenu)
  }
}
