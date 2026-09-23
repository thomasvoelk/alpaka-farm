// PROTOTYPE — wayfinder ticket 04. Throwaway types matching the backend's ad-hoc JSON shape.

export interface Shearing {
  id: number;
  sheared_on: string;
  fleece_weight_kg: number;
  buyer: string | null;
  sale_date: string | null;
  price: number | null;
}

export interface Animal {
  id: number;
  name: string;
  species: 'alpaca' | 'llama';
  birth_date: string;
  weight_kg: number;
  fleece_color: string;
  latestShearing: Shearing | null;
}

export interface Pen {
  id: number;
  label: string;
  capacity: number;
  animals: Animal[];
}

export interface Herd {
  id: number;
  name: string;
  farm_section: string;
  pens: Pen[];
}
