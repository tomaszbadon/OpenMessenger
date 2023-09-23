
import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { Credentials, Tokens } from '../auth/types'

export const loginApi = createApi({
    reducerPath: 'loginApi',
    baseQuery: fetchBaseQuery({ baseUrl: '/api' }),
    endpoints: builder => ({
        getTokens: builder.query<Tokens, Credentials>({
            query: (credencials: Credentials) => {                
                const data = new FormData();
                data.append('username', credencials.username)
                data.append('password', credencials.password)
                return ({
                  url: '/login',
                  method: 'POST',
                  body: data
                });
              },
        })
    })
})

export const { useLazyGetTokensQuery } = loginApi
