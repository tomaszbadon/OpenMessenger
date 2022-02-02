import { Component, Input, OnInit } from '@angular/core';
import { Contact } from 'src/app/model/contact';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.sass']
})
export class ContactComponent implements OnInit {

  @Input() contact: Contact | any;

  @Input() selected: boolean | any;

  constructor() { }

  ngOnInit(): void { }

}
