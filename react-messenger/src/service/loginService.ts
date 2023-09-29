import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { Credentials, Tokens } from '../auth/types'
import { CurrentUser } from '../datamodel/CurrentUser';
import { getAccessToken } from '../util/userContextManagement';

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
        }),

        getCurrentUser: builder.query<CurrentUser, void>({
          query: () => { 
            return({
              headers: { Authorization:  `Bearer ${getAccessToken()?.token}`},
              url: '/users/current',
              method: 'GET'
            })
          }
        })
    })
})

export const { useLazyGetTokensQuery, useGetCurrentUserQuery } = loginApi