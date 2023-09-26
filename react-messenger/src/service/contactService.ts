
import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { Contacts } from '../datamodel/Contact';
import { getAccessToken } from '../util/userContextManagement';

export const contactsApi = createApi({
    reducerPath: 'contactsApi',
    baseQuery: fetchBaseQuery({ baseUrl: '/api/users/current' }),
    endpoints: builder => ({
        getContacts: builder.query<Contacts, void>({
            query: () => {                
                return ({
                  url: '/contacts',
                  method: 'GET',
                  headers: { Authorization:  `Bearer ${getAccessToken()?.token}`}
                });
              },
        })
    })
})

export const { useGetContactsQuery } = contactsApi
