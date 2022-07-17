import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { catchError, map, mapTo, Observable, of, pipe, tap } from 'rxjs';
import { Token } from '../utils/token';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  jwtHelperService = new JwtHelperService();

  constructor(private http: HttpClient) { }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('JWT_TOKEN');
    if(token != null) {
      return !this.jwtHelperService.isTokenExpired(token);
    } else {
      return false;
    }
  }

  login(username: string, password: string): Observable<boolean> {
    const body = new HttpParams()
    .set('username', username)
    .set('password', password);
    const headers = { headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded') } ;
    return this.http.post<Token>('http://localhost:8080/login', body.toString(), headers).pipe(
      tap(token => this.storeToken(token)),
      mapTo(true),
      catchError(e => { console.error(e); return of(false) })
    );
  }

  logout() {
    this.removeToken();
  }

  getToken() {
    return localStorage.getItem('JWT_TOKEN');
  }

  private storeToken(token: Token) {
    localStorage.setItem('JWT_TOKEN', token.access_token);
    localStorage.setItem('REFRESH_TOKEN', token.refresh_token);
  }

  private removeToken() {
    localStorage.removeItem('JWT_TOKEN',);
    localStorage.removeItem('REFRESH_TOKEN');
  }

}
