import { Contact } from "../datamodel/Contact";
import './Contact.sass'

interface ContactProps {
    contact: Contact,
    selected: Boolean,
    onClick: (contact: Contact) => void,
    hasNewMessage: boolean
}

export function ContactComponent(props: ContactProps) {

    function evaluateStyle(props: ContactProps) {
        var styles = 'contact ';
        if(props.selected) {
            styles += 'contact-selected'
        } else {
            styles += 'contact-unselected'
        } 

        if(props.hasNewMessage) {
            styles += ' contact-with-unread-message'
        }

        return styles
    }

    return <div onClick={() => props.onClick(props.contact)} className={evaluateStyle(props)} >
        <div className="contact-avatar">
            <img alt="contact's avatar" className="contact-avatar-img" src={`/api/users/${props.contact.id}/avatar`} />
        </div>
        <div className="contact-name">
            <p>{props.contact.firstName} {props.contact.lastName}</p>
        </div>
        <div className="contact-status">
            This is a status
        </div>
    </div>
}