import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthUtil } from '../../auth/auth.util';
import * as i0 from "@angular/core";
class JwtInterceptor {
    intercept(req, next) {
        // console.log('JwtInterceptor');
        const clonedRequest = req.clone({
            headers: req.headers.set('Authorization', 'Bearer ' + AuthUtil.getJWTFromSession()),
        });
        return next.handle(clonedRequest);
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: JwtInterceptor, deps: [], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: JwtInterceptor });
}
export { JwtInterceptor };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: JwtInterceptor, decorators: [{
            type: Injectable
        }] });
export const jwtProvider = {
    provide: HTTP_INTERCEPTORS,
    useClass: JwtInterceptor,
    multi: true,
};
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiand0LmludGVyY2VwdG9yLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vLi4vLi4vLi4vLi4vcHJvamVjdHMvbmd4LXMyLWNvbW1vbnMvc3JjL2xpYi9zZXJ2aWNlL2ludGVyY2VwdG9yL2p3dC5pbnRlcmNlcHRvci50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQSxPQUFPLEVBQUUsaUJBQWlCLEVBQXdELE1BQU0sc0JBQXNCLENBQUM7QUFFL0csT0FBTyxFQUFFLFVBQVUsRUFBRSxNQUFNLGVBQWUsQ0FBQztBQUMzQyxPQUFPLEVBQUUsUUFBUSxFQUFFLE1BQU0sc0JBQXNCLENBQUM7O0FBRWhELE1BQ2EsY0FBYztJQUN6QixTQUFTLENBQUMsR0FBeUIsRUFBRSxJQUFpQjtRQUNwRCxpQ0FBaUM7UUFDakMsTUFBTSxhQUFhLEdBQUcsR0FBRyxDQUFDLEtBQUssQ0FBQztZQUM5QixPQUFPLEVBQUUsR0FBRyxDQUFDLE9BQU8sQ0FBQyxHQUFHLENBQUMsZUFBZSxFQUFFLFNBQVMsR0FBRyxRQUFRLENBQUMsaUJBQWlCLEVBQUUsQ0FBQztTQUNwRixDQUFDLENBQUM7UUFDSCxPQUFPLElBQUksQ0FBQyxNQUFNLENBQUMsYUFBYSxDQUFDLENBQUM7SUFDcEMsQ0FBQzt1R0FQVSxjQUFjOzJHQUFkLGNBQWM7O1NBQWQsY0FBYzsyRkFBZCxjQUFjO2tCQUQxQixVQUFVOztBQVdYLE1BQU0sQ0FBQyxNQUFNLFdBQVcsR0FBRztJQUN6QixPQUFPLEVBQUUsaUJBQWlCO0lBQzFCLFFBQVEsRUFBRSxjQUFjO0lBQ3hCLEtBQUssRUFBRSxJQUFJO0NBQ1osQ0FBQyIsInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7IEhUVFBfSU5URVJDRVBUT1JTLCBIdHRwRXZlbnQsIEh0dHBIYW5kbGVyLCBIdHRwSW50ZXJjZXB0b3IsIEh0dHBSZXF1ZXN0IH0gZnJvbSAnQGFuZ3VsYXIvY29tbW9uL2h0dHAnO1xyXG5pbXBvcnQgeyBPYnNlcnZhYmxlIH0gZnJvbSAncnhqcyc7XHJcbmltcG9ydCB7IEluamVjdGFibGUgfSBmcm9tICdAYW5ndWxhci9jb3JlJztcclxuaW1wb3J0IHsgQXV0aFV0aWwgfSBmcm9tICcuLi8uLi9hdXRoL2F1dGgudXRpbCc7XHJcblxyXG5ASW5qZWN0YWJsZSgpXHJcbmV4cG9ydCBjbGFzcyBKd3RJbnRlcmNlcHRvciBpbXBsZW1lbnRzIEh0dHBJbnRlcmNlcHRvciB7XHJcbiAgaW50ZXJjZXB0KHJlcTogSHR0cFJlcXVlc3Q8dW5rbm93bj4sIG5leHQ6IEh0dHBIYW5kbGVyKTogT2JzZXJ2YWJsZTxIdHRwRXZlbnQ8dW5rbm93bj4+IHtcclxuICAgIC8vIGNvbnNvbGUubG9nKCdKd3RJbnRlcmNlcHRvcicpO1xyXG4gICAgY29uc3QgY2xvbmVkUmVxdWVzdCA9IHJlcS5jbG9uZSh7XHJcbiAgICAgIGhlYWRlcnM6IHJlcS5oZWFkZXJzLnNldCgnQXV0aG9yaXphdGlvbicsICdCZWFyZXIgJyArIEF1dGhVdGlsLmdldEpXVEZyb21TZXNzaW9uKCkpLFxyXG4gICAgfSk7XHJcbiAgICByZXR1cm4gbmV4dC5oYW5kbGUoY2xvbmVkUmVxdWVzdCk7XHJcbiAgfVxyXG59XHJcblxyXG5leHBvcnQgY29uc3Qgand0UHJvdmlkZXIgPSB7XHJcbiAgcHJvdmlkZTogSFRUUF9JTlRFUkNFUFRPUlMsXHJcbiAgdXNlQ2xhc3M6IEp3dEludGVyY2VwdG9yLFxyXG4gIG11bHRpOiB0cnVlLFxyXG59O1xyXG4iXX0=