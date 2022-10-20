import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Contact } from 'src/app/model/contact';
import { AuthService } from '../service/auth.service';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { EventQueueService } from '../service/event-queue.service';
import { AppEvent } from '../utils/app-event';
import { AppEventType } from '../utils/app-event-type';
import { ContactService } from '../service/contact.service';


@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.sass']
})
export class ApplicationComponent implements OnInit {

  selected: Contact | null = null;
  user: User | null = null;
  contacts: Contact[] = []

  constructor(private eventQueue: EventQueueService,
              private userService: UserService,
              private contactService: ContactService,
              private authService: AuthService, 
              private router: Router) { }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(u => this.user = u);
    this.contactService.getContacts().subscribe(c => this.contacts = c);
  }

  contactWasSelected(contact: Contact) {
    this.selected = contact;
    this.router.navigate(['/application/' + contact.id]);
  }

  logOut() {
    this.eventQueue.dispatch(new AppEvent(AppEventType.LogOut, null))
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
