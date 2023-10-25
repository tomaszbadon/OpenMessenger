import { Message } from '../service/messageService';
import { DateSeparator } from './DateSeparator';
import { GroupOfMessages, groupMessages } from '../util/messagesUtil';
import InfiniteScroll from 'react-infinite-scroll-component';
import useContactList from '../hooks/useContactList';
import './Conversation.sass'
import useMessage from '../hooks/useMessage';
import { useEffect, useState } from 'react';
import { useAppSelector } from '../auth/types';

export function Conversation() {

  const { selectedContact } = useContactList()

  const [ page, setPage ] = useState<number | undefined>(undefined);

  const { messages, firstPage } = useMessage(selectedContact, page)

  function fetchMoreData() {
    if(firstPage && firstPage > 0) {
      setPage(firstPage - 1)
    }
  }

  return (
    <div id="scrollableDiv" className='conversation'>

      <InfiniteScroll
        dataLength={(messages).length}
        next={fetchMoreData}
        hasMore={typeof firstPage !== 'undefined' && firstPage > 0}
        loader={''}
        scrollableTarget="scrollableDiv"
        refreshFunction={fetchMoreData}
        inverse={true}
      >
        {groupMessages((messages ?? []).reduce((accumulator: Message[], value) => accumulator.concat(value.messages), [])).map((group: GroupOfMessages, index: number, array: GroupOfMessages[]) => (
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