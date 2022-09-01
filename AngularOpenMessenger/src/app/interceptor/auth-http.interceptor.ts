import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../service/auth.service';

@Injectable()
export class AuthHttpInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(httpRequest: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    if(!httpRequest.url.endsWith('/login')) {
      httpRequest = httpRequest.clone({
        setHeaders: { Authorization: "Bearer " + this.authService.getLoginInfo().accessToken }
      });
    }
    return next.handle(httpRequest);
  }
}
