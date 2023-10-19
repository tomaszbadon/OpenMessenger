
import { MessagePage } from '../service/messageService'
import { PayloadAction, createSlice } from "@reduxjs/toolkit";

interface MessagePageState { messagePages: MessagePage[] }

const initialState: MessagePageState = { messagePages: [] }

export const conversationSlice = createSlice({
    name: 'conversationSlice',
    initialState,
    reducers: {
        reset: (state) => {
            state.messagePages = []
        },
        setMessagePages: (state, action: PayloadAction<MessagePage[]>) => {
            state.messagePages = action.payload
        },
        setMessagePage: (state, action: PayloadAction<MessagePage>) => {
            state.messagePages.push(action.payload)
        }
    }
});

export const { setMessagePages, setMessagePage, reset } = conversationSlice.actions

export default conversationSlice.reducer