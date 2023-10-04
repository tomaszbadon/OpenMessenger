
import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { Contacts } from '../datamodel/Contact';
import { getAccessToken } from '../util/userContextManagement';
import { baseQueryWithReauth } from './base';

export const contactsApi = createApi({
    reducerPath: 'contactsApi',
    baseQuery: baseQueryWithReauth,
    endpoints: builder => ({
        getContacts: builder.query<Contacts, void>({
            query: () => {                
                return ({
                  url: '/users/current/contacts',
                  method: 'GET',
                  headers: { Authorization:  `Bearer ${getAccessToken()?.token}`}
                });
              },
        })
    })
})

export const { useGetContactsQuery } = contactsApi
