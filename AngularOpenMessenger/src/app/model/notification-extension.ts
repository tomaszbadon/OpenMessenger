import { Notification } from "./notification";

export interface NotificationExtension extends Notification {
    numberOfMessages: number | null;
    firstName: string | null;
    lastName: string | null;
    avatar: string | null;
    visible: boolean | null;
}