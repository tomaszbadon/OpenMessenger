import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, catchError } from 'rxjs';
import { User } from '../model/user';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = "http://localhost:8080/api/users/";

  constructor(private http: HttpClient, private authService: AuthService) { }

  getCurrentUser() : Observable<User> {

    let userId = this.authService.getLoginInfo().userId;

    const opt = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      }),
    };
    return this.http.get<User>(this.url + userId, opt)
    .pipe(
      //catchError(this.handleError<User>('getContacts', [User]))
    );

  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}
