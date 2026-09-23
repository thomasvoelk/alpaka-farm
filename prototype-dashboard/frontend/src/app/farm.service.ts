// PROTOTYPE — wayfinder ticket 04. Throwaway.
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Herd } from './models';

@Injectable({ providedIn: 'root' })
export class FarmService {
  constructor(private http: HttpClient) {}

  overview(): Observable<Herd[]> {
    return this.http.get<Herd[]>('http://localhost:8080/api/farm-overview');
  }
}
