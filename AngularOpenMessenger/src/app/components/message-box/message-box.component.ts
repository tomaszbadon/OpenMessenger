import { Component, ElementRef, HostListener, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { Contact } from 'src/app/model/contact';
import { MessagesGroup } from 'src/app/model/messages-group';
import { User } from 'src/app/model/user';
import { ConversationService } from 'src/app/service/conversation.service';
import { GroupedMessagesUtil } from 'src/app/utils/grouped.messages';
import { EventQueueService } from 'src/app/service/event-queue.service';
import { AppEvent } from 'src/app/utils/app-event';
import { AppEventType } from 'src/app/utils/app-event-type';
import { Message } from 'src/app/model/message';
import { fromEvent } from 'rxjs';
import { interval } from 'rxjs';
import { debounce } from 'rxjs/operators';
import { DOCUMENT } from '@angular/common';
import { Inject } from '@angular/core';
import { Notification } from 'src/app/model/notification';
import { NotificationService } from 'src/app/service/notification.service';

@Component({
  selector: 'app-message-box',
  templateUrl: './message-box.component.html',
  styleUrls: ['./message-box.component.sass']
})
export class MessageBoxComponent implements OnChanges, OnInit {

  readonly scrollTimeout = 10;
  readonly unlockDelay = 500;

  private messages: Message[] = [];
  private hasBouncedFromTheTop = false;
  private isLoadingPreviousMessages = false;

  showLoadingBar = true;
  groupedMessages: MessagesGroup[] = [];
  thereAreNoMessagesLeft = false;
  
  @Input() sender: Contact | any;
  @Input() recipient: User | any;

  constructor(private conversationService: ConversationService, 
              private notificationService: NotificationService,
              private eventQueue: EventQueueService, 
              private element: ElementRef,
              @Inject(DOCUMENT) private document: Document)
  { }

  ngOnInit(): void { 
    this.eventQueue.on(AppEventType.NewMessageSent).subscribe(event => this.handleNewMessage(event.payload));
    this.eventQueue.on(AppEventType.LogOut).subscribe(event => this.handleLogOutEvent(event));
    this.eventQueue.on(AppEventType.NewMessageNotifcation).subscribe(event => this.handleNewMessageNotification(event.payload));

    fromEvent<Event>(this.element.nativeElement, 'scroll').pipe(debounce(val => interval(100))).subscribe((event) => {
        this.handleScrollEvent(event);
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.hasBouncedFromTheTop = false;
    this.showLoadingBar = false;
    this.isLoadingPreviousMessages = false;
    this.thereAreNoMessagesLeft = false; 

    this.conversationService.getLatestMessages(this.recipient.id, this.sender.id).subscribe(messages => { 
      this.messages = messages;
      this.groupedMessages = GroupedMessagesUtil.toMessageGroup(messages);
      this.scrollToBottomAfterLoad();
      this.handleNotificationAcknownledge();
      this.checkIfThereAreNoMessagesLeft(messages);
    });
  } 

  private handleNewMessageNotification(notification: Notification) {
    if(notification.sender == this.sender.id) {
      this.conversationService.getMessage(notification.messageId).subscribe(message => this.handleNewMessage(message));
    }
  }

  private handleNewMessage(message: Message) {
    this.messages.push(message);
    this.groupedMessages = GroupedMessagesUtil.toMessageGroup(this.messages);
    this.scrollToBottomAfterLoad();
    this.handleNotificationAcknownledge();
  }

  private handleScrollEvent(event: Event) {
    let element = (event.target as HTMLElement);
    this.handleBouncedFromTop(event);

    if(this.canLoadPreviousMessages(element)) {
      this.showLoadingBar = true;
      this.isLoadingPreviousMessages = true;

      this.conversationService.getMessages(this.recipient.id, this.sender.id).subscribe(messages => { 
        this.messages = this.messages.concat(messages);
        this.groupedMessages = GroupedMessagesUtil.toMessageGroup(this.messages);
        this.showLoadingBar = false;
        this.checkIfThereAreNoMessagesLeft(messages);
        this.scrollToBottomOfPreviousMessages(messages);
        this.unlockLoadingPreviousMessagesWithDelay();
      });
    }
  }

  private unlockLoadingPreviousMessagesWithDelay() {
    setTimeout(() => {
      this.isLoadingPreviousMessages = false;
    }, this.unlockDelay);
  }

  private scrollToBottomOfPreviousMessages(messages: Message[]) {
    let messageCandidateId = this.findMessageCandidateToFocus(messages);
    setTimeout(() => {
      if(messageCandidateId != null) {
        this.document.getElementById('message_' + messageCandidateId)?.scrollIntoView();
      }
    }, this.scrollTimeout);
  }

  private checkIfThereAreNoMessagesLeft(messages: Message[]) {
    if(messages.length === 0) {
      this.thereAreNoMessagesLeft = true;
    }
  }

  private scrollToBottomAfterLoad() {
    setTimeout(() => {
      let div = this.element.nativeElement;
      div.scrollTop = div.scrollHeight;
    }, this.scrollTimeout);

  }

  private canLoadPreviousMessages(element: Element): boolean {
    return this.hasBouncedFromTheTop && element.scrollTop === 0 && this.isLoadingPreviousMessages === false;
  }

  private handleBouncedFromTop(event: Event) {
    //it prevents loading previous messages when a scroll bar is at top during view initialization
    let scrollableElement = (event.target as HTMLElement);
    if(scrollableElement.scrollTop !== 0) {
      this.hasBouncedFromTheTop = true;
    }
  }

  private handleNotificationAcknownledge() {
    let unacknowledgedMessages = this.messages.filter(m => m.acknowledged == false);
    if(unacknowledgedMessages.length > 0) {
      let notifications: Notification[] = unacknowledgedMessages.map(message => {
        let notification: Notification = { sender: message.sender, messageId: message.id };
        return notification;
      });
      this.notificationService.acknowledgedNotifications(this.recipient.id, notifications).subscribe(n => {
        unacknowledgedMessages.forEach(m => m.acknowledged = true);
        this.eventQueue.dispatch(new AppEvent(AppEventType.ClearNotifications, this.sender.id ));      
      });
    }
  }

  private handleLogOutEvent(event: AppEvent<any>) {
    this.messages = [];
  }

  private findMessageCandidateToFocus(message: Message[]) : number | null {
    if(message.length > 0) {
      return Math.max(...message.map(m => m.id));
    } else {
      return null
    }
  }

}