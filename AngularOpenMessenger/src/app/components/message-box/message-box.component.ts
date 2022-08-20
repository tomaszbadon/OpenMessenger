import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Contact } from 'src/app/model/contact';
import { Message } from 'src/app/model/message';
import { MessagesGroup } from 'src/app/model/messages-group';
import { User } from 'src/app/model/user';
import { ConversationService } from 'src/app/service/conversation.service';
import { GroupedMessagesUtil } from 'src/app/utils/grouped.messages';

@Component({
  selector: 'app-message-box',
  templateUrl: './message-box.component.html',
  styleUrls: ['./message-box.component.sass']
})
export class MessageBoxComponent implements OnChanges {

  groupedMessages: MessagesGroup[] = [];

  @Input() contact: Contact | any;

  @Input() user: User | any;

  constructor(private conversationService: ConversationService) { }

  ngOnChanges(changes: SimpleChanges): void {
    this.conversationService.getConversation(this.contact.id).subscribe(messages => { 
      this.groupedMessages = GroupedMessagesUtil.toMessageGroup(messages);
    });
  }

}
