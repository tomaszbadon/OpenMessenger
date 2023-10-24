import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux"
import type { RootState, AppDispatch } from '../store/store'

export enum TokenTypeEnum { 
    ACCESS_TOKEN = 'ACCESS_TOKEN',
    REFRESH_TOKEN = 'REFRESH_TOKEN'
}

export type TokenType = TokenTypeEnum.ACCESS_TOKEN | TokenTypeEnum.REFRESH_TOKEN

export interface Token { type: TokenType, token: string }

export type Tokens = { tokens: Token[] }

export interface User { username: string, isAuthenticated: boolean }

export interface UserContext { user: User | null, tokens: Token[] }

export interface Credentials { username: string, password: string }

export const useAppDispatch = () => useDispatch<AppDispatch>()

export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector