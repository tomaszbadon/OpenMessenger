import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { Credentials, Tokens } from '../auth/types'

export const loginApi = createApi({
    reducerPath: 'loginApi',
    baseQuery: fetchBaseQuery({ baseUrl: '/api' }),
    endpoints: builder => ({
        getTokens: builder.query<Tokens, Credentials>({
            query: (credentials: Credentials) => {                
                const data = new FormData();
                data.append('username', credentials.username)
                data.append('password', credentials.password)
                return ({
                  url: '/auth/login',
                  method: 'POST',
                  body: data
                });
              },
        })
    })
})



export const { useLazyGetTokensQuery } = loginApi