import { Injectable, NgZone } from '@angular/core';
import { Subject } from 'rxjs';

export interface AppMessage {
  severity?: string;
  detail?: string;
  id?: unknown;
  sticky?: boolean;
  data?: unknown;
}

@Injectable({
  providedIn: 'root',
})
export class AppMessages {
  public static readonly SEVERITY_ERROR = 'error';
  public static readonly SEVERITY_WARN = 'warn';
  public static readonly SEVERITY_SUCCESS = 'success';
  public static readonly SEVERITY_INFO = 'info';

  public readonly add$ = new Subject<AppMessage>();
  public readonly clear$ = new Subject<void>();

  private messages: AppMessage[] = [];

  constructor(private ngZone: NgZone) {}

  private showMessage(message: AppMessage): void {
    if (
      message.id === 'VALIDATION' ||
      message.severity === AppMessages.SEVERITY_ERROR ||
      message.severity === AppMessages.SEVERITY_WARN
    ) {
      message.sticky = true;
    }
    this.ngZone.run(() => {
      if (
        this.messages.some(
          (messageFromArray) =>
            messageFromArray.severity === message.severity && messageFromArray.detail === message.detail
        )
      ) {
        this.clearMessages();
        this.messages.push(message);
        this.add$.next(message);
      } else if (
        (message.data as Record<string, unknown>)?.allowMultiple ||
        !this.messages.some(
          (messageFromArray) =>
            messageFromArray.severity === message.severity && messageFromArray.detail === message.detail
        )
      ) {
        this.messages.push(message);
        this.add$.next(message);
      }
    });
  }

  clearMessages(): void {
    this.clear$.next();
    this.messages = [];
  }

  clearMessagesExceptSuccess(): void {
    if (this.messages.some((message) => message.severity !== AppMessages.SEVERITY_SUCCESS)) {
      this.clear$.next();
      this.messages = [];
    }
  }

  removeMessage(message: AppMessage): void {
    const messageIndex = this.messages.findIndex(
      (messageFromArray) => messageFromArray.severity === message.severity && messageFromArray.detail === message.detail
    );
    if (messageIndex !== -1) {
      this.messages.splice(messageIndex, 1);
    }
  }

  showError(summary: string, detail: string, id?: string): void {
    this.showMessage({ severity: AppMessages.SEVERITY_ERROR, detail, id });
  }

  showWarning(summary: string, detail: string, id?: string): void {
    this.showMessage({ severity: AppMessages.SEVERITY_WARN, detail, id });
  }

  showSuccess(summary: string, detail: string, id?: string): void {
    this.showMessage({ severity: AppMessages.SEVERITY_SUCCESS, detail, id });
  }

  showInfo(summary: string, detail: string, id?: string): void {
    this.showMessage({ severity: AppMessages.SEVERITY_INFO, detail, id });
  }
}
