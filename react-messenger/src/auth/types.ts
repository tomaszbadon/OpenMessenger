import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux"
import type { RootState, AppDispatch } from '../store/store'

export type TokenType = 'ACCESS_TOKEN' | 'REFRESH_TOKEN'

export interface Token { type: TokenType, token: string }

export interface User { username: string, isAuthenticated: boolean }

export interface UserContext { user: User | null, tokens: Token[] }

export const useAppDispatch = () => useDispatch<AppDispatch>()

export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector
