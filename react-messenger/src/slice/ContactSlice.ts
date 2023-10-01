import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { Contact, Contacts } from "../datamodel/Contact";

export interface ContactContext {
    contacts: Contact[],
    selectedContact?: Contact
}

export const initialState: ContactContext = { contacts: [] }

export const contactSlice = createSlice({
    name: 'contactSlice',
    initialState,
    reducers: {
        setContacts: (state, action: PayloadAction<Contacts>) => {
            state.contacts = action.payload.contacts
        },
        
        setSelectedContact: (state, action: PayloadAction<Contact | undefined>) => {
            state.selectedContact = action.payload
        }   
    }
});

export const { setContacts, setSelectedContact } = contactSlice.actions

export default contactSlice.reducer