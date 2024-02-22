import { HTTP_INTERCEPTORS, HttpErrorResponse, HttpResponse, } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs/operators';
import { AuthEvent } from '../../auth/auth.event';
import { S2Error } from '../../error/s2error';
import { ServerError } from '../../error/servererror';
import { UnauthorizedError } from '../../error/unauthorized';
import { LoggerFactory } from '../../log/logger.factory';
import { ServerUnavailableError } from '../../error/server-unavailable-error';
import * as i0 from "@angular/core";
import * as i1 from "../loader.service";
class S2Interceptor {
    loaderService;
    log = LoggerFactory.getLogger();
    constructor(loaderService) {
        this.loaderService = loaderService;
    }
    intercept(req, next) {
        if (!req.headers.has('x-s2-noloader')) {
            this.loaderService.show(req.url);
        }
        return next.handle(req).pipe(tap({
            next: (event) => {
                if (event instanceof HttpResponse) {
                    if (!req.headers.has('x-s2-noloader')) {
                        this.loaderService.hide();
                    }
                    const s2Status = event.headers.get('X-S2-status');
                    this.log.debug(`X-S2-status=${s2Status}`);
                    if (s2Status === 'ERR') {
                        const messages = event.body;
                        this.log.debug(messages);
                        throw new S2Error(messages);
                    }
                }
            },
            error: (err) => {
                if (!req.headers.has('x-s2-noloader')) {
                    this.loaderService.hide();
                }
                this.log.error('HTTP error', err);
                if (err instanceof HttpErrorResponse) {
                    this.log.debug(`${err.status} ' - system error:'}`);
                    switch (err.status) {
                        case 403:
                        case 401:
                            AuthEvent.isUnauthError = true;
                            throw new UnauthorizedError();
                        case 500:
                            throw new ServerError(err.headers.get('X-S2-Err-UUID'));
                        case 503:
                        case 504:
                            throw new ServerUnavailableError();
                    }
                }
            },
        }));
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2Interceptor, deps: [{ token: i1.LoaderService }], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2Interceptor });
}
export { S2Interceptor };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2Interceptor, decorators: [{
            type: Injectable
        }], ctorParameters: function () { return [{ type: i1.LoaderService }]; } });
export const s2Provider = {
    provide: HTTP_INTERCEPTORS,
    useClass: S2Interceptor,
    multi: true,
};
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiczIuaW50ZXJjZXB0b3IuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi8uLi8uLi8uLi8uLi8uLi9wcm9qZWN0cy9uZ3gtczItY29tbW9ucy9zcmMvbGliL3NlcnZpY2UvaW50ZXJjZXB0b3IvczIuaW50ZXJjZXB0b3IudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsT0FBTyxFQUNMLGlCQUFpQixFQUNqQixpQkFBaUIsRUFLakIsWUFBWSxHQUNiLE1BQU0sc0JBQXNCLENBQUM7QUFDOUIsT0FBTyxFQUFFLFVBQVUsRUFBRSxNQUFNLGVBQWUsQ0FBQztBQUUzQyxPQUFPLEVBQUUsR0FBRyxFQUFFLE1BQU0sZ0JBQWdCLENBQUM7QUFDckMsT0FBTyxFQUFFLFNBQVMsRUFBRSxNQUFNLHVCQUF1QixDQUFDO0FBQ2xELE9BQU8sRUFBRSxPQUFPLEVBQUUsTUFBTSxxQkFBcUIsQ0FBQztBQUM5QyxPQUFPLEVBQUUsV0FBVyxFQUFFLE1BQU0seUJBQXlCLENBQUM7QUFDdEQsT0FBTyxFQUFFLGlCQUFpQixFQUFFLE1BQU0sMEJBQTBCLENBQUM7QUFDN0QsT0FBTyxFQUFFLGFBQWEsRUFBRSxNQUFNLDBCQUEwQixDQUFDO0FBR3pELE9BQU8sRUFBRSxzQkFBc0IsRUFBRSxNQUFNLHNDQUFzQyxDQUFDOzs7QUFFOUUsTUFDYSxhQUFhO0lBR0o7SUFGcEIsR0FBRyxHQUFHLGFBQWEsQ0FBQyxTQUFTLEVBQUUsQ0FBQztJQUVoQyxZQUFvQixhQUE0QjtRQUE1QixrQkFBYSxHQUFiLGFBQWEsQ0FBZTtJQUFHLENBQUM7SUFFcEQsU0FBUyxDQUFDLEdBQXlCLEVBQUUsSUFBaUI7UUFDcEQsSUFBSSxDQUFDLEdBQUcsQ0FBQyxPQUFPLENBQUMsR0FBRyxDQUFDLGVBQWUsQ0FBQyxFQUFFO1lBQ3JDLElBQUksQ0FBQyxhQUFhLENBQUMsSUFBSSxDQUFDLEdBQUcsQ0FBQyxHQUFHLENBQUMsQ0FBQztTQUNsQztRQUNELE9BQU8sSUFBSSxDQUFDLE1BQU0sQ0FBQyxHQUFHLENBQUMsQ0FBQyxJQUFJLENBQzFCLEdBQUcsQ0FBQztZQUNGLElBQUksRUFBRSxDQUFDLEtBQUssRUFBRSxFQUFFO2dCQUNkLElBQUksS0FBSyxZQUFZLFlBQVksRUFBRTtvQkFDakMsSUFBSSxDQUFDLEdBQUcsQ0FBQyxPQUFPLENBQUMsR0FBRyxDQUFDLGVBQWUsQ0FBQyxFQUFFO3dCQUNyQyxJQUFJLENBQUMsYUFBYSxDQUFDLElBQUksRUFBRSxDQUFDO3FCQUMzQjtvQkFFRCxNQUFNLFFBQVEsR0FBRyxLQUFLLENBQUMsT0FBTyxDQUFDLEdBQUcsQ0FBQyxhQUFhLENBQUMsQ0FBQztvQkFDbEQsSUFBSSxDQUFDLEdBQUcsQ0FBQyxLQUFLLENBQUMsZUFBZSxRQUFRLEVBQUUsQ0FBQyxDQUFDO29CQUMxQyxJQUFJLFFBQVEsS0FBSyxLQUFLLEVBQUU7d0JBQ3RCLE1BQU0sUUFBUSxHQUFHLEtBQUssQ0FBQyxJQUFtQixDQUFDO3dCQUMzQyxJQUFJLENBQUMsR0FBRyxDQUFDLEtBQUssQ0FBQyxRQUFRLENBQUMsQ0FBQzt3QkFDekIsTUFBTSxJQUFJLE9BQU8sQ0FBQyxRQUFRLENBQUMsQ0FBQztxQkFDN0I7aUJBQ0Y7WUFDSCxDQUFDO1lBQ0QsS0FBSyxFQUFFLENBQUMsR0FBRyxFQUFFLEVBQUU7Z0JBQ2IsSUFBSSxDQUFDLEdBQUcsQ0FBQyxPQUFPLENBQUMsR0FBRyxDQUFDLGVBQWUsQ0FBQyxFQUFFO29CQUNyQyxJQUFJLENBQUMsYUFBYSxDQUFDLElBQUksRUFBRSxDQUFDO2lCQUMzQjtnQkFDRCxJQUFJLENBQUMsR0FBRyxDQUFDLEtBQUssQ0FBQyxZQUFZLEVBQUUsR0FBRyxDQUFDLENBQUM7Z0JBQ2xDLElBQUksR0FBRyxZQUFZLGlCQUFpQixFQUFFO29CQUNwQyxJQUFJLENBQUMsR0FBRyxDQUFDLEtBQUssQ0FBQyxHQUFHLEdBQUcsQ0FBQyxNQUFNLHNCQUFzQixDQUFDLENBQUM7b0JBQ3BELFFBQVEsR0FBRyxDQUFDLE1BQU0sRUFBRTt3QkFDbEIsS0FBSyxHQUFHLENBQUM7d0JBQ1QsS0FBSyxHQUFHOzRCQUNOLFNBQVMsQ0FBQyxhQUFhLEdBQUcsSUFBSSxDQUFDOzRCQUMvQixNQUFNLElBQUksaUJBQWlCLEVBQUUsQ0FBQzt3QkFDaEMsS0FBSyxHQUFHOzRCQUNOLE1BQU0sSUFBSSxXQUFXLENBQUMsR0FBRyxDQUFDLE9BQU8sQ0FBQyxHQUFHLENBQUMsZUFBZSxDQUFDLENBQUMsQ0FBQzt3QkFDMUQsS0FBSyxHQUFHLENBQUM7d0JBQ1QsS0FBSyxHQUFHOzRCQUNOLE1BQU0sSUFBSSxzQkFBc0IsRUFBRSxDQUFDO3FCQUN0QztpQkFDRjtZQUNILENBQUM7U0FDRixDQUFDLENBQ0gsQ0FBQztJQUNKLENBQUM7dUdBaERVLGFBQWE7MkdBQWIsYUFBYTs7U0FBYixhQUFhOzJGQUFiLGFBQWE7a0JBRHpCLFVBQVU7O0FBb0RYLE1BQU0sQ0FBQyxNQUFNLFVBQVUsR0FBRztJQUN4QixPQUFPLEVBQUUsaUJBQWlCO0lBQzFCLFFBQVEsRUFBRSxhQUFhO0lBQ3ZCLEtBQUssRUFBRSxJQUFJO0NBQ1osQ0FBQyIsInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7XG4gIEhUVFBfSU5URVJDRVBUT1JTLFxuICBIdHRwRXJyb3JSZXNwb25zZSxcbiAgSHR0cEV2ZW50LFxuICBIdHRwSGFuZGxlcixcbiAgSHR0cEludGVyY2VwdG9yLFxuICBIdHRwUmVxdWVzdCxcbiAgSHR0cFJlc3BvbnNlLFxufSBmcm9tICdAYW5ndWxhci9jb21tb24vaHR0cCc7XG5pbXBvcnQgeyBJbmplY3RhYmxlIH0gZnJvbSAnQGFuZ3VsYXIvY29yZSc7XG5pbXBvcnQgeyBPYnNlcnZhYmxlIH0gZnJvbSAncnhqcyc7XG5pbXBvcnQgeyB0YXAgfSBmcm9tICdyeGpzL29wZXJhdG9ycyc7XG5pbXBvcnQgeyBBdXRoRXZlbnQgfSBmcm9tICcuLi8uLi9hdXRoL2F1dGguZXZlbnQnO1xuaW1wb3J0IHsgUzJFcnJvciB9IGZyb20gJy4uLy4uL2Vycm9yL3MyZXJyb3InO1xuaW1wb3J0IHsgU2VydmVyRXJyb3IgfSBmcm9tICcuLi8uLi9lcnJvci9zZXJ2ZXJlcnJvcic7XG5pbXBvcnQgeyBVbmF1dGhvcml6ZWRFcnJvciB9IGZyb20gJy4uLy4uL2Vycm9yL3VuYXV0aG9yaXplZCc7XG5pbXBvcnQgeyBMb2dnZXJGYWN0b3J5IH0gZnJvbSAnLi4vLi4vbG9nL2xvZ2dlci5mYWN0b3J5JztcbmltcG9ydCB7IFMyTWVzc2FnZSB9IGZyb20gJy4uLy4uL21vZGVsL2NvbW1vbi5hcGknO1xuaW1wb3J0IHsgTG9hZGVyU2VydmljZSB9IGZyb20gJy4uL2xvYWRlci5zZXJ2aWNlJztcbmltcG9ydCB7IFNlcnZlclVuYXZhaWxhYmxlRXJyb3IgfSBmcm9tICcuLi8uLi9lcnJvci9zZXJ2ZXItdW5hdmFpbGFibGUtZXJyb3InO1xuXG5ASW5qZWN0YWJsZSgpXG5leHBvcnQgY2xhc3MgUzJJbnRlcmNlcHRvciBpbXBsZW1lbnRzIEh0dHBJbnRlcmNlcHRvciB7XG4gIGxvZyA9IExvZ2dlckZhY3RvcnkuZ2V0TG9nZ2VyKCk7XG5cbiAgY29uc3RydWN0b3IocHJpdmF0ZSBsb2FkZXJTZXJ2aWNlOiBMb2FkZXJTZXJ2aWNlKSB7fVxuXG4gIGludGVyY2VwdChyZXE6IEh0dHBSZXF1ZXN0PHVua25vd24+LCBuZXh0OiBIdHRwSGFuZGxlcik6IE9ic2VydmFibGU8SHR0cEV2ZW50PHVua25vd24+PiB7XG4gICAgaWYgKCFyZXEuaGVhZGVycy5oYXMoJ3gtczItbm9sb2FkZXInKSkge1xuICAgICAgdGhpcy5sb2FkZXJTZXJ2aWNlLnNob3cocmVxLnVybCk7XG4gICAgfVxuICAgIHJldHVybiBuZXh0LmhhbmRsZShyZXEpLnBpcGUoXG4gICAgICB0YXAoe1xuICAgICAgICBuZXh0OiAoZXZlbnQpID0+IHtcbiAgICAgICAgICBpZiAoZXZlbnQgaW5zdGFuY2VvZiBIdHRwUmVzcG9uc2UpIHtcbiAgICAgICAgICAgIGlmICghcmVxLmhlYWRlcnMuaGFzKCd4LXMyLW5vbG9hZGVyJykpIHtcbiAgICAgICAgICAgICAgdGhpcy5sb2FkZXJTZXJ2aWNlLmhpZGUoKTtcbiAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgY29uc3QgczJTdGF0dXMgPSBldmVudC5oZWFkZXJzLmdldCgnWC1TMi1zdGF0dXMnKTtcbiAgICAgICAgICAgIHRoaXMubG9nLmRlYnVnKGBYLVMyLXN0YXR1cz0ke3MyU3RhdHVzfWApO1xuICAgICAgICAgICAgaWYgKHMyU3RhdHVzID09PSAnRVJSJykge1xuICAgICAgICAgICAgICBjb25zdCBtZXNzYWdlcyA9IGV2ZW50LmJvZHkgYXMgUzJNZXNzYWdlW107XG4gICAgICAgICAgICAgIHRoaXMubG9nLmRlYnVnKG1lc3NhZ2VzKTtcbiAgICAgICAgICAgICAgdGhyb3cgbmV3IFMyRXJyb3IobWVzc2FnZXMpO1xuICAgICAgICAgICAgfVxuICAgICAgICAgIH1cbiAgICAgICAgfSxcbiAgICAgICAgZXJyb3I6IChlcnIpID0+IHtcbiAgICAgICAgICBpZiAoIXJlcS5oZWFkZXJzLmhhcygneC1zMi1ub2xvYWRlcicpKSB7XG4gICAgICAgICAgICB0aGlzLmxvYWRlclNlcnZpY2UuaGlkZSgpO1xuICAgICAgICAgIH1cbiAgICAgICAgICB0aGlzLmxvZy5lcnJvcignSFRUUCBlcnJvcicsIGVycik7XG4gICAgICAgICAgaWYgKGVyciBpbnN0YW5jZW9mIEh0dHBFcnJvclJlc3BvbnNlKSB7XG4gICAgICAgICAgICB0aGlzLmxvZy5kZWJ1ZyhgJHtlcnIuc3RhdHVzfSAnIC0gc3lzdGVtIGVycm9yOid9YCk7XG4gICAgICAgICAgICBzd2l0Y2ggKGVyci5zdGF0dXMpIHtcbiAgICAgICAgICAgICAgY2FzZSA0MDM6XG4gICAgICAgICAgICAgIGNhc2UgNDAxOlxuICAgICAgICAgICAgICAgIEF1dGhFdmVudC5pc1VuYXV0aEVycm9yID0gdHJ1ZTtcbiAgICAgICAgICAgICAgICB0aHJvdyBuZXcgVW5hdXRob3JpemVkRXJyb3IoKTtcbiAgICAgICAgICAgICAgY2FzZSA1MDA6XG4gICAgICAgICAgICAgICAgdGhyb3cgbmV3IFNlcnZlckVycm9yKGVyci5oZWFkZXJzLmdldCgnWC1TMi1FcnItVVVJRCcpKTtcbiAgICAgICAgICAgICAgY2FzZSA1MDM6XG4gICAgICAgICAgICAgIGNhc2UgNTA0OlxuICAgICAgICAgICAgICAgIHRocm93IG5ldyBTZXJ2ZXJVbmF2YWlsYWJsZUVycm9yKCk7XG4gICAgICAgICAgICB9XG4gICAgICAgICAgfVxuICAgICAgICB9LFxuICAgICAgfSlcbiAgICApO1xuICB9XG59XG5cbmV4cG9ydCBjb25zdCBzMlByb3ZpZGVyID0ge1xuICBwcm92aWRlOiBIVFRQX0lOVEVSQ0VQVE9SUyxcbiAgdXNlQ2xhc3M6IFMySW50ZXJjZXB0b3IsXG4gIG11bHRpOiB0cnVlLFxufTtcbiJdfQ==