import { AuthUtil } from './../auth/auth.util';
import { DatePipe } from '@angular/common';
import { ClientLogService } from './../service/client-log.service';
import { ClientSideError } from '../model/common.api';
import { ErrorHandler, Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { AuthEvent } from '../auth/auth.event';
import { LoggerFactory } from '../log/logger.factory';
import { AppMessages } from '../message/app.messages';
import { S2Error } from './s2error';
import { ServerError } from './servererror';
import { UnauthorizedError } from './unauthorized';
import { v4 as uuid } from 'uuid';
import { ServerUnavailableError } from './server-unavailable-error';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class AppErrorHandler implements ErrorHandler {
  log = LoggerFactory.getLogger();

  logFrequencyPerMinute = 10;
  logTimeArray: number[] = [];

  constructor(
    protected appMessages: AppMessages,
    protected translate: TranslateService,
    protected clientLogService: ClientLogService,
    protected datePipe: DatePipe
  ) {}

  handleError(error: Error): void {
    const currentDateString = this.datePipe.transform(new Date(), 'yyyy-MM-dd HH:mm:ss');
    if (error instanceof S2Error) {
      error.messages.forEach((msg) => {
        if (msg.code) {
          this.translate
            .get(msg.code, {
              param1: msg.param1,
              param2: msg.param2,
              param3: msg.param3,
              param4: msg.param4,
              param5: msg.param5,
            })
            .subscribe((translation: string) => {
              this.showMessage(translation, msg.severity);
            });
        } else {
          this.showMessage(msg.default_text, msg.severity);
        }
      });
      //Resolver do not throw UnauthorizedError instance, why we have to chack message text
    } else if (error instanceof UnauthorizedError || error.message.includes('UnauthorizedError')) {
      AuthEvent.userLoggedIn.next({
        userLoggedIn: false,
        session: null,
        type: AuthEvent.AUTH_EVENT_401,
      });
    } else if (error instanceof ServerError) {
      this.translate
        .get('common.error.unexpectedWithId', { errorID: `${error.uuid} API ${currentDateString} ` })
        .subscribe((translation: string) => this.appMessages.showError('', translation));
      this.log.error('Server error:' + error.uuid);
    } else if (error instanceof ServerUnavailableError) {
      this.translate
        .get('common.error.serverUnavailableError', { dateTimeTxt: currentDateString })
        .subscribe((translation: string) => this.appMessages.showError('', translation));
    } else {
      if (AuthUtil.isLoggedIn()) {
        const errorId = uuid();
        // check if error is sending not more than 10 time per minute
        if (!this.checkLogFrequencyLimit()) {
          this.clientLogService.logClientError(this.formatClientError(error, errorId)).subscribe(() => {
            this.translate
              .get('common.error.unexpectedWithId', {
                errorID: `${errorId} ${currentDateString} `,
              })
              .subscribe((translation: string) => this.appMessages.showError('', translation));
          });
        }
      } else {
        this.translate
          .get('common.error.unexpected')
          .subscribe((translation: string) => this.appMessages.showError('', `${translation}`));
      }
      this.log.error('Unhandled error has occured:\n', error);
    }
  }

  protected showMessage(text: string, severity: string): void {
    if (severity === 'E') {
      this.appMessages.showError('', text);
    }
    if (severity === 'W') {
      this.appMessages.showWarning('', text);
    }
  }

  private formatClientError(error: Error, errorId: string): ClientSideError {
    const clientSideError = {} as ClientSideError;
    clientSideError.errorCode = errorId;
    clientSideError.clientErrorTime = this.datePipe.transform(new Date(), 'yyyy-MM-dd HH:mm:ss');

    if (error instanceof HttpErrorResponse) {
      clientSideError.errorMessage = error.message;
    } else {
      clientSideError.errorMessage = error.stack;
    }
    return clientSideError;
  }

  private checkLogFrequencyLimit(): boolean {
    let isLogToOften = false;
    let isFrequencyCheckRequired = false;
    if (this.logTimeArray.length > this.logFrequencyPerMinute - 1) {
      this.logTimeArray.shift();
      isFrequencyCheckRequired = true;
    }
    this.logTimeArray.push(Date.now());

    if (isFrequencyCheckRequired) {
      if ((this.logTimeArray[this.logTimeArray.length - 1] - this.logTimeArray[0]) / 60000 < 1) {
        isLogToOften = true;
      }
    }

    return isLogToOften;
  }
}

export const appErrorHandler = {
  provide: ErrorHandler,
  useClass: AppErrorHandler,
};
