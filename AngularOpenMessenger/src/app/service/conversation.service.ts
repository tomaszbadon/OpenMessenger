import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, map, catchError } from 'rxjs';
import { Message } from '../model/message';

@Injectable({
  providedIn: 'root'
})
export class ConversationService {
  
  private url = 'http://localhost:8080/messages-api/conversation/';

  constructor(private http: HttpClient) { }

  getConversation(userId: number) : Observable<Message[]> {
    
    const params = new HttpParams()
    .set('page', 0)

    const opt = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      }),
      params:  params
    };
    return this.http.get<Message[]>(this.url + userId, opt)
    .pipe(
      map(messages => messages.sort((a1: Message, a2: Message) => {
        if(a1.id < a2.id) return -1;
        if(a1.id > a2.id) return 1;
        return 0;
    })),
      catchError(this.handleError<Message[]>('getConversation', []))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}