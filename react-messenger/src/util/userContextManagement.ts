import { TokenTypeEnum, UserContext, Token, Tokens } from "../auth/types";

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

export const updateAccessToken = (accessToken: Token) => {
    let tokens: Tokens = {tokens: [accessToken] }
    let refreshToken = getRefreshToken()
    if(typeof refreshToken !== 'undefined') {
        tokens.tokens.push(refreshToken)
    }
    let userContext = getUserContext()
    userContext.tokens = tokens.tokens
    saveUserContext(userContext)
}

export const getRefreshToken = () => {
    return getUserContext().tokens.find(t => t.type === TokenTypeEnum.REFRESH_TOKEN)
}

export const removeUserContext = () => {
    localStorage.removeItem(userContextKey);
}