import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExpiryItemService {
  private apiUrl = '/api/expiry-items';

  constructor(private http: HttpClient) { }

  getExpiryItems(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user/1`);
  }

  addExpiryItem(formData: FormData): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }

  getExpiringItems(daysLeft: number = 7): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user/1/expiring?daysLeft=${daysLeft}`);
  }

  deleteExpiryItem(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}