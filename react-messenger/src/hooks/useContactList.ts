import { useParams } from "react-router-dom";
import { Contact } from "../datamodel/Contact";
import { useGetContactsQuery } from "../service/contactService";
import { useEffect, useState } from "react";

interface ContactListResult {
    contacts: Contact[],
    selectedContact: Contact | undefined
}

const useContactList = (): ContactListResult => {

    const [contacts, setContacts] = useState<Contact[]>([])

    const [selectedContact, setSelectedContact] = useState<Contact | undefined>();

    const { data } = useGetContactsQuery()

    let { username } = useParams();

    useEffect(() => {
        if (data && data.contacts.length > 0) {
            setContacts(data.contacts);
            let newSelection = data.contacts.find(contact => contact.userName === username) ?? data.contacts[0]
            if (newSelection != selectedContact) {
                setSelectedContact(newSelection)
            }
        }
    });

    return { contacts, selectedContact }
}

export default useContactList;
