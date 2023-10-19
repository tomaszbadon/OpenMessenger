import { configureStore } from "@reduxjs/toolkit";

import currentUserReducer from '../slice/CurrentUserSlice'
import conversationReducer from '../slice/ConversationSlice'
import { contactsApi } from "../service/contactService";
import { messagesApi } from "../service/messageService";
import { currentUserApi } from "../service/currentUserService";
import { loginApi } from "../service/loginService";

export const store = configureStore({
    reducer: {
        [loginApi.reducerPath]: loginApi.reducer,
        [currentUserApi.reducerPath]: currentUserApi.reducer,
        [contactsApi.reducerPath]: contactsApi.reducer,
        [messagesApi.reducerPath]: messagesApi.reducer,
        currentUserSlice: currentUserReducer,
        conversationSlice: conversationReducer
    },
    middleware: getDefaultMiddleware => getDefaultMiddleware()
                                            .concat(loginApi.middleware)
                                            .concat(currentUserApi.middleware)
                                            .concat(contactsApi.middleware)
                                            .concat(messagesApi.middleware)
})

export type RootState = ReturnType<typeof store.getState>

export type AppDispatch = typeof store.dispatch