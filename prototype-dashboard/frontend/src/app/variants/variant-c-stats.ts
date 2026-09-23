// PROTOTYPE — wayfinder ticket 04. Variant C: KPI-tile dashboard, aggregate-first.
import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Herd } from '../models';

interface HerdStat {
  name: string;
  animalCount: number;
  revenue: number;
}

@Component({
  selector: 'app-variant-c-stats',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="dash">
      <h1>Farm Dashboard</h1>

      <div class="tiles">
        <div class="tile">
          <div class="value">{{ totalAnimals() }}</div>
          <div class="tile-label">Animals</div>
        </div>
        <div class="tile">
          <div class="value">{{ herds.length }}</div>
          <div class="tile-label">Herds</div>
        </div>
        <div class="tile">
          <div class="value">€{{ totalRevenue() | number:'1.0-0' }}</div>
          <div class="tile-label">Fleece revenue</div>
        </div>
        <div class="tile">
          <div class="value">{{ avgFleeceWeight() | number:'1.1-1' }} kg</div>
          <div class="tile-label">Avg. last fleece</div>
        </div>
      </div>

      <h2>By herd</h2>
      <div class="bars">
        <div class="bar-row" *ngFor="let h of herdStats()">
          <span class="bar-label">{{ h.name }}</span>
          <div class="bar-track">
            <div class="bar-fill" [style.width.%]="(h.animalCount / maxAnimals()) * 100"></div>
          </div>
          <span class="bar-value">{{ h.animalCount }} animals · €{{ h.revenue | number:'1.0-0' }}</span>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .dash { max-width: 900px; margin: 40px auto; padding: 0 20px 80px; font-family: system-ui, sans-serif; }
    h1 { color: #2F6FED; margin-bottom: 24px; }
    h2 { font-size: 14px; text-transform: uppercase; letter-spacing: 0.05em; color: #888; margin-top: 32px; }
    .tiles { display: grid; grid-template-columns: repeat(auto-fit, minmax(160px, 1fr)); gap: 14px; }
    .tile { background: linear-gradient(160deg, #2F6FED, #1a1a2e); color: #fff; border-radius: 14px; padding: 20px; }
    .tile .value { font-size: 30px; font-weight: 700; }
    .tile-label { font-size: 12px; opacity: 0.8; margin-top: 4px; }
    .bars { display: flex; flex-direction: column; gap: 10px; margin-top: 12px; }
    .bar-row { display: grid; grid-template-columns: 140px 1fr auto; align-items: center; gap: 10px; font-size: 13px; }
    .bar-label { font-weight: 600; color: #1a1a2e; }
    .bar-track { background: #eef3fc; border-radius: 6px; height: 10px; overflow: hidden; }
    .bar-fill { background: #2F6FED; height: 100%; border-radius: 6px; }
    .bar-value { color: #888; white-space: nowrap; }
  `],
})
export class VariantCStatsComponent {
  @Input() herds: Herd[] = [];

  totalAnimals(): number {
    return this.herds.reduce((sum, h) => sum + h.pens.reduce((s, p) => s + p.animals.length, 0), 0);
  }

  totalRevenue(): number {
    let total = 0;
    for (const h of this.herds) for (const p of h.pens) for (const a of p.animals) {
      total += a.latestShearing?.price ?? 0;
    }
    return total;
  }

  avgFleeceWeight(): number {
    let sum = 0, n = 0;
    for (const h of this.herds) for (const p of h.pens) for (const a of p.animals) {
      if (a.latestShearing) { sum += a.latestShearing.fleece_weight_kg; n++; }
    }
    return n ? sum / n : 0;
  }

  herdStats(): HerdStat[] {
    return this.herds.map((h) => {
      let animalCount = 0, revenue = 0;
      for (const p of h.pens) for (const a of p.animals) {
        animalCount++;
        revenue += a.latestShearing?.price ?? 0;
      }
      return { name: h.name, animalCount, revenue };
    });
  }

  maxAnimals(): number {
    return Math.max(1, ...this.herdStats().map((h) => h.animalCount));
  }
}
