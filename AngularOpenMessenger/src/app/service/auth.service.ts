import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { catchError, map, mapTo, Observable, of, pipe, tap } from 'rxjs';
import { LoginInfo } from '../model/login-info';

@Injectable({
  providedIn: 'root'
})
export class AuthService {;

  jwtHelperService = new JwtHelperService();

  constructor(private http: HttpClient) { }

  public isAuthenticated(): boolean {
    const token = this.getLoginInfo().accessToken;
    if(token != null) {
      return !this.jwtHelperService.isTokenExpired(token);
    } else {
      return false;
    }
  }

  login(username: string, password: string): Observable<LoginInfo> {
    const body = new HttpParams()
    .set('username', username)
    .set('password', password);
    const headers = { headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded') } ;
    return this.http.post<LoginInfo>('http://localhost:8080/login', body.toString(), headers).pipe(
      tap(loginInfo =>  {
          this.storeLoginInfo(loginInfo);
          console.log(JSON.stringify(loginInfo));
        }
      ),
    );
  }

  logout() {
    this.removeLoginInfo();
  }

  getLoginInfo() {
    return JSON.parse(localStorage.getItem('JWT_TOKEN')!);
  }

  private storeLoginInfo(loginInfo: LoginInfo) {
    localStorage.setItem('JWT_TOKEN', JSON.stringify(loginInfo));
  }

  private removeLoginInfo() {
    localStorage.removeItem('JWT_TOKEN',);

  }

}
