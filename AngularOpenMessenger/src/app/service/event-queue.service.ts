import { Injectable } from '@angular/core';
import { filter, Observable, Subject } from 'rxjs';
import { AppEvent } from '../utils/app-event';
import { AppEventType } from '../utils/app-event-type';

@Injectable({
  providedIn: 'root'
})
export class EventQueueService {

  private eventBrocker = new Subject<AppEvent<any>>();

  on(eventType: AppEventType): Observable<AppEvent<any>> {
    return this.eventBrocker.pipe(filter(event => event.type === eventType));
  }

  dispatch<T>(event: AppEvent<T>): void {
    this.eventBrocker.next(event);
  }

}