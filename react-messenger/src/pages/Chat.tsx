import { Contact } from '../datamodel/Contact';
import { ContactComponent } from '../components/Contact';
import { useParams, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useGetContactsQuery } from '../service/contactService';
import { useAppDispatch, useAppSelector } from '../auth/types';
import { useGetCurrentUserQuery } from '../service/loginService';
import { setCurrentUser } from '../slice/CurrentUserSlice';
import Finder from '../components/Finder';
import Conversation from '../components/Conversation';
import InfiniteScroll from 'react-infinite-scroll-component';
import './Chat.sass'
import { LeftSide } from '../components/LeftSide';
import { setContacts } from '../slice/ContactSlice';

export interface SelectedContact {
  contact: Contact,
  selectedContact: Contact
}

interface TempState {
  items: number[]
}

export default function Chat() {

  const navigate = useNavigate();

  const dispatch = useAppDispatch();

  let { username } = useParams();

  let currentUser = useAppSelector(state => state.currentUserSlice)

  let contacts = useAppSelector(state => state.contactSlice)

  const currentUserQuery = useGetCurrentUserQuery()

  const contactsQuery = useGetContactsQuery()

  const [state, setState] = useState<TempState>({ items: [1] })

  useEffect(() => {
    if(typeof currentUserQuery.data !== 'undefined') {
      dispatch(setCurrentUser(currentUserQuery.data))
    }

    if(typeof contactsQuery.data !== 'undefined') {
      dispatch(setContacts(contactsQuery.data))
    }
  })

  const fetchMoreData = () => {
    setTimeout(() => {
      setState({
        items: state.items.concat(Array.from({ length: 1}))
      });
    }, 250);
  };

  return (
    <div className='box box-grid box-80-80'>
        <LeftSide />
        <div id="scrollableDiv" className='conversation'>
          <InfiniteScroll
            dataLength={state.items.length}
            next={fetchMoreData}
            hasMore={true}
            loader={<h4>Loading...</h4>}
            scrollableTarget="scrollableDiv"
            inverse={true}
            
            refreshFunction={fetchMoreData}
            pullDownToRefresh={true}
            pullDownToRefreshContent={
              <div style={{ textAlign: 'center' }}>AAA</div>
            }
            releaseToRefreshContent={
              <div style={{ textAlign: 'center' }}>BBB</div>
            }
          >

            {state.items.map((i: number, index: number) => (
              <Conversation key={index} index={index} />
            ))}
          </InfiniteScroll>
        </div>
      <div className='message-input-box'>
            <input type='text' placeholder='Type a message...' />
            <button><img src='/assets/envelope.png' height='16px' alt='send'/></button>
      </div>
    </div>
  );
}