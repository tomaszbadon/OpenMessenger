import { useEffect } from 'react';
import { useAppSelector } from '../auth/types';
import { Message, useGetMessagesQuery } from '../service/messageService';
import './Conversation.sass'

interface ConversationProp {
  index: number
}

interface GroupOfMessages {
  recipient: String,
  sender: String,
  messages: Message[]
  date: string
}


function groupMessages(messages: Message[]): GroupOfMessages[] {

  let groupOfMessages = new Array<GroupOfMessages>()

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

export default function Conversation(prop: ConversationProp) {

  let currentUser = useAppSelector(state => state.currentUserSlice);

  const userId = "6511e2afc7ef1c6367748b99"

  const response = useGetMessagesQuery({ userId: userId, page: 0 });

  let groupedMessages: GroupOfMessages[] = []

  if (!response.isError && response.data) {
    groupedMessages = groupMessages(response.data.messages)
  }

   return (<>
     { groupedMessages.map((group: GroupOfMessages) => ( 
         <div className={group.sender === userId ? 'message-group-wrapper left' : 'message-group-wrapper right'}>

          {group.sender === userId &&
          <div className="message-avatar-container">
            <img alt="contact's avatar" className="contact-avatar-img" src={'/assets/avatar_1.png'} />
          </div>
          }

           <div className="message-group">
             { group.messages.map((message: Message) => (
              <div>
                <div className={message.sender === userId ? 'single-message single-message-left' : 'single-message single-message-right'}>
                  <p className="message">{message.message}</p>
                  <p className={message.sender === userId ? 'message-metadata message-metadata-left' : 'message-metadata message-metadata-right'}>{message.sentAt.split(' ')[1]}</p>
                </div>
              </div>
            ))}
          </div>
          {group.sender !== userId &&
          <div className="message-avatar-container">
              <img alt="contact's avatar" className="contact-avatar-img" src={'/assets/avatar_1.png'} />
          </div>}
        </div>
    ))}
    </>
  )
}