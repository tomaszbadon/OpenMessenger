import { Component, HostBinding, Input, OnInit } from '@angular/core';
import { NotificationExtension } from 'src/app/model/notification-extension';

@Component({
  selector: 'app-notification-panel',
  templateUrl: './notification-panel.component.html',
  styleUrls: ['./notification-panel.component.sass']
})
export class NotificationPanelComponent implements OnInit {

  @Input() notification: NotificationExtension | null = null;

  constructor() { }

  ngOnInit(): void { 
    let that = this;
    setTimeout(() => { this.notification!.visible = false; }, 5000); 
  }

}
