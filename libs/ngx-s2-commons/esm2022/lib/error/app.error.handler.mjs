import { AuthUtil } from './../auth/auth.util';
import { ErrorHandler, Injectable } from '@angular/core';
import { AuthEvent } from '../auth/auth.event';
import { LoggerFactory } from '../log/logger.factory';
import { S2Error } from './s2error';
import { ServerError } from './servererror';
import { UnauthorizedError } from './unauthorized';
import { v4 as uuid } from 'uuid';
import { ServerUnavailableError } from './server-unavailable-error';
import { HttpErrorResponse } from '@angular/common/http';
import * as i0 from "@angular/core";
import * as i1 from "../message/app.messages";
import * as i2 from "@ngx-translate/core";
import * as i3 from "./../service/client-log.service";
import * as i4 from "@angular/common";
class AppErrorHandler {
    appMessages;
    translate;
    clientLogService;
    datePipe;
    log = LoggerFactory.getLogger();
    logFrequencyPerMinute = 10;
    logTimeArray = [];
    constructor(appMessages, translate, clientLogService, datePipe) {
        this.appMessages = appMessages;
        this.translate = translate;
        this.clientLogService = clientLogService;
        this.datePipe = datePipe;
    }
    handleError(error) {
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
                        .subscribe((translation) => {
                        this.showMessage(translation, msg.severity);
                    });
                }
                else {
                    this.showMessage(msg.default_text, msg.severity);
                }
            });
            //Resolver do not throw UnauthorizedError instance, why we have to chack message text
        }
        else if (error instanceof UnauthorizedError || error.message.includes('UnauthorizedError')) {
            AuthEvent.userLoggedIn.next({
                userLoggedIn: false,
                session: null,
                type: AuthEvent.AUTH_EVENT_401,
            });
        }
        else if (error instanceof ServerError) {
            this.translate
                .get('common.error.unexpectedWithId', { errorID: `${error.uuid} API ${currentDateString} ` })
                .subscribe((translation) => this.appMessages.showError('', translation));
            this.log.error('Server error:' + error.uuid);
        }
        else if (error instanceof ServerUnavailableError) {
            this.translate
                .get('common.error.serverUnavailableError', { dateTimeTxt: currentDateString })
                .subscribe((translation) => this.appMessages.showError('', translation));
        }
        else {
            if (AuthUtil.isLoggedIn()) {
                const errorId = uuid();
                // check if error is sending not more than 10 time per minute
                if (!this.checkLogFrequencyLimit()) {
                    this.clientLogService.logClientError(this.formatClientError(error, errorId)).subscribe(() => {
                        this.translate
                            .get('common.error.unexpectedWithId', {
                            errorID: `${errorId} ${currentDateString} `,
                        })
                            .subscribe((translation) => this.appMessages.showError('', translation));
                    });
                }
            }
            else {
                this.translate
                    .get('common.error.unexpected')
                    .subscribe((translation) => this.appMessages.showError('', `${translation}`));
            }
            this.log.error('Unhandled error has occured:\n', error);
        }
    }
    showMessage(text, severity) {
        if (severity === 'E') {
            this.appMessages.showError('', text);
        }
        if (severity === 'W') {
            this.appMessages.showWarning('', text);
        }
    }
    formatClientError(error, errorId) {
        const clientSideError = {};
        clientSideError.errorCode = errorId;
        clientSideError.clientErrorTime = this.datePipe.transform(new Date(), 'yyyy-MM-dd HH:mm:ss');
        if (error instanceof HttpErrorResponse) {
            clientSideError.errorMessage = error.message;
        }
        else {
            clientSideError.errorMessage = error.stack;
        }
        return clientSideError;
    }
    checkLogFrequencyLimit() {
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
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AppErrorHandler, deps: [{ token: i1.AppMessages }, { token: i2.TranslateService }, { token: i3.ClientLogService }, { token: i4.DatePipe }], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AppErrorHandler });
}
export { AppErrorHandler };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AppErrorHandler, decorators: [{
            type: Injectable
        }], ctorParameters: function () { return [{ type: i1.AppMessages }, { type: i2.TranslateService }, { type: i3.ClientLogService }, { type: i4.DatePipe }]; } });
export const appErrorHandler = {
    provide: ErrorHandler,
    useClass: AppErrorHandler,
};
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYXBwLmVycm9yLmhhbmRsZXIuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi8uLi8uLi8uLi8uLi9wcm9qZWN0cy9uZ3gtczItY29tbW9ucy9zcmMvbGliL2Vycm9yL2FwcC5lcnJvci5oYW5kbGVyLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBLE9BQU8sRUFBRSxRQUFRLEVBQUUsTUFBTSxxQkFBcUIsQ0FBQztBQUkvQyxPQUFPLEVBQUUsWUFBWSxFQUFFLFVBQVUsRUFBRSxNQUFNLGVBQWUsQ0FBQztBQUV6RCxPQUFPLEVBQUUsU0FBUyxFQUFFLE1BQU0sb0JBQW9CLENBQUM7QUFDL0MsT0FBTyxFQUFFLGFBQWEsRUFBRSxNQUFNLHVCQUF1QixDQUFDO0FBRXRELE9BQU8sRUFBRSxPQUFPLEVBQUUsTUFBTSxXQUFXLENBQUM7QUFDcEMsT0FBTyxFQUFFLFdBQVcsRUFBRSxNQUFNLGVBQWUsQ0FBQztBQUM1QyxPQUFPLEVBQUUsaUJBQWlCLEVBQUUsTUFBTSxnQkFBZ0IsQ0FBQztBQUNuRCxPQUFPLEVBQUUsRUFBRSxJQUFJLElBQUksRUFBRSxNQUFNLE1BQU0sQ0FBQztBQUNsQyxPQUFPLEVBQUUsc0JBQXNCLEVBQUUsTUFBTSw0QkFBNEIsQ0FBQztBQUNwRSxPQUFPLEVBQUUsaUJBQWlCLEVBQUUsTUFBTSxzQkFBc0IsQ0FBQzs7Ozs7O0FBRXpELE1BQ2EsZUFBZTtJQU9kO0lBQ0E7SUFDQTtJQUNBO0lBVFosR0FBRyxHQUFHLGFBQWEsQ0FBQyxTQUFTLEVBQUUsQ0FBQztJQUVoQyxxQkFBcUIsR0FBRyxFQUFFLENBQUM7SUFDM0IsWUFBWSxHQUFhLEVBQUUsQ0FBQztJQUU1QixZQUNZLFdBQXdCLEVBQ3hCLFNBQTJCLEVBQzNCLGdCQUFrQyxFQUNsQyxRQUFrQjtRQUhsQixnQkFBVyxHQUFYLFdBQVcsQ0FBYTtRQUN4QixjQUFTLEdBQVQsU0FBUyxDQUFrQjtRQUMzQixxQkFBZ0IsR0FBaEIsZ0JBQWdCLENBQWtCO1FBQ2xDLGFB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LENBQUMsWUFBWSxHQUFHLEtBQUssQ0FBQyxPQUFPLENBQUM7U0FDOUM7YUFBTTtZQUNMLGVBQWUsQ0FBQyxZQUFZLEdBQUcsS0FBSyxDQUFDLEtBQUssQ0FBQztTQUM1QztRQUNELE9BQU8sZUFBZSxDQUFDO0lBQ3pCLENBQUM7SUFFTyxzQkFBc0I7UUFDNUIsSUFBSSxZQUFZLEdBQUcsS0FBSyxDQUFDO1FBQ3pCLElBQUksd0JBQXdCLEdBQUcsS0FBSyxDQUFDO1FBQ3JDLElBQUksSUFBSSxDQUFDLFlBQVksQ0FBQyxNQUFNLEdBQUcsSUFBSSxDQUFDLHFCQUFxQixHQUFHLENBQUMsRUFBRTtZQUM3RCxJQUFJLENBQUMsWUFBWSxDQUFDLEtBQUssRUFBRSxDQUFDO1lBQzFCLHdCQUF3QixHQUFHLElBQUksQ0FBQztTQUNqQztRQUNELElBQUksQ0FBQyxZQUFZLENBQUMsSUFBSSxDQUFDLElBQUksQ0FBQyxHQUFHLEVBQUUsQ0FBQyxDQUFDO1FBRW5DLElBQUksd0JBQXdCLEVBQUU7WUFDNUIsSUFBSSxDQUFDLElBQUksQ0FBQyxZQUFZLENBQUMsSUFBSSxDQUFDLFlBQVksQ0FBQyxNQUFNLEdBQUcsQ0FBQyxDQUFDLEdBQUcsSUFBSSxDQUFDLFlBQVksQ0FBQyxDQUFDLENBQUMsQ0FBQyxHQUFHLEtBQUssR0FBRyxDQUFDLEVBQUU7Z0JBQ3hGLFlBQVksR0FBRyxJQUFJLENBQUM7YUFDckI7U0FDRjtRQUVELE9BQU8sWUFBWSxDQUFDO0lBQ3RCLENBQUM7dUdBN0dVLGVBQWU7MkdBQWYsZUFBZTs7U0FBZixlQUFlOzJGQUFmLGVBQWU7a0JBRDNCLFVBQVU7O0FBaUhYLE1BQU0sQ0FBQyxNQUFNLGVBQWUsR0FBRztJQUM3QixPQUFPLEVBQUUsWUFBWTtJQUNyQixRQUFRLEVBQUUsZUFBZTtDQUMxQixDQUFDIiwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHsgQXV0aFV0aWwgfSBmcm9tICcuLy4uL2F1dGgvYXV0aC51dGlsJztcbmltcG9ydCB7IERhdGVQaXBlIH0gZnJvbSAnQGFuZ3VsYXIvY29tbW9uJztcbmltcG9ydCB7IENsaWVudExvZ1NlcnZpY2UgfSBmcm9tICcuLy4uL3NlcnZpY2UvY2xpZW50LWxvZy5zZXJ2aWNlJztcbmltcG9ydCB7IENsaWVudFNpZGVFcnJvciB9IGZyb20gJy4uL21vZGVsL2NvbW1vbi5hcGknO1xuaW1wb3J0IHsgRXJyb3JIYW5kbGVyLCBJbmplY3RhYmxlIH0gZnJvbSAnQGFuZ3VsYXIvY29yZSc7XG5pbXBvcnQgeyBUcmFuc2xhdGVTZXJ2aWNlIH0gZnJvbSAnQG5neC10cmFuc2xhdGUvY29yZSc7XG5pbXBvcnQgeyBBdXRoRXZlbnQgfSBmcm9tICcuLi9hdXRoL2F1dGguZXZlbnQnO1xuaW1wb3J0IHsgTG9nZ2VyRmFjdG9yeSB9IGZyb20gJy4uL2xvZy9sb2dnZXIuZmFjdG9yeSc7XG5pbXBvcnQgeyBBcHBNZXNzYWdlcyB9IGZyb20gJy4uL21lc3NhZ2UvYXBwLm1lc3NhZ2VzJztcbmltcG9ydCB7IFMyRXJyb3IgfSBmcm9tICcuL3MyZXJyb3InO1xuaW1wb3J0IHsgU2VydmVyRXJyb3IgfSBmcm9tICcuL3NlcnZlcmVycm9yJztcbmltcG9ydCB7IFVuYXV0aG9yaXplZEVycm9yIH0gZnJvbSAnLi91bmF1dGhvcml6ZWQnO1xuaW1wb3J0IHsgdjQgYXMgdXVpZCB9IGZyb20gJ3V1aWQnO1xuaW1wb3J0IHsgU2VydmVyVW5hdmFpbGFibGVFcnJvciB9IGZyb20gJy4vc2VydmVyLXVuYXZhaWxhYmxlLWVycm9yJztcbmltcG9ydCB7IEh0dHBFcnJvclJlc3BvbnNlIH0gZnJvbSAnQGFuZ3VsYXIvY29tbW9uL2h0dHAnO1xuXG5ASW5qZWN0YWJsZSgpXG5leHBvcnQgY2xhc3MgQXBwRXJyb3JIYW5kbGVyIGltcGxlbWVudHMgRXJyb3JIYW5kbGVyIHtcbiAgbG9nID0gTG9nZ2VyRmFjdG9yeS5nZXRMb2dnZXIoKTtcblxuICBsb2dGcmVxdWVuY3lQZXJNaW51dGUgPSAxMDtcbiAgbG9nVGltZUFycmF5OiBudW1iZXJbXSA9IFtdO1xuXG4gIGNvbnN0cnVjdG9yKFxuICAgIHByb3RlY3RlZCBhcHBNZXNzYWdlczogQXBwTWVzc2FnZXMsXG4gICAgcHJvdGVjdGVkIHRyYW5zbGF0ZTogVHJhbnNsYXRlU2VydmljZSxcbiAgICBwcm90ZWN0ZWQgY2xpZW50TG9nU2VydmljZTogQ2xpZW50TG9nU2VydmljZSxcbiAgICBwcm90ZWN0ZWQgZGF0ZVBpcGU6IERhdGVQaXBlXG4gICkge31cblxuICBoYW5kbGVFcnJvcihlcnJvcjogRXJyb3IpOiB2b2lkIHtcbiAgICBjb25zdCBjdXJyZW50RGF0ZVN0cmluZyA9IHRoaXMuZGF0ZVBpcGUudHJhbnNmb3JtKG5ldyBEYXRlKCksICd5eXl5LU1NLWRkIEhIOm1tOnNzJyk7XG4gICAgaWYgKGVycm9yIGluc3RhbmNlb2YgUzJFcnJvcikge1xuICAgICAgZXJyb3IubWVzc2FnZXMuZm9yRWFjaCgobXNnKSA9PiB7XG4gICAgICAgIGlmIChtc2cuY29kZSkge1xuICAgICAgICAgIHRoaXMudHJhbnNsYXRlXG4gICAgICAgICAgICAuZ2V0KG1zZy5jb2RlLCB7XG4gICAgICAgICAgICAgIHBhcmFtMTogbXNnLnBhcmFtMSxcbiAgICAgICAgICAgICAgcGFyYW0yOiBtc2cucGFyYW0yLFxuICAgICAgICAgICAgICBwYXJhbTM6IG1zZy5wYXJhbTMsXG4gICAgICAgICAgICAgIHBhcmFtNDogbXNnLnBhcmFtNCxcbiAgICAgICAgICAgICAgcGFyYW01OiBtc2cucGFyYW01LFxuICAgICAgICAgICAgfSlcbiAgICAgICAgICAgIC5zdWJzY3JpYmUoKHRyYW5zbGF0aW9uOiBzdHJpbmcpID0+IHtcbiAgICAgICAgICAgICAgdGhpcy5zaG93TWVzc2FnZSh0cmFuc2xhdGlvbiwgbXNnLnNldmVyaXR5KTtcbiAgICAgICAgICAgIH0pO1xuICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgIHRoaXMuc2hvd01lc3NhZ2UobXNnLmRlZmF1bHRfdGV4dCwgbXNnLnNldmVyaXR5KTtcbiAgICAgICAgfVxuICAgICAgfSk7XG4gICAgICAvL1Jlc29sdmVyIGRvIG5vdCB0aHJvdyBVbmF1dGhvcml6ZWRFcnJvciBpbnN0YW5jZSwgd2h5IHdlIGhhdmUgdG8gY2hhY2sgbWVzc2FnZSB0ZXh0XG4gICAgfSBlbHNlIGlmIChlcnJvciBpbnN0YW5jZW9mIFVuYXV0aG9yaXplZEVycm9yIHx8IGVycm9yLm1lc3NhZ2UuaW5jbHVkZXMoJ1VuYXV0aG9yaXplZEVycm9yJykpIHtcbiAgICAgIEF1dGhFdmVudC51c2VyTG9nZ2VkSW4ubmV4dCh7XG4gICAgICAgIHVzZXJMb2dnZWRJbjogZmFsc2UsXG4gICAgICAgIHNlc3Npb246IG51bGwsXG4gICAgICAgIHR5cGU6IEF1dGhFdmVudC5BVVRIX0VWRU5UXzQwMSxcbiAgICAgIH0pO1xuICAgIH0gZWxzZSBpZiAoZXJyb3IgaW5zdGFuY2VvZiBTZXJ2ZXJFcnJvcikge1xuICAgICAgdGhpcy50cmFuc2xhdGVcbiAgICAgICAgLmdldCgnY29tbW9uLmVycm9yLnVuZXhwZWN0ZWRXaXRoSWQnLCB7IGVycm9ySUQ6IGAke2Vycm9yLnV1aWR9IEFQSSAke2N1cnJlbnREYXRlU3RyaW5nfSBgIH0pXG4gICAgICAgIC5zdWJzY3JpYmUoKHRyYW5zbGF0aW9uOiBzdHJpbmcpID0+IHRoaXMuYXBwTWVzc2FnZXMuc2hvd0Vycm9yKCcnLCB0cmFuc2xhdGlvbikpO1xuICAgICAgdGhpcy5sb2cuZXJyb3IoJ1NlcnZlciBlcnJvcjonICsgZXJyb3IudXVpZCk7XG4gICAgfSBlbHNlIGlmIChlcnJvciBpbnN0YW5jZW9mIFNlcnZlclVuYXZhaWxhYmxlRXJyb3IpIHtcbiAgICAgIHRoaXMudHJhbnNsYXRlXG4gICAgICAgIC5nZXQoJ2NvbW1vbi5lcnJvci5zZXJ2ZXJVbmF2YWlsYWJsZUVycm9yJywgeyBkYXRlVGltZVR4dDogY3VycmVudERhdGVTdHJpbmcgfSlcbiAgICAgICAgLnN1YnNjcmliZSgodHJhbnNsYXRpb246IHN0cmluZykgPT4gdGhpcy5hcHBNZXNzYWdlcy5zaG93RXJyb3IoJycsIHRyYW5zbGF0aW9uKSk7XG4gICAgfSBlbHNlIHtcbiAgICAgIGlmIChBdXRoVXRpbC5pc0xvZ2dlZEluKCkpIHtcbiAgICAgICAgY29uc3QgZXJyb3JJZCA9IHV1aWQoKTtcbiAgICAgICAgLy8gY2hlY2sgaWYgZXJyb3IgaXMgc2VuZGluZyBub3QgbW9yZSB0aGFuIDEwIHRpbWUgcGVyIG1pbnV0ZVxuICAgICAgICBpZiAoIXRoaXMuY2hlY2tMb2dGcmVxdWVuY3lMaW1pdCgpKSB7XG4gICAgICAgICAgdGhpcy5jbGllbnRMb2dTZXJ2aWNlLmxvZ0NsaWVudEVycm9yKHRoaXMuZm9ybWF0Q2xpZW50RXJyb3IoZXJyb3IsIGVycm9ySWQpKS5zdWJzY3JpYmUoKCkgPT4ge1xuICAgICAgICAgICAgdGhpcy50cmFuc2xhdGVcbiAgICAgICAgICAgICAgLmdldCgnY29tbW9uLmVycm9yLnVuZXhwZWN0ZWRXaXRoSWQnLCB7XG4gICAgICAgICAgICAgICAgZXJyb3JJRDogYCR7ZXJyb3JJZH0gJHtjdXJyZW50RGF0ZVN0cmluZ30gYCxcbiAgICAgICAgICAgICAgfSlcbiAgICAgICAgICAgICAgLnN1YnNjcmliZSgodHJhbnNsYXRpb246IHN0cmluZykgPT4gdGhpcy5hcHBNZXNzYWdlcy5zaG93RXJyb3IoJycsIHRyYW5zbGF0aW9uKSk7XG4gICAgICAgICAgfSk7XG4gICAgICAgIH1cbiAgICAgIH0gZWxzZSB7XG4gICAgICAgIHRoaXMudHJhbnNsYXRlXG4gICAgICAgICAgLmdldCgnY29tbW9uLmVycm9yLnVuZXhwZWN0ZWQnKVxuICAgICAgICAgIC5zdWJzY3JpYmUoKHRyYW5zbGF0aW9uOiBzdHJpbmcpID0+IHRoaXMuYXBwTWVzc2FnZXMuc2hvd0Vycm9yKCcnLCBgJHt0cmFuc2xhdGlvbn1gKSk7XG4gICAgICB9XG4gICAgICB0aGlzLmxvZy5lcnJvcignVW5oYW5kbGVkIGVycm9yIGhhcyBvY2N1cmVkOlxcbicsIGVycm9yKTtcbiAgICB9XG4gIH1cblxuICBwcm90ZWN0ZWQgc2hvd01lc3NhZ2UodGV4dDogc3RyaW5nLCBzZXZlcml0eTogc3RyaW5nKTogdm9pZCB7XG4gICAgaWYgKHNldmVyaXR5ID09PSAnRScpIHtcbiAgICAgIHRoaXMuYXBwTWVzc2FnZXMuc2hvd0Vycm9yKCcnLCB0ZXh0KTtcbiAgICB9XG4gICAgaWYgKHNldmVyaXR5ID09PSAnVycpIHtcbiAgICAgIHRoaXMuYXBwTWVzc2FnZXMuc2hvd1dhcm5pbmcoJycsIHRleHQpO1xuICAgIH1cbiAgfVxuXG4gIHByaXZhdGUgZm9ybWF0Q2xpZW50RXJyb3IoZXJyb3I6IEVycm9yLCBlcnJvcklkOiBzdHJpbmcpOiBDbGllbnRTaWRlRXJyb3Ige1xuICAgIGNvbnN0IGNsaWVudFNpZGVFcnJvciA9IHt9IGFzIENsaWVudFNpZGVFcnJvcjtcbiAgICBjbGllbnRTaWRlRXJyb3IuZXJyb3JDb2RlID0gZXJyb3JJZDtcbiAgICBjbGllbnRTaWRlRXJyb3IuY2xpZW50RXJyb3JUaW1lID0gdGhpcy5kYXRlUGlwZS50cmFuc2Zvcm0obmV3IERhdGUoKSwgJ3l5eXktTU0tZGQgSEg6bW06c3MnKTtcblxuICAgIGlmIChlcnJvciBpbnN0YW5jZW9mIEh0dHBFcnJvclJlc3BvbnNlKSB7XG4gICAgICBjbGllbnRTaWRlRXJyb3IuZXJyb3JNZXNzYWdlID0gZXJyb3IubWVzc2FnZTtcbiAgICB9IGVsc2Uge1xuICAgICAgY2xpZW50U2lkZUVycm9yLmVycm9yTWVzc2FnZSA9IGVycm9yLnN0YWNrO1xuICAgIH1cbiAgICByZXR1cm4gY2xpZW50U2lkZUVycm9yO1xuICB9XG5cbiAgcHJpdmF0ZSBjaGVja0xvZ0ZyZXF1ZW5jeUxpbWl0KCk6IGJvb2xlYW4ge1xuICAgIGxldCBpc0xvZ1RvT2Z0ZW4gPSBmYWxzZTtcbiAgICBsZXQgaXNGcmVxdWVuY3lDaGVja1JlcXVpcmVkID0gZmFsc2U7XG4gICAgaWYgKHRoaXMubG9nVGltZUFycmF5Lmxlbmd0aCA+IHRoaXMubG9nRnJlcXVlbmN5UGVyTWludXRlIC0gMSkge1xuICAgICAgdGhpcy5sb2dUaW1lQXJyYXkuc2hpZnQoKTtcbiAgICAgIGlzRnJlcXVlbmN5Q2hlY2tSZXF1aXJlZCA9IHRydWU7XG4gICAgfVxuICAgIHRoaXMubG9nVGltZUFycmF5LnB1c2goRGF0ZS5ub3coKSk7XG5cbiAgICBpZiAoaXNGcmVxdWVuY3lDaGVja1JlcXVpcmVkKSB7XG4gICAgICBpZiAoKHRoaXMubG9nVGltZUFycmF5W3RoaXMubG9nVGltZUFycmF5Lmxlbmd0aCAtIDFdIC0gdGhpcy5sb2dUaW1lQXJyYXlbMF0pIC8gNjAwMDAgPCAxKSB7XG4gICAgICAgIGlzTG9nVG9PZnRlbiA9IHRydWU7XG4gICAgICB9XG4gICAgfVxuXG4gICAgcmV0dXJuIGlzTG9nVG9PZnRlbjtcbiAgfVxufVxuXG5leHBvcnQgY29uc3QgYXBwRXJyb3JIYW5kbGVyID0ge1xuICBwcm92aWRlOiBFcnJvckhhbmRsZXIsXG4gIHVzZUNsYXNzOiBBcHBFcnJvckhhbmRsZXIsXG59O1xuIl19