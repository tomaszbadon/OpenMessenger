import { Message } from "./message";

export interface MessagesPage {
    messages: Message[];
    page: number;
}