import { useEffect, useState } from "react";
import { Contact } from "../datamodel/Contact";
import { MessagePage, useLazyGetInitialMessagesQuery, useLazyGetMessagesQuery } from "../service/messageService";

interface UseContactResult {
    messages: MessagePage[],
    firstPage: number | undefined
}

const useMessage = (contact: Contact | undefined, page: number | undefined): UseContactResult => {

    const [savedContact, setSavedContact] = useState<Contact | undefined>(undefined);

    const [messages, setMessages] = useState<MessagePage[]>([]);

    const [firstPage, setFirstPage] = useState<number | undefined>(undefined);

    const lazyGetInitialMessagesQuery = useLazyGetInitialMessagesQuery()

    const lazyGetMessagesQuery = useLazyGetMessagesQuery()

    async function refresh() {
        if (contact && savedContact !== contact) {
            const [ trigger ] = lazyGetInitialMessagesQuery
            const data = await trigger(contact.id).unwrap()
            setSavedContact(contact)
            setMessages(data)
            setFirstPage(Math.min(...data.map(m => m.page)))
        } else if (firstPage && page && contact && savedContact && contact == savedContact && page < firstPage) {
            const [ trigger ] = lazyGetMessagesQuery
            const data = await trigger({userId: contact.id, page: page}).unwrap()
            setMessages(messages.concat(data))
            setFirstPage(page)
        }
    }

    useEffect(() => {
        refresh()
    });

    return { messages: messages, firstPage: firstPage }
}

export default useMessage;