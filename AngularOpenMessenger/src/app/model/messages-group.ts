import { Message } from 'src/app/model/message';

export class MessagesGroup {
    constructor(public recipient: number, public sender: number, public messages: Message[]) { }
}