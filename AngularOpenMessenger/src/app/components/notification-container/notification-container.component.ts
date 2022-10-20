import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Contact } from 'src/app/model/contact';
import { Notification } from 'src/app/model/notification';
import { EventQueueService } from 'src/app/service/event-queue.service';
import { NotificationService } from 'src/app/service/notification.service';
import { WebSocketNotificationService } from 'src/app/service/web-socket-notification.service';
import { AppEvent } from 'src/app/utils/app-event';
import { AppEventType } from 'src/app/utils/app-event-type';
import { User } from 'src/app/model/user';
import { NotificationExtension } from 'src/app/model/notification-extension';

@Component({
  selector: 'app-notification-container',
  templateUrl: './notification-container.component.html',
  styleUrls: ['./notification-container.component.sass']
})
export class NotificationContainerComponent implements OnInit {

  @Input() user: User | null = null;
  @Input() contacts: Contact[] = [];
  notifications: NotificationExtension[] = [];

  constructor(private webSocketNotificationService: WebSocketNotificationService,
              private notificationService: NotificationService,
              private eventQueue: EventQueueService
              ) { }

  ngOnInit(): void {
      this.notificationService.getNotifications(this.user?.id!).subscribe(notifications => notifications.forEach(n => this.handleNotification(n)));
      this.webSocketNotificationService.onNotification().subscribe((notification => this.handleNotification(notification)));
  }

  private handleNotification(notification: Notification) {
    this.displayNotification(notification);
    this.eventQueue.dispatch(new AppEvent(AppEventType.NewMessageNotifcation, notification));
  }

  private displayNotification(receivedNotification: Notification) {
    this.notifications = this.notifications.filter((m) => m.visible);
    let notification = this.notifications.find((m) => m.sender === receivedNotification.sender);
    if(notification != null) {
      notification.numberOfMessages!++;
    } else {
      let sender = this.contacts.find((c) => c.id === receivedNotification.sender);
      this.notifications.push({ 
            sender: receivedNotification.sender,
            messageId: receivedNotification.messageId,
            firstName: sender?.firstName!,
            lastName: sender?.lastName!,
            numberOfMessages: 1,
            visible: true,
            avatar: sender!.avatar
      });
    }
  }
}