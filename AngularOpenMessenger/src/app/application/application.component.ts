import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ContactService } from 'src/app/service/contact.service';
import { Contact } from 'src/app/model/contact';
import { AuthService } from '../service/auth.service';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { ActivatedRoute } from '@angular/router';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.sass']
})
export class ApplicationComponent implements OnInit {

  contacts: Contact[] = []

  selected: Contact | any

  user: User | any;

  constructor(private route: ActivatedRoute, private userService: UserService, private contactService: ContactService, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {

    setTimeout(() => {
      let div = document.getElementById('right');
      if(div != null) {
        div.scrollTop = div.scrollHeight;
      }

      div?.addEventListener('scroll', (e: Event) => {
        console.log('Scroll Event');
        if(e.target != null && e.target instanceof HTMLElement) {
          if(e.target.scrollTop == 0) {
            alert("I am on the top");
          }
        }
      });
    }, 1000);


    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.userService.getCurrentUser().subscribe(u => { 
      this.user = u 
    });

    this.contactService.getContacts().subscribe(contacts => { 
      this.contacts = contacts 
      this.selected = this.getSelected(id);
    });

  }

  getSelected(id: number) {
    for(let m of this.contacts) {
      if(m.id === id) {
        return m;
      }
    }
    return null;
  }

  changeSelectionOnClick(contact: Contact) {
    this.selected = contact;
    this.router.navigate(['/application/' + contact.id]);
  }

  logOut() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
