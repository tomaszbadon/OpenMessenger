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

    async function refresh() {
        if (contact && savedContact !== contact) {
            const [ trigger ] = lazyGetInitialMessagesQuery
            await trigger(contact).unwrap()

        } else if (firstPage && page && contact && savedContact && contact == savedContact && page < firstPage) {
            const [ trigger ] = lazyGetMessagesQuery
            await trigger({contact: contact, page: page}).unwrap()
        }
    }

    useEffect(() => {
        refresh()
    });

    return { messages: messages, firstPage: firstPage }
}

export default useMessage;