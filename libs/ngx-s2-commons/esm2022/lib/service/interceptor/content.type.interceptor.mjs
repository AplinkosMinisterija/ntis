import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as i0 from "@angular/core";
class ContentTypeInterceptor {
    intercept(request, next) {
        if (request.method === 'POST' &&
            !request.headers.get('Content-Type') &&
            (request.body === undefined || request.body === null)) {
            const clonedRequest = request.clone({
                headers: request.headers.set('Content-Type', 'application/json'),
            });
            return next.handle(clonedRequest);
        }
        return next.handle(request);
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ContentTypeInterceptor, deps: [], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ContentTypeInterceptor });
}
export { ContentTypeInterceptor };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ContentTypeInterceptor, decorators: [{
            type: Injectable
        }] });
export const contentTypeInterceptor = {
    provide: HTTP_INTERCEPTORS,
    useClass: ContentTypeInterceptor,
    multi: true,
};
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY29udGVudC50eXBlLmludGVyY2VwdG9yLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vLi4vLi4vLi4vLi4vcHJvamVjdHMvbmd4LXMyLWNvbW1vbnMvc3JjL2xpYi9zZXJ2aWNlL2ludGVyY2VwdG9yL2NvbnRlbnQudHlwZS5pbnRlcmNlcHRvci50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQSxPQUFPLEVBQUUsaUJBQWlCLEVBQXdELE1BQU0sc0JBQXNCLENBQUM7QUFDL0csT0FBTyxFQUFFLFVBQVUsRUFBRSxNQUFNLGVBQWUsQ0FBQzs7QUFHM0MsTUFDYSxzQkFBc0I7SUFDakMsU0FBUyxDQUFDLE9BQTZCLEVBQUUsSUFBaUI7UUFDeEQsSUFDRSxPQUFPLENBQUMsTUFBTSxLQUFLLE1BQU07WUFDekIsQ0FBQyxPQUFPLENBQUMsT0FBTyxDQUFDLEdBQUcsQ0FBQyxjQUFjLENBQUM7WUFDcEMsQ0FBQyxPQUFPLENBQUMsSUFBSSxLQUFLLFNBQVMsSUFBSSxPQUFPLENBQUMsSUFBSSxLQUFLLElBQUksQ0FBQyxFQUNyRDtZQUNBLE1BQU0sYUFBYSxHQUFHLE9BQU8sQ0FBQyxLQUFLLENBQUM7Z0JBQ2xDLE9BQU8sRUFBRSxPQUFPLENBQUMsT0FBTyxDQUFDLEdBQUcsQ0FBQyxjQUFjLEVBQUUsa0JBQWtCLENBQUM7YUFDakUsQ0FBQyxDQUFDO1lBQ0gsT0FBTyxJQUFJLENBQUMsTUFBTSxDQUFDLGFBQWEsQ0FBQyxDQUFDO1NBQ25DO1FBQ0QsT0FBTyxJQUFJLENBQUMsTUFBTSxDQUFDLE9BQU8sQ0FBQyxDQUFDO0lBQzlCLENBQUM7dUdBYlUsc0JBQXNCOzJHQUF0QixzQkFBc0I7O1NBQXRCLHNCQUFzQjsyRkFBdEIsc0JBQXNCO2tCQURsQyxVQUFVOztBQWlCWCxNQUFNLENBQUMsTUFBTSxzQkFBc0IsR0FBRztJQUNwQyxPQUFPLEVBQUUsaUJBQWlCO0lBQzFCLFFBQVEsRUFBRSxzQkFBc0I7SUFDaEMsS0FBSyxFQUFFLElBQUk7Q0FDWixDQUFDIiwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHsgSFRUUF9JTlRFUkNFUFRPUlMsIEh0dHBFdmVudCwgSHR0cEhhbmRsZXIsIEh0dHBJbnRlcmNlcHRvciwgSHR0cFJlcXVlc3QgfSBmcm9tICdAYW5ndWxhci9jb21tb24vaHR0cCc7XG5pbXBvcnQgeyBJbmplY3RhYmxlIH0gZnJvbSAnQGFuZ3VsYXIvY29yZSc7XG5pbXBvcnQgeyBPYnNlcnZhYmxlIH0gZnJvbSAncnhqcyc7XG5cbkBJbmplY3RhYmxlKClcbmV4cG9ydCBjbGFzcyBDb250ZW50VHlwZUludGVyY2VwdG9yIGltcGxlbWVudHMgSHR0cEludGVyY2VwdG9yIHtcbiAgaW50ZXJjZXB0KHJlcXVlc3Q6IEh0dHBSZXF1ZXN0PHVua25vd24+LCBuZXh0OiBIdHRwSGFuZGxlcik6IE9ic2VydmFibGU8SHR0cEV2ZW50PHVua25vd24+PiB7XG4gICAgaWYgKFxuICAgICAgcmVxdWVzdC5tZXRob2QgPT09ICdQT1NUJyAmJlxuICAgICAgIXJlcXVlc3QuaGVhZGVycy5nZXQoJ0NvbnRlbnQtVHlwZScpICYmXG4gICAgICAocmVxdWVzdC5ib2R5ID09PSB1bmRlZmluZWQgfHwgcmVxdWVzdC5ib2R5ID09PSBudWxsKVxuICAgICkge1xuICAgICAgY29uc3QgY2xvbmVkUmVxdWVzdCA9IHJlcXVlc3QuY2xvbmUoe1xuICAgICAgICBoZWFkZXJzOiByZXF1ZXN0LmhlYWRlcnMuc2V0KCdDb250ZW50LVR5cGUnLCAnYXBwbGljYXRpb24vanNvbicpLFxuICAgICAgfSk7XG4gICAgICByZXR1cm4gbmV4dC5oYW5kbGUoY2xvbmVkUmVxdWVzdCk7XG4gICAgfVxuICAgIHJldHVybiBuZXh0LmhhbmRsZShyZXF1ZXN0KTtcbiAgfVxufVxuXG5leHBvcnQgY29uc3QgY29udGVudFR5cGVJbnRlcmNlcHRvciA9IHtcbiAgcHJvdmlkZTogSFRUUF9JTlRFUkNFUFRPUlMsXG4gIHVzZUNsYXNzOiBDb250ZW50VHlwZUludGVyY2VwdG9yLFxuICBtdWx0aTogdHJ1ZSxcbn07XG4iXX0=