import { Contact } from '../datamodel/Contact';
import { ContactComponent } from '../components/Contact';
import { useParams, useNavigate } from 'react-router-dom';
import { useState, useCallback } from 'react';
import { AuthData } from '../auth/AuthWrapper';
import Finder from '../components/Finder';
import './Chat.sass'
import Conversation from '../components/Conversation';
import InfiniteScroll from 'react-infinite-scroll-component';

export interface SelectedContact {
  contact: Contact,
  selectedContact: Contact
}

interface TempState {
  items: number[]
}

let contacts: Contact[] = [
  { username: "daniel.silva", firstName: "Daniel", lastName: "Silva", avatar: "avatar_1.png" },
  { username: "dominica.rosatti", firstName: "Dominica", lastName: "Rosatti", avatar: "avatar_2.png" },
  { username: "christian.wolf", firstName: "Christopher", lastName: "Wolf", avatar: "avatar_3.png" },
  { username: "claudia.williams", firstName: "Claudia", lastName: "Williams", avatar: "avatar_4.png" },
  { username: "monica.rosatti", firstName: "Monica", lastName: "Rosatti", avatar: "avatar_5.png" }
]

export default function Chat() {
  let { username } = useParams();
  const [filteredContacts, setFilteredContacts] = useState(contacts.sort((a: Contact, b: Contact) => (a.lastName < b.lastName ? -1 : 1)));
  const navigate = useNavigate();
  const context = AuthData();

  const [state, setState] = useState<TempState>({ items: [1] })

  let selectedContact = contacts.find(contact => contact.username === username) ?? contacts[0];

  function selectUserOnClick(selectedContact: Contact) {
    navigate("/chat/" + selectedContact.username);
  }

  const filterContacts = useCallback((input: string) => {
    let text = input.toLocaleLowerCase();
    setFilteredContacts(contacts.filter(c => c.firstName.toLocaleLowerCase().startsWith(text) || c.lastName.toLocaleLowerCase().startsWith(text)));
  }, []);

  function doLogout() {
    context?.logout();
    navigate("/login");
  }

  const fetchMoreData = () => {
    setTimeout(() => {
      setState({
        items: state.items.concat(Array.from({ length: 1}))
      });
    }, 250);
  };

  return (
    <div className='box box-grid box-80-80'>
      <div className='contact-list'>
        <Finder onChange={filterContacts} />
        {filteredContacts.map(c => <ContactComponent
          key={c.username}
          contact={c} selected={selectedContact.username === c.username}
          hasNewMessage={c.username === 'claudia.williams'}
          onClick={selectUserOnClick}
        />)}
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

        {/* <Conversation /> */}
      </div>
    </div>
  );
}