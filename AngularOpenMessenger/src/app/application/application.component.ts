import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ContactService } from 'src/app/service/contact.service';
import { Contact } from 'src/app/model/contact';
import { AuthService } from '../service/auth.service';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { ActivatedRoute } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { EventQueueService } from '../service/event-queue.service';
import { AppEvent } from '../utils/app-event';
import { AppEventType } from '../utils/app-event-type';

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.sass']
})
export class ApplicationComponent implements OnInit {

  selected: Contact | any

  user: User | any;

  constructor(private eventQueue: EventQueueService, private route: ActivatedRoute, private userService: UserService, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(u => { 
      this.user = u 
    });
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
