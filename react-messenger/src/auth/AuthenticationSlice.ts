import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { UserContext } from "./types";

export const initialState: UserContext = {
    user: {username: 'default-user-name', isAuthenticated: false},
    tokens: [],
}

export const authenticationSlice = createSlice({
    name: 'authSlice',
    initialState,
    reducers: {
        login: (state, action: PayloadAction<UserContext>) => {
            state.user = action.payload.user
            state.tokens = action.payload.tokens
        },
        logout: (state) => {
            state =  initialState
        }
    }
});

export const { login, logout } = authenticationSlice.actions

export default authenticationSlice.reducer;


