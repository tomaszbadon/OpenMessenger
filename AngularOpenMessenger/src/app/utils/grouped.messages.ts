import { MessagesGroup } from 'src/app/model/messages-group';
import { Message } from '../model/message';

export class GroupedMessagesUtil {

  public static toMessageGroup(messages: Message[]): MessagesGroup[] {

    messages.sort((a1: Message, a2: Message) => {
      if(a1.id < a2.id) return -1;
      if(a1.id > a2.id) return 1;
      return 0;
    });

    let groupedMessages: MessagesGroup[] = [];
    for (let message of messages) {
      let date: string | any;
      let match = message.sentAt.match("^(\\d{2}-\\d{2}-\\d{4}).+$");
      if (match) {
        date = match[1];
      }
      if (groupedMessages.length == 0) {
        let messagesTempArray: Message[] = [];
        messagesTempArray.push(message);
        groupedMessages.push(new MessagesGroup(message.recipient, message.sender, date, messagesTempArray));
      } else {
        let last = groupedMessages[groupedMessages.length - 1];
        if (last.recipient === message.recipient && last.sender === message.sender && last.date == date) {
          last.messages.push(message);
        } else {
          let messagesTempArray: Message[] = [];
          messagesTempArray.push(message);
          var newGroup = new MessagesGroup(message.recipient, message.sender, date, messagesTempArray);
          groupedMessages.push(newGroup);
        }
      }
    }
    return groupedMessages;
  }

}