import { Message, MessagePage } from "../service/messageService";

export interface GroupOfMessages {
    recipient: String,
    sender: String,
    messages: Message[]
    date: string
}

export function groupMessages(messages: Message[]): GroupOfMessages[] {
    let groupOfMessages = new Array<GroupOfMessages>()

    messages.sort((a, b) => {
        if(a.id < b.id) {
            return -1
        } else {
            return 1
        }
    })

    for (let message of messages) {
        let date: string | any;
        let match = message.sentAt.match("^(\\d{2}-\\d{2}-\\d{4}).+$");
        if (match) {
            date = match[1];
        }
        if (groupOfMessages.length === 0) {
            let messagesTempArray: Message[] = [];
            messagesTempArray.push(message);
            groupOfMessages.push({ recipient: message.recipient, sender: message.sender, messages: messagesTempArray, date: date });
        } else {
            let last = groupOfMessages[groupOfMessages.length - 1];
            if (last.recipient === message.recipient && last.sender === message.sender && last.date === date) {
                last.messages.push(message);
            } else {
                let messagesTempArray: Message[] = [];
                messagesTempArray.push(message);
                var newGroup = { recipient: message.recipient, sender: message.sender, messages: messagesTempArray, date: date }
                groupOfMessages.push(newGroup);
            }
        }
    }
    return groupOfMessages;
}
