import { MessagesGroup } from 'src/app/model/messages-group';
import { Message } from '../model/message';

export class GroupedMessagesUtil {

    public static toMessageGroup(messages: Message[]): MessagesGroup[] {
        let groupedMessages: MessagesGroup[] = [];
        for(let message of messages) {
            if(groupedMessages.length == 0) {
              let messagesTempArray: Message[] = [];
              messagesTempArray.push(message);
              var newGroup = new MessagesGroup(message.recipient, message.sender, messagesTempArray);
              groupedMessages.push(newGroup);
            } else {
              let last = groupedMessages[groupedMessages.length - 1];
              if(last.recipient === message.recipient && last.sender === message.sender) {
                last.messages.push(message);
              } else {
                let messagesTempArray: Message[] = [];
                messagesTempArray.push(message);
                var newGroup = new MessagesGroup(message.recipient, message.sender, messagesTempArray);
                groupedMessages.push(newGroup);
              }
            }
          }
          return groupedMessages;
    }

}