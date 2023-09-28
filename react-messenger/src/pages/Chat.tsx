import { Contact } from '../datamodel/Contact';
import { ContactComponent } from '../components/Contact';
import { useParams, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useGetContactsQuery } from '../service/contactService';
import Finder from '../components/Finder';
import Conversation from '../components/Conversation';
import InfiniteScroll from 'react-infinite-scroll-component';
import './Chat.sass'

export interface SelectedContact {
  contact: Contact,
  selectedContact: Contact
}

interface TempState {
  items: number[]
}

export default function Chat() {

  const navigate = useNavigate();

  let { username } = useParams();

  const [filteredContacts, setFilteredContacts] = useState<Contact[] | null>(null)

  const { data, isLoading, error } = useGetContactsQuery()

  const [state, setState] = useState<TempState>({ items: [1] })

  let selectedContact = data?.contacts.find(contact => contact.userName === username) ?? data?.contacts[0]

  function selectUserOnClick(selectedContact: Contact) {
    navigate("/chat/" + selectedContact.userName);
  }

  const filterContacts = (input: string) => {
    if(input.length === 0) {
      setFilteredContacts(null)
    } else {
      let text = input.toLowerCase();
      setFilteredContacts(data?.contacts.filter(c => c.firstName.toLocaleLowerCase().startsWith(text) || c.lastName.toLocaleLowerCase().startsWith(text)) ?? [])
    }
  }

  const fetchMoreData = () => {
    setTimeout(() => {
      setState({
        items: state.items.concat(Array.from({ length: 1}))
      });
    }, 250);
  };

  let contacts: Contact[] = filteredContacts ?? data?.contacts ?? []

  return (
    <div className='box box-grid box-80-80'>
      <div className='contact-list-wrapper'>
        <div className='contact-list'>
          <Finder onChange={filterContacts} />
          {contacts.map(c => <ContactComponent
            key={c.userName}
            contact={c} selected={selectedContact?.userName === c.userName}
            hasNewMessage={c.userName === 'claudia.wiliams'}
            onClick={selectUserOnClick}
          />)}
        </div>
      </div>
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