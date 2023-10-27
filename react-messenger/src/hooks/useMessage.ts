import { useEffect, useState } from "react";
import { Contact } from "../datamodel/Contact";
import { MessagePage, useLazyGetInitialMessagesQuery, useLazyGetMessagesQuery } from "../service/messageService";
import { useAppSelector } from "../auth/types";

interface UseContactResult {
    messages: MessagePage[],
    firstPage: number | undefined
    isLoading: boolean
}

const useMessage = (contact: Contact | undefined, page: number | undefined): UseContactResult => {

    const [isLoading, setIsLoading] = useState<boolean>(false);

    const { savedContact, messages, firstPage } = useAppSelector(state => state.chatSlice)

    const lazyGetInitialMessagesQuery = useLazyGetInitialMessagesQuery()

    const lazyGetMessagesQuery = useLazyGetMessagesQuery()

    async function refresh() {
        if (contact && savedContact !== contact) {
            setIsLoading(true)
            const [ trigger ] = lazyGetInitialMessagesQuery
            await trigger(contact).unwrap()
            setIsLoading(false)
        } else if (typeof firstPage !== 'undefined' && typeof page !== 'undefined' && contact && savedContact && contact === savedContact && page < firstPage) {
            setIsLoading(true)
            const [ trigger ] = lazyGetMessagesQuery
            await trigger({contact: contact, page: page}).unwrap()
            setIsLoading(false)
        }
    }

    useEffect(() => {
        refresh()
    }, [contact, page]);

    return { isLoading: isLoading, messages: messages, firstPage: firstPage }
}

export default useMessage;