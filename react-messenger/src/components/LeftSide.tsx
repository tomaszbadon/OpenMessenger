import { useNavigate, useParams } from "react-router-dom";
import { Contact } from "../datamodel/Contact";
import { ContactComponent } from "./Contact";
import { useState } from "react";
import { useGetContactsQuery } from "../service/contactService";
import { useAppSelector } from "../auth/types";
import Finder from "./Finder";
import './LeftSide.sass'

export function LeftSide() {

    const navigate = useNavigate();

    let contacts: Contact[] = []

    const { selectedContact } = useAppSelector(state => state.applicationContextSlice)

    const [filteredContacts, setFilteredContacts] = useState<Contact[] | undefined>(undefined)

    const contactsQuery = useGetContactsQuery()


    if (contactsQuery.data) {
        contacts = contactsQuery.data.contacts
    }

    function selectUserOnClick(selectedContact: Contact) {
        navigate("/chat/" + selectedContact.userName);
    }

    const filterContacts = (input: string) => {
        if (input.length === 0) {
            setFilteredContacts(undefined)
        } else {
            let text = input.toLowerCase();
            setFilteredContacts(contacts.filter(c => c.firstName.toLocaleLowerCase().startsWith(text) || c.lastName.toLocaleLowerCase().startsWith(text)) ?? [])
        }
    }

    let contactsToDisplay = filteredContacts ?? contacts

    return (
        <div className='contact-list-wrapper'>
            <div className='contact-list'>
                <Finder onChange={filterContacts} />
                {contactsToDisplay.map(c => <ContactComponent
                    key={c.userName}
                    contact={c} selected={selectedContact?.userName === c.userName}
                    hasNewMessage={c.userName === 'claudia.wiliams'}
                    onClick={selectUserOnClick}
                />)}
            </div>
        </div>
    )
}