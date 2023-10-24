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
        
    }
});

export const { setCurrentUser, unSetCurrentUser } = applicationContextSlice.actions

export default applicationContextSlice.reducer