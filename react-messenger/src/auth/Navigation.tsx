
import { ReactElement } from "react";
import Chat from "../pages/Chat";
import Login from "../pages/Login";

export interface NavigationItem {
    path: string,
    name: string,
    element: ReactElement,
    isPrivate: boolean
}

export const navigationItems: NavigationItem[]= [

    { path:"chat/:username", name:"Chat with another user", element: <Chat />, isPrivate: true },

    { path:"chat", name:"Default Chat view", element: <Chat />, isPrivate: true },

    { path:"login", name:"Login Page", element: <Login />, isPrivate: false }

]