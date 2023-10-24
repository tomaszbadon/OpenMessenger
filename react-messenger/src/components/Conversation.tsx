import { Message, useLazyGetMessagesQuery, MessagePage, useGetInitialMessagesQuery } from '../service/messageService';
import { DateSeparator } from './DateSeparator';
import { GroupOfMessages, groupMessages } from '../util/messagesUtil';
import InfiniteScroll from 'react-infinite-scroll-component';
import { useAppSelector } from '../auth/types';
import './Conversation.sass'

export default function Conversation() {

  const { messages, selectedContact } = useAppSelector(state => state.applicationContextSlice)

  const lazyGetMessagesQuery = useLazyGetMessagesQuery();

  let messagesForContact: MessagePage[] | undefined

  let theMinimalPage: number | undefined = undefined

  if (selectedContact) {
    messagesForContact = messages.get(selectedContact.id)?.messages
    theMinimalPage = messages.get(selectedContact.id)?.theMinimalPage
  }

  function fetchMoreData() {
    const [trigger] = lazyGetMessagesQuery;
    if (theMinimalPage && theMinimalPage > -1) {
      trigger({ userId: selectedContact?.id ?? "0", page: theMinimalPage - 1 })
    }
  }

  return (
    <div id="scrollableDiv" className='conversation'>

      <InfiniteScroll
        dataLength={(messagesForContact ?? []).length}
        next={fetchMoreData}
        hasMore={typeof theMinimalPage !== 'undefined' && theMinimalPage > 0}
        loader={''}
        scrollableTarget="scrollableDiv"
        refreshFunction={fetchMoreData}
        inverse={true}
      >
        {groupMessages((messagesForContact ?? []).reduce((accumulator: Message[], value) => accumulator.concat(value.messages), [])).map((group: GroupOfMessages, index: number, array: GroupOfMessages[]) => (
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