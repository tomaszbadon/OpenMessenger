import { useNavigate, useParams } from "react-router-dom";
import { Contact } from "../datamodel/Contact";
import Finder from "./Finder";
import { ContactComponent } from "./Contact";
import { useState } from "react";
import { useGetContactsQuery } from "../service/contactService";
import './LeftSide.sass'
import { useAppSelector } from "../auth/types";
import { stat } from "fs";

export function LeftSide() {

    const navigate = useNavigate();

    let { username } = useParams();

    const { selectedContact, contacts } = useAppSelector(state => state.contactSlice)

    const [filteredContacts, setFilteredContacts] = useState<Contact[] | null>(null)

    // let selectedContact = contacts.find(contact => contact.userName === username) ?? contacts[0]

    function selectUserOnClick(selectedContact: Contact) {
        navigate("/chat/" + selectedContact.userName);
    }

    // let contacts: Contact[] = filteredContacts ?? data?.contacts ?? []

    const filterContacts = (input: string) => {
        if(input.length === 0) {
          setFilteredContacts(null)
        } else {
          let text = input.toLowerCase();
          setFilteredContacts(contacts.filter(c => c.firstName.toLocaleLowerCase().startsWith(text) || c.lastName.toLocaleLowerCase().startsWith(text)) ?? [])
        }
    }

    console.log(JSON.stringify(contacts))

    return (
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
    )
}