import { Component, OnInit } from '@angular/core';
import { ContactService } from 'src/app/contact.service';
import { Contact } from 'src/app/model/contact';

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.sass']
})
export class ApplicationComponent implements OnInit {

  contacts: Contact[] = []

  selected: Contact | any

  constructor(private contactService: ContactService) { }

  ngOnInit(): void {
    this.contactService.getContacts().subscribe(contacts => this.contacts = contacts);
    this.selected = this.contacts.length > 2 ? this.contacts[1] : null;
  }

  changeSelectionOnClick(contact: Contact) {
    this.selected = contact;
  }

}
