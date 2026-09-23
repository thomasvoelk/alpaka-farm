// PROTOTYPE — wayfinder ticket 04. Variant A: dense ledger table, flat rows.
import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Herd, Animal } from '../models';

interface Row {
  animal: Animal;
  herdName: string;
  penLabel: string;
}

@Component({
  selector: 'app-variant-a-table',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="ledger">
      <h1>The Data Barn — Ledger View</h1>
      <table>
        <thead>
          <tr>
            <th>Name</th><th>Species</th><th>Herd</th><th>Pen</th>
            <th>Weight (kg)</th><th>Fleece</th><th>Last sheared</th><th>Last sale</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let r of rows()">
            <td class="name">{{ r.animal.name }}</td>
            <td>{{ r.animal.species }}</td>
            <td>{{ r.herdName }}</td>
            <td>{{ r.penLabel }}</td>
            <td>{{ r.animal.weight_kg }}</td>
            <td>{{ r.animal.fleece_color }}</td>
            <td>{{ r.animal.latestShearing?.sheared_on ?? '—' }}</td>
            <td [class.no-sale]="!r.animal.latestShearing?.price">
              {{ r.animal.latestShearing?.price ? '€' + r.animal.latestShearing?.price : 'unsold' }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `,
  styles: [`
    .ledger { max-width: 1000px; margin: 40px auto; padding: 0 20px 80px; font-family: 'Courier New', monospace; }
    h1 { font-family: system-ui, sans-serif; color: #2F6FED; }
    table { width: 100%; border-collapse: collapse; font-size: 13px; }
    th, td { text-align: left; padding: 6px 10px; border-bottom: 1px solid #e3e8f0; }
    th { background: #eef3fc; color: #2F6FED; text-transform: uppercase; font-size: 11px; letter-spacing: 0.05em; }
    tr:hover td { background: #f7faff; }
    .name { font-weight: 700; }
    .no-sale { color: #999; font-style: italic; }
  `],
})
export class VariantATableComponent {
  @Input() herds: Herd[] = [];

  rows(): Row[] {
    const out: Row[] = [];
    for (const h of this.herds) {
      for (const p of h.pens) {
        for (const a of p.animals) {
          out.push({ animal: a, herdName: h.name, penLabel: p.label });
        }
      }
    }
    return out;
  }
}
