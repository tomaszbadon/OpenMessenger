import { TokenTypeEnum, UserContext } from "../auth/types";

const userContextKey = 'user-context'

export const saveUserContext = (userContext: UserContext) => {
    localStorage.setItem(userContextKey, JSON.stringify(userContext))
}

export const getUserContext = () => {
    return (JSON.parse(localStorage.getItem(userContextKey) ?? '{}') as UserContext)
}

export const getAccessToken = () => {
    return getUserContext().tokens.find(t => t.type === TokenTypeEnum.ACCESS_TOKEN)
}