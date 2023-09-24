
import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { Contacts } from '../datamodel/Contact';
import { Token } from '../auth/types';

export const contactsApi = createApi({
    reducerPath: 'contactsApi',
    baseQuery: fetchBaseQuery({ baseUrl: '/api/users/current' }),
    endpoints: builder => ({
        getContacts: builder.query<Contacts, Token | undefined>({
            query: (accessToken: Token | undefined) => {                
                return ({
                  url: '/contacts',
                  method: 'GET',
                  headers: { Authorization:  `Bearer ${accessToken?.token}`}
                });
              },
        })
    })
})

export const { useGetContactsQuery } = contactsApi
