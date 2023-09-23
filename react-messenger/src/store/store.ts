import { configureStore, getDefaultMiddleware } from "@reduxjs/toolkit";

import appReducer from '../auth/AuthenticationSlice'
import { loginApi } from "../service/loginService";

export const store = configureStore({
    reducer: {
        [loginApi.reducerPath]: loginApi.reducer,
        authSlice: appReducer
    },
    middleware: getDefaultMiddleware => getDefaultMiddleware().concat(loginApi.middleware)
})

export type RootState = ReturnType<typeof store.getState>

export type AppDispatch = typeof store.dispatch