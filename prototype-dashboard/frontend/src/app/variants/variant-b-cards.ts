// PROTOTYPE — wayfinder ticket 04. Variant B: herd cards + pen chips, click an animal to drill in.
import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Herd, Animal } from '../models';

@Component({
  selector: 'app-variant-b-cards',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="board">
      <h1>Farm Overview — click an alpaca</h1>
      <div class="herds">
        <section class="herd-card" *ngFor="let h of herds">
          <header>
            <h2>{{ h.name }}</h2>
            <span class="section">{{ h.farm_section }}</span>
          </header>
          <div class="pen" *ngFor="let p of h.pens">
            <div class="pen-label">{{ p.label }} <span class="cap">({{ p.animals.length }}/{{ p.capacity }})</span></div>
            <div class="chips">
              <button
                class="chip"
                [class.selected]="selected?.id === a.id"
                *ngFor="let a of p.animals"
                (click)="select(a)">
                {{ a.name }}
              </button>
            </div>
          </div>
        </section>
      </div>

      <aside class="drawer" *ngIf="selected">
        <button class="close" (click)="selected = null">&times;</button>
        <h3>{{ selected.name }}</h3>
        <dl>
          <dt>Species</dt><dd>{{ selected.species }}</dd>
          <dt>Born</dt><dd>{{ selected.birth_date }}</dd>
          <dt>Weight</dt><dd>{{ selected.weight_kg }} kg</dd>
          <dt>Fleece</dt><dd>{{ selected.fleece_color }}</dd>
          <ng-container *ngIf="selected.latestShearing as s">
            <dt>Last sheared</dt><dd>{{ s.sheared_on }} — {{ s.fleece_weight_kg }} kg</dd>
            <dt>Sale</dt>
            <dd *ngIf="s.price; else noSale">{{ s.buyer }} — €{{ s.price }} ({{ s.sale_date }})</dd>
            <ng-template #noSale><dd class="muted">not sold yet</dd></ng-template>
          </ng-container>
        </dl>
      </aside>
    </div>
  `,
  styles: [`
    .board { max-width: 1100px; margin: 40px auto; padding: 0 20px 80px; font-family: system-ui, sans-serif; position: relative; }
    h1 { color: #2F6FED; }
    .herds { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 16px; }
    .herd-card { background: #fff; border: 1px solid #e3e8f0; border-radius: 12px; padding: 16px; box-shadow: 0 2px 8px rgba(0,0,0,0.04); }
    .herd-card header { display: flex; justify-content: space-between; align-items: baseline; margin-bottom: 10px; }
    .herd-card h2 { margin: 0; font-size: 16px; color: #1a1a2e; }
    .section { font-size: 11px; color: #888; }
    .pen { margin-bottom: 10px; }
    .pen-label { font-size: 12px; font-weight: 600; color: #555; margin-bottom: 4px; }
    .cap { font-weight: 400; color: #aaa; }
    .chips { display: flex; flex-wrap: wrap; gap: 6px; }
    .chip { border: 1px solid #cfe0fb; background: #f2f7ff; color: #2F6FED; border-radius: 999px; padding: 4px 10px; font-size: 12px; cursor: pointer; }
    .chip:hover { background: #e3edff; }
    .chip.selected { background: #2F6FED; color: #fff; }
    .drawer { position: fixed; top: 0; right: 0; height: 100%; width: 300px; background: #fff; box-shadow: -4px 0 20px rgba(0,0,0,0.15); padding: 24px; box-sizing: border-box; }
    .drawer .close { position: absolute; top: 12px; right: 16px; border: none; background: none; font-size: 20px; cursor: pointer; color: #888; }
    .drawer h3 { margin-top: 0; color: #1a1a2e; }
    dl { display: grid; grid-template-columns: 90px 1fr; gap: 6px 8px; font-size: 13px; }
    dt { color: #888; }
    dd { margin: 0; }
    .muted { color: #aaa; font-style: italic; }
  `],
})
export class VariantBCardsComponent {
  @Input() herds: Herd[] = [];
  selected: Animal | null = null;

  select(a: Animal) {
    this.selected = this.selected?.id === a.id ? null : a;
  }
}
