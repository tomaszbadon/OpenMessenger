import { Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Contact } from 'src/app/model/contact';
import { MessagesGroup } from 'src/app/model/messages-group';
import { User } from 'src/app/model/user';
import { ConversationService } from 'src/app/service/conversation.service';
import { GroupedMessagesUtil } from 'src/app/utils/grouped.messages';
import { EventQueueService } from 'src/app/service/event-queue.service';
import { AppEvent } from 'src/app/utils/app-event';
import { AppEventType } from 'src/app/utils/app-event-type';
import { Message } from 'src/app/model/message';
import { fromEvent, observable, Observable } from 'rxjs';

@Component({
  selector: 'app-message-box',
  templateUrl: './message-box.component.html',
  styleUrls: ['./message-box.component.sass']
})
export class MessageBoxComponent implements OnChanges, OnInit {

  private isReadyToLoadPreviousMessages = false;
  groupedMessages: MessagesGroup[] = [];
  messages: Message[] = [];

  @Input() recipient: Contact | any;
  @Input() sender: User | any;

  constructor(private conversationService: ConversationService, private eventQueue: EventQueueService, private element: ElementRef) { }

  ngOnInit(): void { 
    this.eventQueue.on(AppEventType.NewMessageSent).subscribe(event => this.handleNewMessageEvent(event));
    this.eventQueue.on(AppEventType.LogOut).subscribe(event => this.handleLogOutEvent(event));

    fromEvent<Event>(this.element.nativeElement, 'scroll').subscribe((event) => {
        this.handleScrollEvent(event);
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.conversationService.getLatestMessages(this.recipient.id, this.sender.id).subscribe(messages => { 
      this.messages = messages;
      this.groupedMessages = GroupedMessagesUtil.toMessageGroup(messages);
      this.scrollToBottomAfterLoad();
      this.isReadyToLoadPreviousMessages = false;
    });
  } 

  handleLogOutEvent(event: AppEvent<any>) {
    this.messages = [];
    this.groupedMessages = [];
    this.isReadyToLoadPreviousMessages = false;
  }

  handleNewMessageEvent(event: AppEvent<Message>) {
    this.messages.push(event.payload);
    this.groupedMessages = GroupedMessagesUtil.toMessageGroup(this.messages);
    this.scrollToBottomAfterLoad();
  }

  handleScrollEvent(event: Event) {
    let element = (event.target as  HTMLElement);

    if(this.isReadyToLoadPreviousMessages && element.scrollTop === 0) {
      this.conversationService.getMessages(this.recipient.id, this.sender.id).subscribe(messages => { 
        this.messages = this.messages.concat(messages);
        this.groupedMessages = GroupedMessagesUtil.toMessageGroup(this.messages);
      });
    }

    if(element.scrollTop != 0) {
      this.isReadyToLoadPreviousMessages = true;
    }
  }

  scrollToBottomAfterLoad() {
      this.element.nativeElement.scrollTop = this.element.nativeElement.scrollHeight;
  }
}