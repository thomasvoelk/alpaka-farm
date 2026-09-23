// PROTOTYPE — wayfinder ticket 04. Throwaway variant switcher, not for production.
import { Component, EventEmitter, HostListener, Input, Output } from '@angular/core';

export interface VariantInfo {
  key: string;
  label: string;
}

@Component({
  selector: 'app-switcher',
  standalone: true,
  template: `
    <div class="switcher">
      <button (click)="cycle(-1)" aria-label="Previous variant">&larr;</button>
      <span class="label">{{ currentLabel() }}</span>
      <button (click)="cycle(1)" aria-label="Next variant">&rarr;</button>
    </div>
  `,
  styles: [`
    .switcher {
      position: fixed;
      bottom: 20px;
      left: 50%;
      transform: translateX(-50%);
      display: flex;
      align-items: center;
      gap: 12px;
      background: #1a1a2e;
      color: #fff;
      padding: 10px 18px;
      border-radius: 999px;
      box-shadow: 0 6px 20px rgba(0,0,0,0.35);
      font-family: system-ui, sans-serif;
      z-index: 1000;
    }
    .switcher button {
      background: rgba(255,255,255,0.12);
      border: none;
      color: #fff;
      width: 28px;
      height: 28px;
      border-radius: 50%;
      cursor: pointer;
      font-size: 16px;
      line-height: 1;
    }
    .switcher button:hover { background: rgba(255,255,255,0.25); }
    .label { min-width: 160px; text-align: center; font-size: 13px; font-weight: 600; letter-spacing: 0.02em; }
  `],
})
export class SwitcherComponent {
  @Input() variants: VariantInfo[] = [];
  @Input() current = '';
  @Output() currentChange = new EventEmitter<string>();

  currentLabel(): string {
    const v = this.variants.find((x) => x.key === this.current);
    return v ? `${v.key} — ${v.label}` : this.current;
  }

  cycle(dir: 1 | -1) {
    const idx = this.variants.findIndex((v) => v.key === this.current);
    const next = (idx + dir + this.variants.length) % this.variants.length;
    this.currentChange.emit(this.variants[next].key);
  }

  @HostListener('window:keydown', ['$event'])
  onKeydown(e: KeyboardEvent) {
    const tag = (e.target as HTMLElement)?.tagName;
    if (tag === 'INPUT' || tag === 'TEXTAREA' || (e.target as HTMLElement)?.isContentEditable) return;
    if (e.key === 'ArrowLeft') this.cycle(-1);
    if (e.key === 'ArrowRight') this.cycle(1);
  }
}
