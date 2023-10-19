import { useParams } from 'react-router-dom';
import { useAppSelector } from '../auth/types';
import { Contact } from '../datamodel/Contact';
import { useGetContactsQuery } from '../service/contactService';
import { Message, useLazyGetMessagesQuery, useLazyGetInitialMessagesQuery } from '../service/messageService';
import { DateSeparator } from './DateSeparator';
import { useEffect, useState } from 'react';
import { GroupOfMessages, groupMessages } from '../util/messagesUtil';
import InfiniteScroll from 'react-infinite-scroll-component';
import './Conversation.sass'

export default function Conversation() {

  let { username } = useParams();

  let someMessages = useAppSelector(state => state.conversationSlice)

  let currentPage = someMessages.messagePages.length > 0 ? Math.min(...someMessages.messagePages.map(m => m.page)) : 0

  let [selectedContact, setSelectedContact] = useState<Contact | undefined>(undefined)

  const contactQueryResult = useGetContactsQuery()

  const lazyGetInitialMessagesQuery = useLazyGetInitialMessagesQuery();

  const lazyGetMessagesQuery = useLazyGetMessagesQuery();

  useEffect(() => {
    if (contactQueryResult.data && contactQueryResult.data.contacts.length > 0) {
      let newSelection = contactQueryResult.data?.contacts.find(contact => contact.userName === username) ?? contactQueryResult.data?.contacts[0]
      if (newSelection != selectedContact) {
        setSelectedContact(newSelection)
        const [ trigger ] = lazyGetInitialMessagesQuery
        trigger(newSelection?.id);
      }
    }
  })

  function fetchMoreData() {
    const [ trigger ] = lazyGetMessagesQuery;
    trigger({ userId: selectedContact?.id ?? "0", page: currentPage - 1 })
  }

  return (
    <div id="scrollableDiv" className='conversation'>

    <InfiniteScroll
            dataLength={ someMessages.messagePages.length }
            next={fetchMoreData}
            hasMore={ currentPage > 0 }
            loader={<h4>Loading...</h4>}
            scrollableTarget="scrollableDiv"
            inverse={true}
            refreshFunction={fetchMoreData}
            pullDownToRefresh={true}
      >

      { groupMessages(someMessages.messagePages.reduce((accumulator: Message[], value) => accumulator.concat(value.messages), [])).map((group: GroupOfMessages, index: number, array: GroupOfMessages[]) => (
        <>
          <DateSeparator condition={index === 0 || array[index - 1].date !== group.date} date={group.date} />
          <div className={group.sender === selectedContact?.id ? 'message-group-wrapper left' : 'message-group-wrapper right'}>
            {group.sender === selectedContact?.id &&
              <div className="message-avatar-container">
                <img alt="contact's avatar" className="contact-avatar-img-min" src={`/api/users/${group.sender}/avatar`} />
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
                <img alt="contact's avatar" className="contact-avatar-img-min" src={`/api/users/${group.sender}/avatar`} />
              </div>}
          </div>
        </>
      ))}
      </InfiniteScroll>
    </div>
  )
}