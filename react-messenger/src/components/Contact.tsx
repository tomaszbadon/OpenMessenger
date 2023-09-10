import { Contact } from "../datamodel/Contact";
import './Contact.sass'

export function ContactComponent(properties: any) {

    let contact: Contact = properties.contact
    let selected: Boolean = properties.selected
    let onClickCallback = properties.onClick

    return <div onClick={() => onClickCallback(contact)} className={ selected ? "contact contact-selected" : "contact"} >

        <div className="contact-avatar">
            <img alt="contact's avatar" className="contact-avatar-img" src={'/assets/' + contact.avatar} />
        </div>

        <div className="contact-name">
            <p>{contact.firstName} {contact.lastName}</p>
        </div>

        <div className="contact-status">
            This is a status
        </div>

    </div>
}