// PROTOTYPE — wayfinder ticket 04 (Spring+Angular dashboard slice).
// Three variants of the farm dashboard, switchable via ?variant=, on this throwaway route.
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Herd } from './models';
import { FarmService } from './farm.service';
import { SwitcherComponent, VariantInfo } from './switcher/switcher';
import { VariantATableComponent } from './variants/variant-a-table';
import { VariantBCardsComponent } from './variants/variant-b-cards';
import { VariantCStatsComponent } from './variants/variant-c-stats';

const VARIANTS: VariantInfo[] = [
  { key: 'A', label: 'Ledger table' },
  { key: 'B', label: 'Herd cards + drill-down' },
  { key: 'C', label: 'KPI dashboard' },
];

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, SwitcherComponent, VariantATableComponent, VariantBCardsComponent, VariantCStatsComponent],
  template: `
    <ng-container *ngIf="herds.length">
      <app-variant-a-table *ngIf="variant === 'A'" [herds]="herds" />
      <app-variant-b-cards *ngIf="variant === 'B'" [herds]="herds" />
      <app-variant-c-stats *ngIf="variant === 'C'" [herds]="herds" />
    </ng-container>
    <p *ngIf="!herds.length && !error" class="loading">Loading farm data…</p>
    <p *ngIf="error" class="error">Couldn't reach the backend at localhost:8080 — is it running? ({{ error }})</p>

    <app-switcher [variants]="variants" [current]="variant" (currentChange)="setVariant($event)" />
  `,
  styles: [`
    .loading, .error { font-family: system-ui, sans-serif; text-align: center; margin-top: 100px; color: #888; }
    .error { color: #c0392b; }
  `],
})
export class App implements OnInit {
  variants = VARIANTS;
  variant = 'A';
  herds: Herd[] = [];
  error: string | null = null;

  constructor(private farm: FarmService) {}

  ngOnInit() {
    const params = new URLSearchParams(window.location.search);
    const v = params.get('variant');
    if (v && VARIANTS.some((x) => x.key === v)) this.variant = v;

    this.farm.overview().subscribe({
      next: (herds) => (this.herds = herds),
      error: (err) => (this.error = err.message ?? String(err)),
    });
  }

  setVariant(key: string) {
    this.variant = key;
    const url = new URL(window.location.href);
    url.searchParams.set('variant', key);
    history.replaceState({}, '', url.toString());
  }
}
