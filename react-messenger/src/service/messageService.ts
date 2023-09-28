import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { getAccessToken } from '../util/userContextManagement';

export interface FetchContactApiParams {
    userId: String,
    page: number
}

export interface Message {
    id: string,
    message: string,
    recipient: string,
    sender: string,
    sentAt: string,
    read: boolean
}

export interface Messages {
    messages: Message[]
}

export const messagesApi = createApi({
    reducerPath: 'messagesApi',
    baseQuery: fetchBaseQuery({ baseUrl: '/api/users' }),
    endpoints: builder => ({
        getMessages: builder.query<Messages, FetchContactApiParams>({
            query: (params: FetchContactApiParams) => {                
                return ({
                  url: `${params.userId}/messages/${params.page}`,
                  method: 'GET',
                  headers: { Authorization:  `Bearer ${getAccessToken()?.token}`}
                });
              },
        })
    })
})

export const { useGetMessagesQuery } = messagesApi
