import { Component, Input, OnInit } from '@angular/core';
import { Contact } from 'src/app/model/contact';

@Component({
  selector: 'app-message-box',
  templateUrl: './message-box.component.html',
  styleUrls: ['./message-box.component.sass']
})
export class MessageBoxComponent implements OnInit {

  @Input() contact: Contact | null = null

  constructor() { }

  ngOnInit(): void { 

    //var element = document.getElementById('cont')!;
    //element.scrollTop = element.scrollHeight;

  }

}
