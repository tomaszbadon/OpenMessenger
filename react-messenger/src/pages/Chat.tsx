import { Contact } from '../datamodel/Contact';
import { ContactComponent } from '../components/Contact';
import { useParams, useNavigate } from 'react-router-dom';
import './Chat.sass'

export interface SelectedContact {
  contact: Contact,
  selectedContact: Contact
}

let contacts: Contact[] = [
  {username: "daniel.silva", firstName: "Daniel", lastName: "Silva", avatar: "avatar_1.png"},
  {username: "dominica.rosatti", firstName: "Dominica", lastName: "Rosatti", avatar: "avatar_2.png"},
  {username: "christian.wolf", firstName: "Christopher", lastName: "Wolf", avatar: "avatar_3.png"},
  {username: "claudia.wiliams", firstName: "Claudia", lastName: "Wiliams", avatar: "avatar_4.png"},
  {username: "monica.rosatti", firstName: "Monica", lastName: "Rosatti", avatar: "avatar_5.png"}
]

export default function Chat() {

      let { username } = useParams();
      const navigate = useNavigate();
      let selectedContact = contacts.find(contact => contact.username === username) ?? contacts[0];
    
      function selectUserOnClick(selectedContact: Contact) {
        navigate("/chat/" + selectedContact.username);
      }
    
      return (
            <div className='box'>
              <div className='contact-list'>
                { contacts.map(c => <ContactComponent
                                            key={c.username} 
                                            contact={c} selected={selectedContact.username === c.username }
                                            onClick={ selectUserOnClick } 
                                            />) }
              </div>
              <div className='conversation'>
                
              </div>
            </div>
      );
    }