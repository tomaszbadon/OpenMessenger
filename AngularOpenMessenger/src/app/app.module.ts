import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ApplicationComponent } from './application/application.component';
import { LoginComponent } from './login/login.component';
import { ContactComponent } from './components/contact/contact.component';
import { MessageBoxComponent } from './components/message-box/message-box.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthHttpInterceptor } from './interceptor/auth-http.interceptor';
import { MessageGroupComponent } from './components/message-group/message-group.component';
import { ContactListComponent } from './components/contact-list/contact-list.component';
import { MessageInputComponent } from './components/message-input/message-input.component';
import { ApiHttpInterceptor } from './interceptor/api-http.interceptor';
import { AuthService } from './service/auth.service';
import { NotificationContainerComponent } from './components/notification-container/notification-container.component';
import { NotificationPanelComponent } from './components/notification-panel/notification-panel.component';
import { WebSocketNotificationService } from './service/web-socket-notification.service';
import { notificationServiceFactory } from './provider/notification-service-provider';

@NgModule({
  declarations: [
    AppComponent,
    ApplicationComponent,
    LoginComponent,
    ContactComponent,
    MessageBoxComponent,
    SearchBarComponent,
    MessageGroupComponent,
    ContactListComponent,
    MessageInputComponent,
    NotificationContainerComponent,
    NotificationPanelComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthHttpInterceptor, multi: true }, 
    { provide: HTTP_INTERCEPTORS, useClass: ApiHttpInterceptor, multi: true },
    { provide: WebSocketNotificationService, useFactory: notificationServiceFactory, deps: [AuthService] }
],

  bootstrap: [AppComponent]
})
export class AppModule { }
