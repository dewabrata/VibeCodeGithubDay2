import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MetricCardIcon, TrendData, ChartData } from '../../../core/models/dashboard.model';
import { FIGMA_ASSETS } from '../../../core/constants/figma-assets';

@Component({
  selector: 'app-metric-card',
  imports: [CommonModule],
  templateUrl: './metric-card.html',
  styleUrl: './metric-card.scss'
})
export class MetricCard {
  @Input() title: string = '';
  @Input() value: number = 0;
  @Input() icon?: MetricCardIcon;
  @Input() trend?: TrendData;
  @Input() trendLabel?: string;
  @Input() hasChart?: boolean;
  @Input() chartData?: ChartData[];

  // Figma assets
  assets = FIGMA_ASSETS;

  getIconSrc(): string {
    if (!this.icon) return '';

    switch (this.icon.type) {
      case 'package':
        return this.assets.ICONS.PACKAGE;
      case 'package-off':
        return this.assets.ICONS.PACKAGE_OFF;
      case 'file-chart':
        return this.assets.ICONS.FILE_CHART;
      default:
        return '';
    }
  }

  getArrowIcon(): string {
    return this.assets.ICONS.ARROW_UP;
  }

  getMiniChart(): string {
    return this.assets.CHARTS.MINI_CHART;
  }
}
