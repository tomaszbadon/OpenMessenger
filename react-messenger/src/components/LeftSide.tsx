import { useNavigate, useParams } from "react-router-dom";
import { Contact } from "../datamodel/Contact";
import { ContactComponent } from "./Contact";
import { useEffect, useState } from "react";
import { useAppDispatch, useAppSelector } from "../auth/types";
import { useGetContactsQuery } from "../service/contactService";
import { setContacts, setSelectedContact } from "../slice/ContactSlice";
import Finder from "./Finder";
import './LeftSide.sass'

export function LeftSide() {

    let { username } = useParams();

    const dispatch = useAppDispatch();

    const navigate = useNavigate();

    const contactsQuery = useGetContactsQuery()

    const { selectedContact, contacts } = useAppSelector(state => state.contactSlice)

    const [filteredContacts, setFilteredContacts] = useState<Contact[] | null>(null)

    useEffect(() => {
        if(typeof contactsQuery.data !== 'undefined') {
            dispatch(setContacts(contactsQuery.data))
        }
        let selectedContact = contacts.find((c) => c.userName === username)
        if(!selectedContact && contacts.length > 0) {
            selectedContact = contacts[0]
        }
        dispatch(setSelectedContact(selectedContact))
    })

    function selectUserOnClick(selectedContact: Contact) {
        navigate("/chat/" + selectedContact.userName);
    }

    const filterContacts = (input: string) => {
        if(input.length === 0) {
          setFilteredContacts(null)
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