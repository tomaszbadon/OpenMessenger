import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { CurrentUser } from "../datamodel/CurrentUser";

const initialState: CurrentUser = { }

export const currentUserSlice = createSlice({
    name: 'currentUserSlice',
    initialState,
    reducers: {
        setCurrentUser: (state, action: PayloadAction<CurrentUser>) => {
            state.id = action.payload.id
            state.email = action.payload.email
            state.userName = action.payload.userName
            state.firstName = action.payload.firstName
            state.lastName = action.payload.lastName
            state.status = action.payload.status
        },
        unSetCurrentUser: (state) => {
            state =  initialState
        }
    }
});

export const { setCurrentUser, unSetCurrentUser } = currentUserSlice.actions

export default currentUserSlice.reducer