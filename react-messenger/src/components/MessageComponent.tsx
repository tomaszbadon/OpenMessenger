import { Contact } from "../datamodel/Contact"
import { Message } from "../service/messageService"

interface MessageProp {
    message: Message,
    selectedContact: Contact | undefined
}

export const MessageComponent = (prop: MessageProp) => {

    const { message, selectedContact } = prop

    return (
        <div>
            <div className={message.sender === selectedContact?.id ? 'single-message single-message-left' : 'single-message single-message-right'}>
                <p className="message">{message.message}</p>
                <p className={message.sender === selectedContact?.id ? 'message-metadata message-metadata-left' : 'message-metadata message-metadata-right'}>{message.sentAt.split(' ')[1]}</p>
            </div>
        </div>
    )
}