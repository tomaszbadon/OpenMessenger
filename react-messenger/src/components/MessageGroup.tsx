import { Contact } from "../datamodel/Contact";
import { Message } from "../service/messageService";
import { GroupOfMessages } from "../util/messagesUtil";
import { MessageComponent } from "./MessageComponent";

interface MessageGroupProp {
    group: GroupOfMessages
    selectedContact: Contact | undefined
}

export const MessageGroup = (prop: MessageGroupProp) => {

    const { group, selectedContact } = prop

    return (
        <div className={group.sender === selectedContact?.id ? 'message-group-wrapper left' : 'message-group-wrapper right'}>
            {
                group.sender === selectedContact?.id &&
                <div className="message-avatar-container">
                    <img alt="contact's avatar" className="contact-avatar-img-min" src={`/api/users/${group.sender}/avatar`} />
                </div>
            }
            <div className="message-group">
                {group.messages.map((message: Message) => (
                    <MessageComponent key={message.id} message={message} selectedContact={selectedContact} />
                ))}
            </div>
            {group.sender !== selectedContact?.id &&
                <div className="message-avatar-container">
                    <img alt="contact's avatar" className="contact-avatar-img-min" src={`/api/users/${group.sender}/avatar`} />
                </div>}
        </div>
    )

}