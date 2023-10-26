import { Message } from '../service/messageService';
import { DateSeparator } from './DateSeparator';
import { GroupOfMessages, groupMessages } from '../util/messagesUtil';
import { useMemo, useState } from 'react';
import { MessageGroup } from './MessageGroup';
import InfiniteScroll from 'react-infinite-scroll-component';
import useContactList from '../hooks/useContactList';
import useMessage from '../hooks/useMessage';
import './Conversation.sass'

export function Conversation() {

  const { selectedContact } = useContactList()

  const [ page, setPage ] = useState<number | undefined>(undefined);

  const { messages, firstPage } = useMessage(selectedContact, page)

  const groupedMessages = useMemo(() => groupMessages((messages ?? []).reduce((accumulator: Message[], value) => accumulator.concat(value.messages), [])), [messages])

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
        { groupedMessages.map((group: GroupOfMessages, index: number, array: GroupOfMessages[]) => (
          <>
            <DateSeparator condition={index === 0 || array[index - 1].date !== group.date} date={group.date} />
            <MessageGroup group={group} selectedContact={selectedContact} />
          </>
        ))}
      </InfiniteScroll>
      
    </div>
  )
}