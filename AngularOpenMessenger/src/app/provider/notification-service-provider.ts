import { AuthService } from "../service/auth.service";
import { WebSocketNotificationService } from "../service/web-socket-notification.service";

export function notificationServiceFactory(authService: AuthService): WebSocketNotificationService {
    let service = new WebSocketNotificationService(authService);
    return service;
}