import { useParams } from 'react-router-dom';
import { useAppSelector } from '../auth/types';
import { Contact } from '../datamodel/Contact';
import { useGetContactsQuery } from '../service/contactService';
import { Message, useGetMessagesQuery } from '../service/messageService';
import './Conversation.sass'
import { DateSeparator } from './DateSeparator';

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

  let map = new Map<string, GroupOfMessages>();

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

  let { username } = useParams();

  let currentUser = useAppSelector(state => state.currentUserSlice)

  const contactsQuery = useGetContactsQuery()

  let selectedContact: Contact | undefined = undefined

  if (contactsQuery.data) {
    selectedContact = contactsQuery.data.contacts.find(c => c.userName === username) ?? contactsQuery.data.contacts[0]
  }

  const responseWithMessages = useGetMessagesQuery({ userId: selectedContact?.id, page: 0 }, { skip: typeof selectedContact === 'undefined' });

  let groupedMessages: GroupOfMessages[] = []

  if (!responseWithMessages.isError && responseWithMessages.data) {
    groupedMessages = groupMessages(responseWithMessages.data.messages)
  }

  return (<>
    {groupedMessages.map((group: GroupOfMessages, index: number, array: GroupOfMessages[]) => (
      <>
        <DateSeparator condition={index === 0 || array[index - 1].date !== group.date} date={group.date} />
        <div className={group.sender === selectedContact?.id ? 'message-group-wrapper left' : 'message-group-wrapper right'}>
          {group.sender === selectedContact?.id &&
            <div className="message-avatar-container">
              <img alt="contact's avatar" className="contact-avatar-img-min" src={`/api/users/${selectedContact?.id}/avatar`} />
            </div>
          }
          <div className="message-group">
            {group.messages.map((message: Message) => (
              <div>
                <div className={message.sender === selectedContact?.id ? 'single-message single-message-left' : 'single-message single-message-right'}>
                  <p className="message">{message.message}</p>
                  <p className={message.sender === selectedContact?.id ? 'message-metadata message-metadata-left' : 'message-metadata message-metadata-right'}>{message.sentAt.split(' ')[1]}</p>
                </div>
              </div>
            ))}
          </div>
          {group.sender !== selectedContact?.id &&
            <div className="message-avatar-container">
              <img alt="contact's avatar" className="contact-avatar-img-min" src={`/api/users/${currentUser.id}/avatar`} />
            </div>}
        </div>
      </>
    ))}
  </>
  )
}