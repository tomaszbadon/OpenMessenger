import { Message } from '../service/messageService';
import { DateSeparator } from './DateSeparator';
import { GroupOfMessages, groupMessages } from '../util/messagesUtil';
import { useEffect, useMemo, useState } from 'react';
import { MessageGroup } from './MessageGroup';
import InfiniteScroll from 'react-infinite-scroll-component';
import useContactList from '../hooks/useContactList';
import useMessage from '../hooks/useMessage';
import './Conversation.sass'

export function Conversation() {

  const { selectedContact } = useContactList()

  const [page, setPage] = useState<number | undefined>(undefined);

  const { isLoading, messages, firstPage } = useMessage(selectedContact, page)

  const groupedMessages = useMemo(() => groupMessages((messages ?? []).reduce((accumulator: Message[], value) => accumulator.concat(value.messages), [])), [messages])

  useEffect(() => {
    if (typeof page !== 'undefined' && typeof firstPage === 'undefined') {
      setPage(undefined)
    }
  }, [page, firstPage])

  function fetchMoreData() {
    if (firstPage && firstPage > 0) {
      setPage(firstPage - 1)
    }
  }

  function generateKey(group: GroupOfMessages) {
    return group.messages.map(m => m.id).join("")
  }

  if (isLoading && !firstPage) {
    return (<div className='loader-main-container'>
      <img width={64} height={64} src="/assets/loader.gif" />
    </div>)
  } else if (messages.length > 0) {
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
          initialScrollY={10}
          scrollThreshold={0.8}
        >
          {groupedMessages.map((group: GroupOfMessages, index: number, array: GroupOfMessages[]) => (
            <>
              <DateSeparator key={`date_separator_key_${generateKey(group)}`} condition={index === 0 || array[index - 1].date !== group.date} date={group.date} />
              <MessageGroup key={`message_group_key_${generateKey(group)}`} group={group} selectedContact={selectedContact} />
            </>
          ))}
        </InfiniteScroll>
        {typeof firstPage !== 'undefined' && isLoading &&
          <div className='loader-container'>
            <img width={32} height={32} src="/assets/loader.gif" />
          </div>
        }
      </div>
    )
  } else {
    return (
      <div className='loader-main-container'>
        <div className='date-separator'>
          There is no history conversation...
        </div>
      </div>)
  }
}