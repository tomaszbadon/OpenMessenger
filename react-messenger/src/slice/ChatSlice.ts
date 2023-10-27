import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { Contact } from "../datamodel/Contact";
import { MessagePage } from "../service/messageService";

interface ChatState {
    savedContact: Contact | undefined
    firstPage: number | undefined
    messages: MessagePage[]
}

interface InitialChatState {
    contact: Contact,
    messages: MessagePage[]
}

interface AdditionalMessages {
    messages: MessagePage
    page: number
}

const initialState: ChatState = { savedContact: undefined, firstPage: undefined, messages: [] }

export const chatSlice = createSlice({
    name: 'chatSlice',
    initialState,
    reducers: {

        setInitialEmptyState: (state, action: PayloadAction<void>) => {
            state.firstPage = undefined
            state.messages = []
            state.savedContact = undefined
        },
        
        setInitialChatState: (state, action: PayloadAction<InitialChatState>) => {
            state.savedContact = action.payload.contact
            state.messages = action.payload.messages
            state.firstPage = action.payload.messages.length > 0 ? Math.min(...action.payload.messages.map(m => m.page)) : undefined
        },

        addMessagesToPage: (state, action: PayloadAction<AdditionalMessages>) => {
            state.messages = state.messages.concat(action.payload.messages)
            state.firstPage = action.payload.page
        }

    }
})

export const { setInitialEmptyState, setInitialChatState, addMessagesToPage } = chatSlice.actions

export default chatSlice.reducer