import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  isAuthenticated: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void { 
    this.isAuthenticated = "isAuthenticated : " + this.authService.isAuthenticated();
  }

  login() {
    this.authService.login('dominica.rosatti', 'my_password').subscribe(output => {
      if(output) {
        this.router.navigate(['/application']);
      } else {
        alert("Error occured");
      }
      this.isAuthenticated = "isAuthenticated : " + output;
    });
  }

}
