import { Message } from './message';

export class MessageCache {

    private cache: Map<String, Message[]> = new Map();

    addMessages(messages: Message[], userId1: number, userId2: number) {
      let key = this.key(userId1, userId2);
      let storedMessages = this.cache.get(key);
      if(storedMessages != null) {
        this.cache.set(key, storedMessages.concat(messages));
      } else {
        this.cache.set(key, messages);
      }
    }

    addMessage(message: Message, userId1: number, userId2: number) {
      let key = this.key(userId1, userId2);
      let storedMessages = this.cache.get(key);
      if(storedMessages != null) {
        storedMessages.push(message);
      } else {
        this.cache.set(key, new Array(message));
      }
    }

    getMessages(userId1: number, userId2: number): Message[] {
      let messages = this.cache.get(this.key(userId1, userId2));
      if(messages != null) {
        return messages;
      } else {
        return Array<Message>();
      }
    }

    clear() {
      this.cache.clear();
    }

    private key(userId1: number, userId2: number): string {
        if(userId1 < userId2) {
          return userId1 + '_' + userId2;
        } else {
          return userId2 + '_' + userId1;
        }
    }

}