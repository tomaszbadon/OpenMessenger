
export type Contacts = { contacts: Contact[] }

export interface Contact {
    userName: string,
    firstName: string,
    lastName: string,
    avatar?: string
}