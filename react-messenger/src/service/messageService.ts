import { FetchBaseQueryError, createApi } from '@reduxjs/toolkit/query/react'
import { baseQueryWithReauth } from './base';
import { QueryReturnValue } from '@reduxjs/toolkit/dist/query/baseQueryTypes';
import { addMessagePage, setInitialMessages } from '../slice/CurrentUserSlice';

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
                const result = await baseQueryWithReauth(`/users/${params.userId}/messages/${params.page}`, api, extraOptions)
                if(result.error) {
                    return { error: result.error }
                } else {
                    api.dispatch(addMessagePage(result.data as MessagePage))
                    return {data: result.data as MessagePage }
                }
            }
        }),

        getInitialMessages: builder.query<MessagePage[], string | undefined>({
            queryFn: async (userId: string | undefined, api, extraOptions) => {
                // const result = await baseQueryWithReauth(`/users/${userId}/messages/latest`, api, extraOptions);
                // const initialPages = result.data as InitialMessagePagesPayload
                // if (result.error) {
                //     return { error: result.error }
                // }

                const initialPages = { pagesToLoad: [ 4, 5] }

                const resultWithMessages = await Promise.all(initialPages.pagesToLoad.map(
                    page => baseQueryWithReauth(`/users/${userId}/messages/${page}`, api, extraOptions)
                )) as QueryReturnValue<MessagePage, FetchBaseQueryError, {}>[]

                let arrayOfMessages: MessagePage[] = []

                resultWithMessages.forEach((returnValue) => {
                    if (returnValue.error) {
                        return { error: returnValue.error }
                    }
                    arrayOfMessages.push(returnValue.data)
                })

                api.dispatch(setInitialMessages(arrayOfMessages))

                console.log("Executing Query");
                return { data: arrayOfMessages }
            }
        })
    })
})

export const { useLazyGetMessagesQuery, useGetInitialMessagesQuery, useLazyGetInitialMessagesQuery } = messagesApi