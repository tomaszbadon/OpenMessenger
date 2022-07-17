import { Component, Input, OnInit, Renderer2 } from '@angular/core';
import { Contact } from 'src/app/model/contact';

@Component({
  selector: 'app-message-box',
  templateUrl: './message-box.component.html',
  styleUrls: ['./message-box.component.sass']
})
export class MessageBoxComponent implements OnInit {

  @Input() contact: Contact | null = null

  constructor(private renderer2: Renderer2) { }

  ngOnInit(): void { 

    this.renderer2.listen('container', 'scroll', (e) => {
      console.log(this.getYPosition(e));
    });

  }

  getYPosition(e: Event): number {
    return (e.target as Element).scrollTop;
  }

}
