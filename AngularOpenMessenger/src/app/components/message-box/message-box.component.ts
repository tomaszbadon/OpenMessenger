import { Component, ElementRef, HostListener, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Contact } from 'src/app/model/contact';
import { MessagesGroup } from 'src/app/model/messages-group';
import { User } from 'src/app/model/user';
import { ConversationService } from 'src/app/service/conversation.service';
import { GroupedMessagesUtil } from 'src/app/utils/grouped.messages';
import { EventQueueService } from 'src/app/service/event-queue.service';
import { AppEvent } from 'src/app/utils/app-event';
import { AppEventType } from 'src/app/utils/app-event-type';
import { Message } from 'src/app/model/message';

@Component({
  selector: 'app-message-box',
  templateUrl: './message-box.component.html',
  styleUrls: ['./message-box.component.sass']
})
export class MessageBoxComponent implements OnChanges, OnInit {

  private readonly default_timeout = 0;

  private isReady = false;

  groupedMessages: MessagesGroup[] = [];

  @Input() recipient: Contact | any;

  @Input() sender: User | any;

  constructor(private conversationService: ConversationService, private eventQueue: EventQueueService, private element: ElementRef) { }

  ngOnInit(): void { 
    this.eventQueue.on(AppEventType.NewMessageSent).subscribe(event => this.handleNewMessageEvent(event.payload));
    this.eventQueue.on(AppEventType.LogOut).subscribe(event => this.handleLogOutEvent(event.payload));

    let div2 = this.element.nativeElement;
    div2.addEventListener('scroll', (e: Event) => {
      if(div2.scrollTop == 0 && this.isReady) { 
        this.conversationService.getPrevioustMessages(this.recipient.id, this.sender.id).subscribe(messages => { 
          this.groupedMessages = GroupedMessagesUtil.toMessageGroup(messages);
        });
      }

      if(div2.scrollTop != 0) {
        this.isReady = true;
      }
    });


  }

  handleLogOutEvent(event: AppEvent<any>) {
    this.conversationService.clearCachedMessages();
  }

  handleNewMessageEvent(event: AppEvent<Message>) {
    this.conversationService.getMessages(this.recipient.id, this.sender.id).subscribe(messages => { 
      this.groupedMessages = GroupedMessagesUtil.toMessageGroup(messages);
      this.scrollToBottomAfterLoad();
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.conversationService.getMessages(this.recipient.id, this.sender.id).subscribe(messages => { 
      this.groupedMessages = GroupedMessagesUtil.toMessageGroup(messages);
      this.scrollToBottomAfterLoad();
    });
  } 

  scrollToBottomAfterLoad() {
    setTimeout(() => {
      let div = this.element.nativeElement;
          div.scrollTop = div.scrollHeight;
      }, this.default_timeout);
  }

}