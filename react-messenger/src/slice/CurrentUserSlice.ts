import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { CurrentUser } from "../datamodel/CurrentUser";

export interface ApplicationContext {
    currentUser: CurrentUser | undefined
}

const initialState: ApplicationContext = { currentUser: undefined }

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