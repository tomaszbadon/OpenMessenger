import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, map, catchError, tap, merge, from } from 'rxjs';
import { Message } from '../model/message';
import { MessagesPage } from '../model/messages-page';
import { Service } from './service';

@Injectable({
  providedIn: 'root'
})
export class ConversationService extends Service {

  private page = 0;

  constructor(private http: HttpClient) { super(); }

  getLatestMessages(userId1: number, userId2: number): Observable<Message[]> {

    const params = new HttpParams()
      .set('user1', userId1)
      .set('user2', userId2)

    const opt = {
      headers: this.defaultJsonHeaders,
      params: params
    };

    return this.http.get<MessagesPage>('/api/messages/latest', opt)
      .pipe(
        tap(response => this.page = response.page),
        map(response => response.messages),
        catchError(this.handleError<Message[]>('getConversation', []))
      );
  }

  getMessages(userId1: number, userId2: number): Observable<Message[]> {

    if(this.page === 0) {
      return of(new Array<Message>());
    }

    this.page--;

    const params = new HttpParams()
      .set('page', this.page)
      .set('user1', userId1)
      .set('user2', userId2)

    const opt = {
      headers: this.defaultJsonHeaders,
      params: params
    };

    return this.http.get<MessagesPage>('/api/messages', opt)
      .pipe(
        map(response => response.messages),
        catchError(this.handleError<Message[]>('getConversation', []))
      );
  }

  postMessage(message: string, recipient: number): Observable<Message> {
    const opt = { headers: this.defaultJsonHeaders };
    let body = { "message": message, "recipient": recipient };

    return this.http.post<Message>('/api/messages', body).pipe(
      catchError(this.handleError<Message>('sendMessage'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}