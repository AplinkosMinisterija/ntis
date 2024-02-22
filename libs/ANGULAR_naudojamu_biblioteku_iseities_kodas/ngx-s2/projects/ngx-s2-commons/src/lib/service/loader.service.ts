import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AppMessages } from '../message/app.messages';

export interface LoaderParams {
  show: boolean;
  dimmed?: boolean;
  message?: string;
}

@Injectable({ providedIn: 'root' })
export class LoaderService {
  private showCount = 0;
  private timeOutInterval: ReturnType<typeof setTimeout>;
  showLoader: BehaviorSubject<LoaderParams> = new BehaviorSubject<LoaderParams>({ show: false });

  constructor(private appMessages: AppMessages) {}

  show(message?: string, dimmed?: boolean): void {
    if (this.showCount === 0) {
      this.showLoader.next({ show: true, message, dimmed });
    }
    this.showCount++;
  }

  hide(): void {
    if (this.showCount > 0) {
      this.showCount--;
    }
    if (this.showCount === 0) {
      this.showLoader.next({ show: false });
      if (this.timeOutInterval !== undefined) {
        clearTimeout(this.timeOutInterval);
        this.timeOutInterval = undefined;
      }
    }
  }

  showWithOptions({
    message,
    dimmed,
    timeout,
    timeoutMessage,
  }: {
    message?: string;
    dimmed?: boolean;
    timeout?: number;
    timeoutMessage?: string;
  }): void {
    this.show(message, dimmed);
    if (timeout) {
      this.timeOutInterval = setTimeout(() => {
        this.onTimeout(timeoutMessage);
      }, timeout);
    }
  }

  protected onTimeout(timeoutMessage?: string): void {
    this.hide();
    if (timeoutMessage) {
      this.appMessages.showError('', timeoutMessage);
    }
  }
}
