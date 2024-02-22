import { Injectable } from '@angular/core';
import * as i0 from "@angular/core";
import * as i1 from "@angular/common/http";
class ClientLogService {
    http;
    constructor(http) {
        this.http = http;
    }
    logClientError(clientSideError) {
        return this.http.post(this.getLogServiceApiUrl(), clientSideError);
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ClientLogService, deps: [{ token: i1.HttpClient }], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ClientLogService });
}
export { ClientLogService };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ClientLogService, decorators: [{
            type: Injectable
        }], ctorParameters: function () { return [{ type: i1.HttpClient }]; } });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY2xpZW50LWxvZy5zZXJ2aWNlLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vLi4vLi4vLi4vcHJvamVjdHMvbmd4LXMyLWNvbW1vbnMvc3JjL2xpYi9zZXJ2aWNlL2NsaWVudC1sb2cuc2VydmljZS50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQSxPQUFPLEVBQUUsVUFBVSxFQUFFLE1BQU0sZUFBZSxDQUFDOzs7QUFLM0MsTUFDc0IsZ0JBQWdCO0lBQ2Q7SUFBcEIsWUFBb0IsSUFBZ0I7UUFBaEIsU0FBSSxHQUFKLElBQUksQ0FBWTtJQUFJLENBQUM7SUFFekMsY0FBYyxDQUFDLGVBQWdDO1FBQzNDLE9BQU8sSUFBSSxDQUFDLElBQUksQ0FBQyxJQUFJLENBQU8sSUFBSSxDQUFDLG1CQUFtQixFQUFFLEVBQUUsZUFBZSxDQUFDLENBQUM7SUFDN0UsQ0FBQzt1R0FMaUIsZ0JBQWdCOzJHQUFoQixnQkFBZ0I7O1NBQWhCLGdCQUFnQjsyRkFBaEIsZ0JBQWdCO2tCQURyQyxVQUFVIiwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHsgSW5qZWN0YWJsZSB9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xyXG5pbXBvcnQgeyBIdHRwQ2xpZW50IH0gZnJvbSAnQGFuZ3VsYXIvY29tbW9uL2h0dHAnO1xyXG5pbXBvcnQgeyBPYnNlcnZhYmxlIH0gZnJvbSAncnhqcyc7XHJcbmltcG9ydCB7IENsaWVudFNpZGVFcnJvciB9IGZyb20gJy4uL21vZGVsL2NvbW1vbi5hcGknO1xyXG5cclxuQEluamVjdGFibGUoKVxyXG5leHBvcnQgYWJzdHJhY3QgY2xhc3MgQ2xpZW50TG9nU2VydmljZSB7XHJcbiAgICBjb25zdHJ1Y3Rvcihwcml2YXRlIGh0dHA6IEh0dHBDbGllbnQpIHsgfVxyXG5cclxuICAgIGxvZ0NsaWVudEVycm9yKGNsaWVudFNpZGVFcnJvcjogQ2xpZW50U2lkZUVycm9yKTogT2JzZXJ2YWJsZTx2b2lkPiB7XHJcbiAgICAgICAgcmV0dXJuIHRoaXMuaHR0cC5wb3N0PHZvaWQ+KHRoaXMuZ2V0TG9nU2VydmljZUFwaVVybCgpLCBjbGllbnRTaWRlRXJyb3IpO1xyXG4gICAgfVxyXG5cclxuICAgIGFic3RyYWN0IGdldExvZ1NlcnZpY2VBcGlVcmwoKTogc3RyaW5nO1xyXG59XHJcbiJdfQ==