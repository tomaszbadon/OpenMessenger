import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, map, catchError, tap, merge } from 'rxjs';
import { Message } from '../model/message';
import { MessageCache } from '../model/message-cache';

@Injectable({
  providedIn: 'root'
})
export class ConversationService {

  private headers = new HttpHeaders({'Content-Type':  'application/json'});

  private cache: MessageCache = new MessageCache();
  
  private url = 'http://localhost:8080/api/messages';

  constructor(private http: HttpClient) { }

  getMessages(userId1: number, userId2: number) : Observable<Message[]> {
      const params = new HttpParams()
      .set('page', 0)
      .set('user1', userId1)
      .set('user2', userId2)

      const opt = {
        headers: this.headers,
        params:  params
      };

      let cachedMessages = this.cache.getMessages(userId1, userId2);
      if(cachedMessages.length == 0) {
        return this.http.get<Message[]>(this.url, opt)
        .pipe(
          tap(messages => this.cache.addMessages(messages, userId1, userId2)),
          catchError(this.handleError<Message[]>('getConversation', []))
        );
      } else {
        return of(cachedMessages);
      }
  }

  getPrevioustMessages(userId1: number, userId2: number) : Observable<Message[]> {
    let cachedMessages = this.cache.getMessages(userId1, userId2);
    if(cachedMessages.length == 0) {
      return of(cachedMessages);
    } else {
      let theLowestId = Math.min(...cachedMessages.map(m => m.id));

      const params = new HttpParams()
      .set('page', 0)
      .set('user1', userId1)
      .set('user2', userId2)
      .set('lowerIdThan', theLowestId)

      const opt = {
        headers: this.headers,
        params:  params
      };

      let currentResults = of(this.cache.getMessages(userId1, userId2));

      let previousResults = this.http.get<Message[]>(this.url, opt)
      .pipe(
        tap(messages => this.cache.addMessages(messages, userId1, userId2)),
        catchError(this.handleError<Message[]>('getConversation', []))
      );
      return merge(previousResults, currentResults);
    }
  }

  postMessage(message: string, recipient: number) : Observable<Message>{
    const opt = { headers: this.headers };
    let body = { "message" : message,  "recipient": recipient };
    return this.http.post<Message>(this.url, body, opt).pipe(
      tap(message => this.cache.addMessage(message, message.recipient, message.sender)),
      catchError(this.handleError<Message>('sendMessage'))
    );
  }

  clearCachedMessages() {
    this.cache.clear();
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}