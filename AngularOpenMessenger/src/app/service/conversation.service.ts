import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, map, catchError, tap, merge } from 'rxjs';
import { Message } from '../model/message';
import { MessageCache } from '../model/message-cache';

@Injectable({
  providedIn: 'root'
})
export class ConversationService {

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  private url = 'http://localhost:8080/api/messages';

  constructor(private http: HttpClient) { }

  getMessages(userId1: number, userId2: number): Observable<Message[]> {
    const params = new HttpParams()
      .set('page', 0)
      .set('user1', userId1)
      .set('user2', userId2)

    const opt = {
      headers: this.headers,
      params: params
    };

    return this.http.get<Message[]>(this.url, opt)
      .pipe(
        catchError(this.handleError<Message[]>('getConversation', []))
      );
  }

  getPrevioustMessages(userId1: number, userId2: number, lowerIdThen: number): Observable<Message[]> {
    const params = new HttpParams()
      .set('page', 0)
      .set('user1', userId1)
      .set('user2', userId2)
      .set('lowerIdThan', lowerIdThen)

    const opt = {
      headers: this.headers,
      params: params
    };

    return this.http.get<Message[]>(this.url, opt)
      .pipe(
        catchError(this.handleError<Message[]>('getConversation', []))
      );
  }

  postMessage(message: string, recipient: number): Observable<Message> {
    const opt = { headers: this.headers };
    let body = { "message": message, "recipient": recipient };
    return this.http.post<Message>(this.url, body, opt).pipe(
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