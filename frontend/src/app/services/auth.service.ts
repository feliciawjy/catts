import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private _authStatus = new BehaviorSubject<boolean>(this.isLoggedIn());
  authStatus = this._authStatus.asObservable();

  constructor(private http: HttpClient) { }

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/authenticate`, credentials).pipe(
      tap(response => {
        this.setSession(response);
        this._authStatus.next(true);
      })
    );
  }

  register(user: { username: string; email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user);
  }

  private setSession(authResult: any) {
    localStorage.setItem('token', authResult.token);
  }

  logout() {
    localStorage.removeItem('token');
    this._authStatus.next(false);
  }

  isLoggedIn() {
    return !!localStorage.getItem('token');
  }

  getToken() {
    return localStorage.getItem('token');
  }
}