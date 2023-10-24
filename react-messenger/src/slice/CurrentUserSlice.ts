import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { CurrentUser } from "../datamodel/CurrentUser";
import { MessagePage } from "../service/messageService";
import { Contact } from "../datamodel/Contact";

export interface MessagesPerContact {
    messages: MessagePage[] | undefined
    theMinimalPage: number | undefined
}

export interface ApplicationContext {
    currentUser: CurrentUser | undefined
    selectedContact: Contact | undefined
    messages: Map<string, MessagesPerContact>
}

const initialState: ApplicationContext = { currentUser: undefined, selectedContact: undefined, messages: new Map() }

export const applicationContextSlice = createSlice({
    name: 'applicationContextSlice',
    initialState,
    reducers: {
        
        setCurrentUser: (state, action: PayloadAction<CurrentUser>) => {
            state.currentUser = action.payload
        },

        unSetCurrentUser: (state) => {
            state.currentUser = undefined
        },

        setSelectedUser: (state, action: PayloadAction<Contact | undefined>) => {
            state.selectedContact = action.payload
        },

        setInitialMessages: (state, action: PayloadAction<MessagePage[]>) => {
            if(state.selectedContact) {
                let messages: MessagesPerContact = { messages: action.payload, theMinimalPage: action.payload.length > 0 ? Math.min(...action.payload.map(m => m.page)) : 0 }
                state.messages.set(state.selectedContact.id, messages);
            }
        },

        addMessagePage: (state, action: PayloadAction<MessagePage>) => {
            if(state.selectedContact) {
                let messagesPerUser = state.messages.get(state.selectedContact.id);
                if(messagesPerUser) {
                    messagesPerUser.messages?.push(action.payload)
                    messagesPerUser.theMinimalPage = action.payload.page
                }
            }
        }
    }
});

export const { setCurrentUser, unSetCurrentUser, setSelectedUser, setInitialMessages, addMessagePage } = applicationContextSlice.actions

export default applicationContextSlice.reducer