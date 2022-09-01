import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Route } from '@angular/router';
import { Contact } from '../model/contact';
import { ContactService } from '../service/contact.service';

@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.sass']
})
export class ContactListComponent implements OnInit {

  contacts: Contact[] = [];
  
  selected: Contact | any;

  @Output() onContactSelectedEmitter: EventEmitter<Contact> = new EventEmitter();

  constructor(private contactService: ContactService, private route: ActivatedRoute) { }

  ngOnInit(): void { 
    this.contactService.getContacts().subscribe(contacts => { 
      this.contacts = contacts
      this.selectDefaultContact();
    });
  }

  onContactSelected(contact: Contact) {
    this.selected = contact;
    this.onContactSelectedEmitter.emit(contact);
  }

  private selectDefaultContact() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    let contact = this.getSelected(id);
    if(contact != null) {
      this.onContactSelected(contact);
    }
  }

  private getSelected(id: number) : Contact | null {
    for(let m of this.contacts) {
      if(m.id === id) {
        return m;
      }
    }
    return null;
  }

}
