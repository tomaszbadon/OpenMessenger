import { useEffect } from "react";
import { Contact } from "../datamodel/Contact";
import { MessagePage, useLazyGetInitialMessagesQuery, useLazyGetMessagesQuery } from "../service/messageService";
import { useAppSelector } from "../auth/types";

interface UseContactResult {
    messages: MessagePage[],
    firstPage: number | undefined
}

const useMessage = (contact: Contact | undefined, page: number | undefined): UseContactResult => {

    const { savedContact, messages, firstPage } = useAppSelector(state => state.chatSlice)

    const lazyGetInitialMessagesQuery = useLazyGetInitialMessagesQuery()

    const lazyGetMessagesQuery = useLazyGetMessagesQuery()

    useEffect(() => {
        if (contact && savedContact !== contact) {
            const [ trigger ] = lazyGetInitialMessagesQuery
            trigger(contact).unwrap()

        } else if (typeof firstPage !== 'undefined' && typeof page !== 'undefined' && contact && savedContact && contact === savedContact && page < firstPage) {
            const [ trigger ] = lazyGetMessagesQuery
            trigger({contact: contact, page: page}).unwrap()
        }
    });

    return { messages: messages, firstPage: firstPage }
}

export default useMessage;