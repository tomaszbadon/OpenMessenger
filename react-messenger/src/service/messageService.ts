import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { getAccessToken } from '../util/userContextManagement';

export interface MessageApiParams {
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

export interface InitialMessagePagesPayload {
    pagesToLoad: number[]
}

export const messagesApi = createApi({
    reducerPath: 'messagesApi',
    baseQuery: fetchBaseQuery({ baseUrl: '/api/users' }),
    endpoints: builder => ({
        getMessages: builder.query<Messages, MessageApiParams>({
            query: (params: MessageApiParams) => {
                return ({
                    url: `${params.userId}/messages/${params.page}`,
                    method: 'GET',
                    headers: { Authorization:  `Bearer ${getAccessToken()?.token}`}
                });
              }
        }),
        getLatestMessages: builder.query<InitialMessagePagesPayload, string>({
            query: (userId: string) => {                
                return ({
                  url: `${userId}/messages/latest`,
                  method: 'GET',
                  headers: { Authorization:  `Bearer ${getAccessToken()?.token}`}
                });
              }
        })
    })
})

export const { useGetMessagesQuery, useGetLatestMessagesQuery } = messagesApi
