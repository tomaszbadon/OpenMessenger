import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, catchError } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = "http://localhost:8080/user";

  constructor(private http: HttpClient) { }

  getCurrentUser() : Observable<User> {

    const opt = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      }),
    };
    return this.http.get<User>(this.url, opt)
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
