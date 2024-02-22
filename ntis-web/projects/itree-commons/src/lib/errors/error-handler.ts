import { DatePipe } from '@angular/common';
import { ErrorHandler, Injectable, NgZone, Provider } from '@angular/core';
import { Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AppErrorHandler, AppMessages, ClientLogService, S2Error, UnauthorizedError } from '@itree/ngx-s2-commons';
import { TranslateService } from '@ngx-translate/core';

@Injectable({ providedIn: 'root' })
export class SprErrorHandler extends AppErrorHandler implements ErrorHandler {
  constructor(
    protected override appMessages: AppMessages,
    protected override translate: TranslateService,
    protected override clientLogService: ClientLogService,
    protected override datePipe: DatePipe,
    private router: Router,
    private ngZone: NgZone
  ) {
    super(appMessages, translate, clientLogService, datePipe);
  }

  override handleError(error: Error): void {
    if (error instanceof S2Error && error.messages.find((message) => message.item === 'USER_PASSWORD')) {
      this.ngZone.run(() => {
        void this.router.navigate(['/', RoutingConst.INTERNAL, RoutingConst.SPARK_CHANGE_PASSWORD]);
      });
    } else {
      if (!(error instanceof UnauthorizedError) && error.message.includes('UnauthorizedError')) {
        error = new UnauthorizedError();
      }
      super.handleError(error);
    }
  }
}

export const sprErrorHandlerProvider: Provider = {
  provide: ErrorHandler,
  useClass: SprErrorHandler,
};
