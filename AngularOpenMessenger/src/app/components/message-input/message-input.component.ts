import { Component, Input, OnInit } from '@angular/core';
import { Contact } from 'src/app/model/contact';
import { ConversationService } from 'src/app/service/conversation.service';
import { EventQueueService } from 'src/app/service/event-queue.service';
import { AppEvent } from 'src/app/utils/app-event';
import { AppEventType } from 'src/app/utils/app-event-type';

@Component({
  selector: 'app-message-input',
  templateUrl: './message-input.component.html',
  styleUrls: ['./message-input.component.sass']
})
export class MessageInputComponent {

  @Input() recipient: Contact | any

  constructor(private conversationService: ConversationService, private eventQueue: EventQueueService) { }

  onClick(message: HTMLInputElement) {
    this.sendMessage(message);
  }

  onKeydown(message: HTMLInputElement) {
    this.sendMessage(message);
  }

  sendMessage(message: HTMLInputElement) {
    this.conversationService.postMessage(message.value, this.recipient.id).subscribe(
      m => { this.eventQueue.dispatch(new AppEvent(AppEventType.NewMessageSent, m));});
    message.value = "";
  }

}
