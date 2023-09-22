import { configureStore } from "@reduxjs/toolkit";

import appReducer from '../auth/AuthenticationSlice'

export const store = configureStore({
    reducer: {
        authSlice: appReducer
    }
})

export type RootState = ReturnType<typeof store.getState>

export type AppDispatch = typeof store.dispatch