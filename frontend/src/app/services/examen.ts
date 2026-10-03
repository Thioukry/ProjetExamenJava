import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Examen {
  id?: number;
  titre: string;
  description: string;
  dureeMinutes: number;
  statut: string;
}

@Injectable({
  providedIn: 'root'
})
export class ExamenService {
  private apiUrl = `${environment.apiUrl}/examens`;

  constructor(private http: HttpClient) {}

  getExamens(): Observable<Examen[]> {
    return this.http.get<Examen[]>(this.apiUrl);
  }

  createExamen(examen: Examen): Observable<Examen> {
    return this.http.post<Examen>(this.apiUrl, examen);
  }

  deleteExamen(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
