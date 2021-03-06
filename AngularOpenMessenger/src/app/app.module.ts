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

@NgModule({
  declarations: [
    AppComponent,
    ApplicationComponent,
    LoginComponent,
    ContactComponent,
    MessageBoxComponent,
    SearchBarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthHttpInterceptor, multi: true } ],

  bootstrap: [AppComponent]
})
export class AppModule { }
