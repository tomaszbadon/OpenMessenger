import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Service } from './service';
import { Notification } from 'src/app/model/notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationService extends Service {

  constructor(private httpClient: HttpClient) {
    super();
  }

  public getNotifications(userId: number): Observable<Notification[]> {
    const opt = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      }),
    };
    let url = "/api/users/{userId}/notifications".replace('{userId}', userId.toString());
    return this.httpClient.get<Notification[]>(url, opt);
  }

  public acknowledgedNotifications(userId: number, notification: Notification[]): Observable<Notification[]> {
    let body = { notificationsToAcknowledge: notification };
    const opt = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      }),
    };

    let url = "/api/users/{userId}/notifications".replace('{userId}', userId.toString());
    this.httpClient.post(url, body, opt).subscribe((o) => { });
    return of(notification);
  }

}
