import { AfterViewInit, Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Route } from '@angular/router';
import { Contact } from '../../model/contact';
import { EventQueueService } from '../../service/event-queue.service';
import { AppEventType } from '../../utils/app-event-type';
import { Notification } from 'src/app/model/notification';

@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.sass']
})
export class ContactListComponent implements OnInit {

  @Input() contacts: Contact[] = [];
  @Output() onContactSelectedEmitter: EventEmitter<Contact> = new EventEmitter();
  selected: Contact | null = null;

  constructor(private route: ActivatedRoute,
              private eventQueueService: EventQueueService) { }

  ngOnInit(): void { 
    this.contacts.forEach(contact => contact.unreadMessages = 0);
    this.selectDefaultContact();
    this.eventQueueService.on(AppEventType.NewMessageNotifcation).subscribe(event => {
      this.handleNotification(event.payload);
    });
    this.eventQueueService.on(AppEventType.ClearNotifications).subscribe(event => {
      this.clearNotifications(event.payload);
    });
  }

  onContactSelected(contact: Contact) {
    this.selected = contact;
    this.onContactSelectedEmitter.emit(contact);
  }

  private handleNotification(notification: Notification) {
    let contact = this.contacts.find(c => c.id === notification.sender);
    if(contact != null) {
      if(isNaN(contact.unreadMessages)) {
        contact.unreadMessages = 1;
      } else {
        contact.unreadMessages++;
      }
    }
  }

  private clearNotifications(sender: number) {
    let contact = this.contacts.find(c => c.id === sender);
    if(contact != null) {
      contact.unreadMessages = 0;
    }
  }

  private selectDefaultContact() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    let contact;
    if(id === 0) {
      contact = this.contacts.find(x=>x!==undefined);
    } else {
      contact = this.getSelected(id);
    }
    if(contact != undefined) {
      this.onContactSelected(contact);
    }
  }

  private getSelected(id: number) : Contact | undefined {
    return this.contacts.find(contact => contact.id == id);
  }

}