import { Injectable } from '@angular/core';

export interface S2UiPopupMessage<T = unknown> {
  closable?: boolean;
  containerKey?: string | number;
  data?: T;
  duration?: number;
  heading?: string | null;
  icon?: string;
  key?: string | number;
  severity: 'error' | 'info' | 'success' | 'warning' | null;
  text?: string;
}

@Injectable({
  providedIn: 'root',
})
export class S2UiPopupMessagesService {
  messages: S2UiPopupMessage[] = [];

  add(message: S2UiPopupMessage): void {
    this.messages.push(message);
    if (message.duration === undefined || message.duration > 0) {
      setTimeout(() => {
        this.remove(message);
      }, message.duration || 5000);
    }
  }

  addAll(messages: S2UiPopupMessage[]): void {
    messages.forEach((message) => {
      this.add(message);
    });
  }

  remove(message: S2UiPopupMessage): boolean {
    if (message && this.messages.includes(message)) {
      this.messages.splice(this.messages.indexOf(message), 1);
      return true;
    }
    return false;
  }

  removeAt(index: number): boolean {
    if (this.messages[index]) {
      this.messages.splice(index, 1);
      return true;
    }
    return false;
  }

  removeByKey(key: S2UiPopupMessage['key']): boolean {
    const message = this.messages.find((message) => message.key === key);
    if (message) {
      this.messages.splice(this.messages.indexOf(message), 1);
      return true;
    }
    return false;
  }

  clear(): void {
    this.messages.splice(0, this.messages.length);
  }
}
