import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { StompMessage } from 'src/app/utils/stomp-message';
import { AuthService } from './auth.service';
import { Observable, Subject } from 'rxjs';
import { Notification } from 'src/app/model/notification';


@Injectable({
  providedIn: 'root'
})
export class WebSocketNotificationService {

  private eventBrocker = new Subject<Notification>();

  private stompClient: Stomp.Client;

  constructor(private authService: AuthService) { 
    let webSocketEndPoint = "http://localhost:8080/stomp-endpoint";
    let socket = new SockJS(webSocketEndPoint);
    this.stompClient = Stomp.over(socket); 

    this.stompClient.connect({ Authorization: "Bearer " + this.authService.getLoginInfo().accessToken }, (frame: any) => {
      this.stompClient?.subscribe("/user/queue/new", (message: StompMessage) => {
        let notification = JSON.parse(message.body) as Notification;
          this.eventBrocker.next(notification); 
      });
    });
  }

  onNotification(): Observable<Notification> {
    return this.eventBrocker;
  } 

}