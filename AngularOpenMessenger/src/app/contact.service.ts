import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Contact } from './model/contact';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor() { }

  getContacts() : Observable<Contact[]> {

    var contacts: Contact[] = [
      { id: "1", fullName: "Daniel Silva", status: "Catch every day", avatar: "avatar_1.png", unreadMessages: 0 },
      { id: "2", fullName: "Dominica Rosati", status: "Out of Office today", avatar: "avatar_2.png", unreadMessages: 1 },
      { id: "3", fullName: "Christopher Wolf", status: "Let's have a party", avatar: "avatar_3.png", unreadMessages: 0 },
      { id: "4", fullName: "Claudia Wiliams", status: "Sleep Work Eat Repeat", avatar: "avatar_3.png", unreadMessages: 4 },
      { id: "5", fullName: "Monica Rosatii", status: "Cat...Cat...Cat", avatar: "avatar_5.png", unreadMessages: 0 },

      { id: "1", fullName: "Daniel Silva", status: "Catch every day", avatar: "avatar_1.png", unreadMessages: 0 },
      { id: "2", fullName: "Dominica Rosati", status: "Out of Office today", avatar: "avatar_2.png", unreadMessages: 1 },
      { id: "3", fullName: "Christopher Wolf", status: "Let's have a party", avatar: "avatar_3.png", unreadMessages: 0 },
      { id: "4", fullName: "Claudia Wiliams", status: "Sleep Work Eat Repeat", avatar: "avatar_3.png", unreadMessages: 4 },
      { id: "5", fullName: "Monica Rosatii", status: "Cat...Cat...Cat", avatar: "avatar_5.png", unreadMessages: 0 },


      { id: "1", fullName: "Daniel Silva", status: "Catch every day", avatar: "avatar_1.png", unreadMessages: 0 },
      { id: "2", fullName: "Dominica Rosati", status: "Out of Office today", avatar: "avatar_2.png", unreadMessages: 1 },
      { id: "3", fullName: "Christopher Wolf", status: "Let's have a party", avatar: "avatar_3.png", unreadMessages: 0 },
      { id: "4", fullName: "Claudia Wiliams", status: "Sleep Work Eat Repeat", avatar: "avatar_3.png", unreadMessages: 4 },
      { id: "5", fullName: "Monica Rosatii", status: "Cat...Cat...Cat", avatar: "avatar_5.png", unreadMessages: 0 },


      { id: "1", fullName: "Daniel Silva", status: "Catch every day", avatar: "avatar_1.png", unreadMessages: 0 },
      { id: "2", fullName: "Dominica Rosati", status: "Out of Office today", avatar: "avatar_2.png", unreadMessages: 1 },
      { id: "3", fullName: "Christopher Wolf", status: "Let's have a party", avatar: "avatar_3.png", unreadMessages: 0 },
      { id: "4", fullName: "Claudia Wiliams", status: "Sleep Work Eat Repeat", avatar: "avatar_3.png", unreadMessages: 4 },
      { id: "5", fullName: "Monica Rosatii", status: "Cat...Cat...Cat", avatar: "avatar_5.png", unreadMessages: 0 }
    ];

    return of(contacts);

  }

}
