import './App.sass'
import React, { useState } from 'react';
import { Contact } from './datamodel/Contact';
import { ContactComponent } from './components/Contact';

export interface SelectedContact {
  contact: Contact,
  selectedContact: Contact
}

function App() {

  let contact: Contact[] = [
    {username: "daniel.silva", firstName: "Daniel", lastName: "Silva", avatar: "avatar_1.png"},
    {username: "dominica.rosatti", firstName: "Dominica", lastName: "Rosatti", avatar: "avatar_2.png"},
    {username: "christian.wolf", firstName: "Christopher", lastName: "Wolf", avatar: "avatar_3.png"},
    {username: "claudia.wiliams", firstName: "Claudia", lastName: "Wiliams", avatar: "avatar_4.png"},
    {username: "monica.rosatti", firstName: "Monica", lastName: "Rosatti", avatar: "avatar_5.png"}
  ]

  const [selectedContact, setSelectedContact] = useState(contact[0]);

  function selectUserOnClick(selectedContact: Contact) {
    setSelectedContact(selectedContact)
  }

  return (
        <div className='box'>
          <div className='contact-list'>
            { contact.map(contact => <ContactComponent 
                                        key={contact.username} 
                                        contact={contact} selected={selectedContact.username === contact.username }
                                        onClick={ selectUserOnClick } 
                                        />) }
          </div>
          <div className='conversation'>
            
          </div>
        </div>
  );
}

export default App;
