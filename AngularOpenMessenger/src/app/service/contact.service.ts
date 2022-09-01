import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, map, catchError } from 'rxjs';
import { Contact } from '../model/contact';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  private url = 'http://localhost:8080/api/users/{userId}/contacts';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getContacts() : Observable<Contact[]> {

    let userId = this.authService.getLoginInfo().userId;

    const opt = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      }),
    };
    return this.http.get<Contact[]>(this.url.replace('{userId}', userId), opt)
    .pipe(
      catchError(this.handleError<Contact[]>('getContacts', []))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }

}
