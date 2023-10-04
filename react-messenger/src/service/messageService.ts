import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import { getAccessToken } from '../util/userContextManagement';
import { baseQueryWithReauth } from './base';

export interface MessageApiParams {
    userId: String | undefined,
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
    baseQuery: baseQueryWithReauth,
    endpoints: builder => ({
        getMessages: builder.query<Messages, MessageApiParams>({
            query: (params: MessageApiParams) => {
                return ({
                    url: `/users/${params.userId}/messages/${params.page}`,
                    method: 'GET',
                    headers: { Authorization:  `Bearer ${getAccessToken()?.token}`}
                });
              }
        })
    })
})

export const { useGetMessagesQuery } = messagesApi
