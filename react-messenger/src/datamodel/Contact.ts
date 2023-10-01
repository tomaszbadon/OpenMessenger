
export type Contacts = { contacts: Contact[] }

export interface Contact {
    id: string
    userName: string,
    firstName: string,
    lastName: string,
    status: string,
    avatar?: string
}