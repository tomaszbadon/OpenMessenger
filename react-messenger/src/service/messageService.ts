import { FetchBaseQueryError, createApi } from '@reduxjs/toolkit/query/react'
import { baseQueryWithReauth } from './base';
import { QueryReturnValue } from '@reduxjs/toolkit/dist/query/baseQueryTypes';
import { Contact } from '../datamodel/Contact';
import { addMessagesToPage, setInitialChatState, setInitialEmptyState } from '../slice/ChatSlice';

const timeout = 250

export interface MessageApiParams {
    contact: Contact
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

export interface MessagePage {
    messages: Message[],
    page: number
}

export interface InitialMessagePagesPayload {
    pagesToLoad: number[]
}

export const messagesApi = createApi({
    reducerPath: 'messagesApi',
    baseQuery: baseQueryWithReauth,
    endpoints: builder => ({

        getMessages: builder.query<MessagePage, MessageApiParams>({
            queryFn: async (params: MessageApiParams, api, extraOptions) => {
                await new Promise(f => setTimeout(f, timeout));
                const result = await baseQueryWithReauth(`/users/${params.contact.id}/messages/${params.page}`, api, extraOptions)
                if(result.error) {
                    return { error: result.error }
                } else {
                    api.dispatch(addMessagesToPage({ messages: result.data as MessagePage, page: params.page }))
                    return {data: result.data as MessagePage }
                }
            }
        }),

        getInitialMessages: builder.query<MessagePage[], Contact>({
            queryFn: async (contact: Contact, api, extraOptions) => {
                api.dispatch(setInitialEmptyState())

                await new Promise(f => setTimeout(f, timeout));
                const result = await baseQueryWithReauth(`/users/${contact?.id}/messages/latest`, api, extraOptions);
                const initialPages = result.data as InitialMessagePagesPayload
                if (result.error) {
                    return { error: result.error }
                }

                const resultWithMessages = await Promise.all(initialPages.pagesToLoad.map(
                    page => baseQueryWithReauth(`/users/${contact?.id}/messages/${page}`, api, extraOptions)
                )) as QueryReturnValue<MessagePage, FetchBaseQueryError, {}>[]

                let arrayOfMessages: MessagePage[] = []

                resultWithMessages.forEach((returnValue) => {
                    if (returnValue.error) {
                        return { error: returnValue.error }
                    }
                    arrayOfMessages.push(returnValue.data)
                })

                api.dispatch(setInitialChatState({ contact: contact, messages: arrayOfMessages }))
                return { data: arrayOfMessages }
            }
        })
    })
})

export const { useLazyGetMessagesQuery, useLazyGetInitialMessagesQuery } = messagesApi