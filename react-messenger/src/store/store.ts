import { configureStore, getDefaultMiddleware } from "@reduxjs/toolkit";

import appReducer from '../auth/AuthenticationSlice'
import { loginApi } from "../service/loginService";
import { contactsApi } from "../service/contactService";
import { messagesApi } from "../service/messageService";

export const store = configureStore({
    reducer: {
        [loginApi.reducerPath]: loginApi.reducer,
        [contactsApi.reducerPath]: contactsApi.reducer,
        [messagesApi.reducerPath]: messagesApi.reducer,
        authSlice: appReducer
    },
    middleware: getDefaultMiddleware => getDefaultMiddleware()
                                            .concat(loginApi.middleware)
                                            .concat(contactsApi.middleware)
                                            .concat(messagesApi.middleware)
})

export type RootState = ReturnType<typeof store.getState>

export type AppDispatch = typeof store.dispatch