import { Contact } from "../datamodel/Contact";
import './Contact.sass'

export function ContactComponent(properties: any) {

    let contact = properties.contact as Contact
    let selected = properties.selected as Boolean
    let onClickCallback = properties.onClick as ((contact: Contact) => void);

    return <div onClick={() => onClickCallback(contact)} className={ selected ? "contact contact-selected" : "contact contact-unselected"} >

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