import { Component, Input, OnInit } from '@angular/core';
import { MessagesGroup } from 'src/app/model/messages-group';

@Component({
  selector: 'app-message-group',
  templateUrl: './message-group.component.html',
  styleUrls: ['./message-group.component.sass']
})
export class MessageGroupComponent implements OnInit {

  @Input() messages: MessagesGroup | null = null;

  sender = 6;

  constructor() { }

  ngOnInit(): void { }

}
