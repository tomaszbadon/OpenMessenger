import { LeftSide } from '../components/LeftSide';
import Conversation from '../components/Conversation';
import { useParams } from 'react-router-dom';
import { useGetContactsQuery } from '../service/contactService';
import { useAppDispatch, useAppSelector } from '../auth/types';
import { setSelectedUser } from '../slice/CurrentUserSlice';
import { useGetInitialMessagesQuery } from '../service/messageService';
import { useEffect } from 'react';
import './Chat.sass'


export default function Chat() {

  const dispatch = useAppDispatch();

  let { username } = useParams();

  const { selectedContact } = useAppSelector(state => state.applicationContextSlice)

  const contactQueryResult = useGetContactsQuery()

  useGetInitialMessagesQuery(selectedContact?.id, { skip: !selectedContact })

  useEffect(() => {
    if (contactQueryResult.data && contactQueryResult.data.contacts.length > 0) {
      let newSelection = contactQueryResult.data?.contacts.find(contact => contact.userName === username) ?? contactQueryResult.data?.contacts[0]
      if (newSelection != selectedContact) {
        dispatch(setSelectedUser(newSelection))
      }
    }
  });

  return (
    <div className='box box-grid box-80-80'>
      <LeftSide />
      <Conversation />
      <div className='message-input-box'>
        <input type='text' placeholder='Type a message...' />
        <button><img src='/assets/envelope.png' height='16px' alt='send' /></button>
      </div>
    </div>
  );
}