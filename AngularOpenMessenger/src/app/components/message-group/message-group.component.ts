import { Component, Input, OnInit } from '@angular/core';
import { Contact } from 'src/app/model/contact';
import { MessagesGroup } from 'src/app/model/messages-group';
import { User } from 'src/app/model/user';

@Component({
  selector: 'app-message-group',
  templateUrl: './message-group.component.html',
  styleUrls: ['./message-group.component.sass']
})
export class MessageGroupComponent implements OnInit {

  @Input() group: MessagesGroup | any = null;
  @Input() recipient: Contact | any;
  @Input() sender: User | any;

  constructor() { }

  ngOnInit(): void { }

}
