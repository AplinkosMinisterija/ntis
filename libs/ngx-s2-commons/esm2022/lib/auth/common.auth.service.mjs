import { of } from 'rxjs';
import { Injectable } from '@angular/core';
import { AuthEvent } from './auth.event';
import { AuthUtil } from './auth.util';
import * as i0 from "@angular/core";
import * as i1 from "@angular/common/http";
class CommonAuthService {
    http;
    constructor(http) {
        this.http = http;
    }
    processLogin(response, returnUrl, checkForPasswordChangeToken = true) {
        if (response && response.session) {
            AuthUtil.setLoginResult(response);
            if (response.token && (!checkForPasswordChangeToken || !response.session.usrPasswordChangeToken)) {
                AuthUtil.setTermsAccepted(response.session?.usrTermsAccepted === 'Y');
                this.getRegistrationStatus().subscribe((regStatus) => {
                    AuthEvent.userLoggedIn.next({
                        userLoggedIn: true,
                        session: response.session,
                        type: AuthEvent.AUTH_EVENT_LOGIN,
                        regStatus,
                        returnUrl,
                    });
                });
            }
        }
    }
    getRegistrationStatus() {
        return of('ok');
    }
    processLogout() {
        AuthUtil.clearSessionStorage();
        AuthEvent.userLoggedIn.next({ userLoggedIn: false, session: null, type: AuthEvent.AUTH_EVENT_LOGOUT });
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CommonAuthService, deps: [{ token: i1.HttpClient }], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CommonAuthService, providedIn: 'root' });
}
export { CommonAuthService };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CommonAuthService, decorators: [{
            type: Injectable,
            args: [{
                    providedIn: 'root',
                }]
        }], ctorParameters: function () { return [{ type: i1.HttpClient }]; } });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY29tbW9uLmF1dGguc2VydmljZS5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uLy4uLy4uLy4uL3Byb2plY3RzL25neC1zMi1jb21tb25zL3NyYy9saWIvYXV0aC9jb21tb24uYXV0aC5zZXJ2aWNlLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBLE9BQU8sRUFBYyxFQUFFLEVBQUUsTUFBTSxNQUFNLENBQUM7QUFDdEMsT0FBTyxFQUFFLFVBQVUsRUFBRSxNQUFNLGVBQWUsQ0FBQztBQUMzQyxPQUFPLEVBQUUsU0FBUyxFQUFFLE1BQU0sY0FBYyxDQUFDO0FBRXpDLE9BQU8sRUFBRSxRQUFRLEVBQUUsTUFBTSxhQUFhLENBQUM7OztBQUd2QyxNQUdhLGlCQUFpQjtJQUNOO0lBQXRCLFlBQXNCLElBQWdCO1FBQWhCLFNBQUksR0FBSixJQUFJLENBQVk7SUFBRyxDQUFDO0lBRWhDLFlBQVksQ0FDcEIsUUFBcUMsRUFDckMsU0FBaUIsRUFDakIsOEJBQXVDLElBQUk7UUFFM0MsSUFBSSxRQUFRLElBQUksUUFBUSxDQUFDLE9BQU8sRUFBRTtZQUNoQyxRQUFRLENBQUMsY0FBYyxDQUFDLFFBQVEsQ0FBQyxDQUFDO1lBQ2xDLElBQUksUUFBUSxDQUFDLEtBQUssSUFBSSxDQUFDLENBQUMsMkJBQTJCLElBQUksQ0FBQyxRQUFRLENBQUMsT0FBTyxDQUFDLHNCQUFzQixDQUFDLEVBQUU7Z0JBQ2hHLFFBQVEsQ0FBQyxnQkFBZ0IsQ0FBQyxRQUFRLENBQUMsT0FBTyxFQUFFLGdCQUFnQixLQUFLLEdBQUcsQ0FBQyxDQUFDO2dCQUN0RSxJQUFJLENBQUMscUJBQXFCLEVBQUUsQ0FBQyxTQUFTLENBQUMsQ0FBQyxTQUFTLEVBQUUsRUFBRTtvQkFDbkQsU0FBUyxDQUFDLFlBQVksQ0FBQyxJQUFJLENBQUM7d0JBQzFCLFlBQVksRUFBRSxJQUFJO3dCQUNsQixPQUFPLEVBQUUsUUFBUSxDQUFDLE9BQU87d0JBQ3pCLElBQUksRUFBRSxTQUFTLENBQUMsZ0JBQWdCO3dCQUNoQyxTQUFTO3dCQUNULFNBQVM7cUJBQ1YsQ0FBQyxDQUFDO2dCQUNMLENBQUMsQ0FBQyxDQUFDO2FBQ0o7U0FDRjtJQUNILENBQUM7SUFFUyxxQkFBcUI7UUFDN0IsT0FBTyxFQUFFLENBQUMsSUFBSSxDQUFDLENBQUM7SUFDbEIsQ0FBQztJQUVTLGFBQWE7UUFDckIsUUFBUSxDQUFDLG1CQUFtQixFQUFFLENBQUM7UUFDL0IsU0FBUyxDQUFDLFlBQVksQ0FBQyxJQUFJLENBQUMsRUFBRSxZQUFZLEVBQUUsS0FBSyxFQUFFLE9BQU8sRUFBRSxJQUFJLEVBQUUsSUFBSSxFQUFFLFNBQVMsQ0FBQyxpQkFBaUIsRUFBRSxDQUFDLENBQUM7SUFDekcsQ0FBQzt1R0FoQ1UsaUJBQWlCOzJHQUFqQixpQkFBaUIsY0FGaEIsTUFBTTs7U0FFUCxpQkFBaUI7MkZBQWpCLGlCQUFpQjtrQkFIN0IsVUFBVTttQkFBQztvQkFDVixVQUFVLEVBQUUsTUFBTTtpQkFDbkIiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBPYnNlcnZhYmxlLCBvZiB9IGZyb20gJ3J4anMnO1xyXG5pbXBvcnQgeyBJbmplY3RhYmxlIH0gZnJvbSAnQGFuZ3VsYXIvY29yZSc7XHJcbmltcG9ydCB7IEF1dGhFdmVudCB9IGZyb20gJy4vYXV0aC5ldmVudCc7XHJcbmltcG9ydCB7IEh0dHBDbGllbnQgfSBmcm9tICdAYW5ndWxhci9jb21tb24vaHR0cCc7XHJcbmltcG9ydCB7IEF1dGhVdGlsIH0gZnJvbSAnLi9hdXRoLnV0aWwnO1xyXG5pbXBvcnQgeyBMb2dpblJlc3VsdCwgV2ViU2Vzc2lvbkluZm8gfSBmcm9tICcuLi9tb2RlbC9jb21tb24uYXBpJztcclxuXHJcbkBJbmplY3RhYmxlKHtcclxuICBwcm92aWRlZEluOiAncm9vdCcsXHJcbn0pXHJcbmV4cG9ydCBjbGFzcyBDb21tb25BdXRoU2VydmljZSB7XHJcbiAgY29uc3RydWN0b3IocHJvdGVjdGVkIGh0dHA6IEh0dHBDbGllbnQpIHt9XHJcblxyXG4gIHByb3RlY3RlZCBwcm9jZXNzTG9naW4oXHJcbiAgICByZXNwb25zZTogTG9naW5SZXN1bHQ8V2ViU2Vzc2lvbkluZm8+LFxyXG4gICAgcmV0dXJuVXJsOiBzdHJpbmcsXHJcbiAgICBjaGVja0ZvclBhc3N3b3JkQ2hhbmdlVG9rZW46IGJvb2xlYW4gPSB0cnVlXHJcbiAgKTogdm9pZCB7XHJcbiAgICBpZiAocmVzcG9uc2UgJiYgcmVzcG9uc2Uuc2Vzc2lvbikge1xyXG4gICAgICBBdXRoVXRpbC5zZXRMb2dpblJlc3VsdChyZXNwb25zZSk7XHJcbiAgICAgIGlmIChyZXNwb25zZS50b2tlbiAmJiAoIWNoZWNrRm9yUGFzc3dvcmRDaGFuZ2VUb2tlbiB8fCAhcmVzcG9uc2Uuc2Vzc2lvbi51c3JQYXNzd29yZENoYW5nZVRva2VuKSkge1xyXG4gICAgICAgIEF1dGhVdGlsLnNldFRlcm1zQWNjZXB0ZWQocmVzcG9uc2Uuc2Vzc2lvbj8udXNyVGVybXNBY2NlcHRlZCA9PT0gJ1knKTtcclxuICAgICAgICB0aGlzLmdldFJlZ2lzdHJhdGlvblN0YXR1cygpLnN1YnNjcmliZSgocmVnU3RhdHVzKSA9PiB7XHJcbiAgICAgICAgICBBdXRoRXZlbnQudXNlckxvZ2dlZEluLm5leHQoe1xyXG4gICAgICAgICAgICB1c2VyTG9nZ2VkSW46IHRydWUsXHJcbiAgICAgICAgICAgIHNlc3Npb246IHJlc3BvbnNlLnNlc3Npb24sXHJcbiAgICAgICAgICAgIHR5cGU6IEF1dGhFdmVudC5BVVRIX0VWRU5UX0xPR0lOLFxyXG4gICAgICAgICAgICByZWdTdGF0dXMsXHJcbiAgICAgICAgICAgIHJldHVyblVybCxcclxuICAgICAgICAgIH0pO1xyXG4gICAgICAgIH0pO1xyXG4gICAgICB9XHJcbiAgICB9XHJcbiAgfVxyXG5cclxuICBwcm90ZWN0ZWQgZ2V0UmVnaXN0cmF0aW9uU3RhdHVzKCk6IE9ic2VydmFibGU8c3RyaW5nPiB7XHJcbiAgICByZXR1cm4gb2YoJ29rJyk7XHJcbiAgfVxyXG5cclxuICBwcm90ZWN0ZWQgcHJvY2Vzc0xvZ291dCgpOiB2b2lkIHtcclxuICAgIEF1dGhVdGlsLmNsZWFyU2Vzc2lvblN0b3JhZ2UoKTtcclxuICAgIEF1dGhFdmVudC51c2VyTG9nZ2VkSW4ubmV4dCh7IHVzZXJMb2dnZWRJbjogZmFsc2UsIHNlc3Npb246IG51bGwsIHR5cGU6IEF1dGhFdmVudC5BVVRIX0VWRU5UX0xPR09VVCB9KTtcclxuICB9XHJcbn1cclxuIl19