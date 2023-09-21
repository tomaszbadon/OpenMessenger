import { createContext, useContext, useState } from "react"
import { Navigate, Route, Routes } from 'react-router-dom';
import { navigationItems } from "./Navigation";
import Layout from "../pages/_layout";

type Login = (username: string, password: string) => Promise<String>

type Logout = () => void

type TokenType = 'ACCESS_TOKEN' | 'REFRESH_TOKEN'

export interface Token { type: TokenType, token: string }

export interface User { username: string, isAuthenticated: boolean }

export interface UserContext { user: User | null, tokens: Token[] | undefined, login: Login, logout: Logout }

export type UserContextNullable = UserContext | null;

const AuthContext = createContext<UserContextNullable>(null);

export const AuthData = () => useContext(AuthContext);

export const RenderRoutes = () => {

     const userContext: UserContextNullable = AuthData();

     return (
          <Routes>
               <Route path="/" element={<Layout />}>
                    {navigationItems.map((r, i) => {
                         if (r.isPrivate && userContext?.user?.isAuthenticated) {
                              return <Route key={i} path={r.path} element={r.element} />
                         } else if (!r.isPrivate) {
                              return <Route key={i} path={r.path} element={r.element} />
                         } else return false
                    })}
                    <Route path='*' element={<Navigate to='/login' />} />
               </Route>
          </Routes>
     )
}


export const AuthWrapper = () => {

     const [user, setUser] = useState<User>({ username: "", isAuthenticated: false });

     const [token, setToken] = useState<[Token]>();

     const login = async (username: string, password: string) => {
          const data = new FormData();
          data.append('username', 'dominica.rosatti')
          data.append('password', 'my_password')
          try {
               const response = await fetch('/api/login', { method: 'POST', body: data });
               const json = await response.json();
               console.log('Content: ' + JSON.stringify(json));
               setUser({ username: username, isAuthenticated: true });
               const json_1 = undefined;
               return 'success';
          } catch (error) {
               console.error(error);
               return 'failure';
          }
     }

     const logout = () => {
          setUser({ ...user, isAuthenticated: false })
     }

     return (
          <AuthContext.Provider value={{ user: user, tokens: token, login: login, logout: logout }}>
               <RenderRoutes />
          </AuthContext.Provider>);

}