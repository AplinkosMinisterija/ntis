import { Component } from '@angular/core';
import { ActivationEnd, NavigationEnd } from '@angular/router';
import { AuthEvent } from './auth/auth.event';
import { AuthUtil } from './auth/auth.util';
import { initLang, setLang } from './i18n/lang.util';
import * as i0 from "@angular/core";
import * as i1 from "@angular/router";
import * as i2 from "./message/app.messages";
import * as i3 from "@ngx-translate/core";
class BaseAppComponent {
    router;
    appMessages;
    translate;
    route;
    ngZone;
    currentUser;
    paramsLang;
    constructor(router, appMessages, translate, route, ngZone) {
        this.router = router;
        this.appMessages = appMessages;
        this.translate = translate;
        this.route = route;
        this.ngZone = ngZone;
        this.router.events.subscribe((event) => {
            if (event instanceof ActivationEnd) {
                this.paramsLang = event.snapshot.queryParams['lang'];
                if (this.paramsLang) {
                    setLang(this.translate, this.paramsLang);
                }
            }
            initLang(this.translate);
        });
        AuthEvent.userLoggedIn.subscribe((res) => {
            if (res.type === AuthEvent.AUTH_EVENT_UPDATE) {
                this.onUserInfoUpdate(res.session);
            }
            else {
                if (res.userLoggedIn) {
                    this.setUser();
                    this.onLogin();
                    this.handleRegStatus(res);
                }
                else if (res.type !== AuthEvent.AUTH_EVENT_LOGOUT) {
                    this.clearUser();
                    this.onLogout();
                    this.ngZone.run(() => {
                        void this.router
                            .navigate([this.getLoginUrl()], { queryParams: { returnUrl: this.getReturnUrl(res) } })
                            .then(() => {
                            if (res.type === AuthEvent.AUTH_EVENT_401) {
                                this.showNoAuthWarning();
                            }
                        });
                    });
                }
                if (res.type === AuthEvent.AUTH_EVENT_LOGOUT) {
                    this.clearUser();
                    this.onLogout();
                }
            }
        });
    }
    // eslint-disable-next-line @typescript-eslint/no-empty-function
    onLogin() { }
    // eslint-disable-next-line @typescript-eslint/no-empty-function
    onLogout() { }
    // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
    handleRegStatus(regStatus) { }
    onUserInfoUpdate(session) {
        AuthUtil.updateSessionInfo(session);
        this.currentUser = session;
    }
    ngOnInit() {
        this.setUser();
        this.router.events.subscribe((val) => {
            if (val instanceof NavigationEnd) {
                this.appMessages.clearMessagesExceptSuccess();
            }
        });
    }
    clearUser() {
        this.currentUser = null;
        AuthUtil.clearSessionStorage();
    }
    setUser() {
        this.currentUser = AuthUtil.getSessionInfo();
        if (this.currentUser != null && this.currentUser.language != null) {
            setLang(this.translate, this.currentUser.language.toLowerCase());
        }
    }
    showNoAuthWarning() {
        this.translate.get('common.message.noSession').subscribe((translation) => {
            this.appMessages.showWarning('', translation, AuthEvent.AUTH_EVENT_NO_AUTH);
        });
    }
    getReturnUrl(authEvent) {
        return authEvent.returnUrl || '';
    }
    isLoggedIn() {
        return AuthUtil.isLoggedIn();
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BaseAppComponent, deps: [{ token: i1.Router }, { token: i2.AppMessages }, { token: i3.TranslateService }, { token: i1.ActivatedRoute }, { token: i0.NgZone }], target: i0.ɵɵFactoryTarget.Component });
    static ɵcmp = i0.ɵɵngDeclareComponent({ minVersion: "14.0.0", version: "16.0.2", type: BaseAppComponent, selector: "ng-component", ngImport: i0, template: '', isInline: true });
}
export { BaseAppComponent };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BaseAppComponent, decorators: [{
            type: Component,
            args: [{ template: '' }]
        }], ctorParameters: function () { return [{ type: i1.Router }, { type: i2.AppMessages }, { type: i3.TranslateService }, { type: i1.ActivatedRoute }, { type: i0.NgZone }]; } });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYmFzZS5hcHAuY29tcG9uZW50LmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vLi4vLi4vcHJvamVjdHMvbmd4LXMyLWNvbW1vbnMvc3JjL2xpYi9iYXNlLmFwcC5jb21wb25lbnQudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBR0EsT0FBTyxFQUFFLFNBQVMsRUFBa0IsTUFBTSxlQUFlLENBQUM7QUFDMUQsT0FBTyxFQUFrQixhQUFhLEVBQUUsYUFBYSxFQUFVLE1BQU0saUJBQWlCLENBQUM7QUFDdkYsT0FBTyxFQUFFLFNBQVMsRUFBRSxNQUFNLG1CQUFtQixDQUFDO0FBQzlDLE9BQU8sRUFBRSxRQUFRLEVBQUUsTUFBTSxrQkFBa0IsQ0FBQztBQUM1QyxPQUFPLEVBQUUsUUFBUSxFQUFFLE9BQU8sRUFBRSxNQUFNLGtCQUFrQixDQUFDOzs7OztBQUVyRCxNQUNzQixnQkFBZ0I7SUFLeEI7SUFDQTtJQUNBO0lBQ0E7SUFDQTtJQVJaLFdBQVcsQ0FBSTtJQUNmLFVBQVUsQ0FBUztJQUVuQixZQUNZLE1BQWMsRUFDZCxXQUF3QixFQUN4QixTQUEyQixFQUMzQixLQUFxQixFQUNyQixNQUFjO1FBSmQsV0FBTSxHQUFOLE1BQU0sQ0FBUTtRQUNkLGdCQUFXLEdBQVgsV0FBVyxDQUFhO1FBQ3hCLGNBQVMsR0FBVCxTQUFTLENBQWtCO1FBQzNCLFVBQUssR0FBTCxLQUFLLENBQWdCO1FBQ3JCLFdBQU0sR0FBTixNQUFNLENBQVE7UUFFeEIsSUFBSSxDQUFDLE1BQU0sQ0FBQyxNQUFNLENBQUMsU0FBUyxDQUFDLENBQUMsS0FBSyxFQUFFLEVBQUU7WUFDckMsSUFBSSxLQUFLLFlBQVksYUFBYSxFQUFFO2dCQUNsQyxJQUFJLENBQUMsVUFBVSxHQUFHLEtBQUssQ0FBQyxRQUFRLENBQUMsV0FBVyxDQUFDLE1BQU0sQ0FBVyxDQUFDO2dCQUMvRCxJQUFJLElBQUksQ0FBQyxVQUFVLEVBQUU7b0JBQ25CLE9BQU8sQ0FBQyxJQUFJLENBQUMsU0FB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QyxXQUFXLEVBQUUsQ0FBQyxDQUFDO1NBQ2xFO0lBQ0gsQ0FBQztJQUVPLGlCQUFpQjtRQUN2QixJQUFJLENBQUMsU0FBUyxDQUFDLEdBQUcsQ0FBQywwQkFBMEIsQ0FBQyxDQUFDLFNBQVMsQ0FBQyxDQUFDLFdBQW1CLEVBQUUsRUFBRTtZQUMvRSxJQUFJLENBQUMsV0FBVyxDQUFDLFdBQVcsQ0FBQyxFQUFFLEVBQUUsV0FBVyxFQUFFLFNBQVMsQ0FBQyxrQkFBa0IsQ0FBQyxDQUFDO1FBQzlFLENBQUMsQ0FBQyxDQUFDO0lBQ0wsQ0FBQztJQUVELFlBQVksQ0FBQyxTQUFvQjtRQUMvQixPQUFPLFNBQVMsQ0FBQyxTQUFTLElBQUksRUFBRSxDQUFDO0lBQ25DLENBQUM7SUFFRCxVQUFVO1FBQ1IsT0FBTyxRQUFRLENBQUMsVUFBVSxFQUFFLENBQUM7SUFDL0IsQ0FBQzt1R0FuR21CLGdCQUFnQjsyRkFBaEIsZ0JBQWdCLG9EQURmLEVBQUU7O1NBQ0gsZ0JBQWdCOzJGQUFoQixnQkFBZ0I7a0JBRHJDLFNBQVM7bUJBQUMsRUFBRSxRQUFRLEVBQUUsRUFBRSxFQUFFIiwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHsgV2ViU2Vzc2lvbkluZm8gfSBmcm9tICcuL21vZGVsL2NvbW1vbi5hcGknO1xyXG5pbXBvcnQgeyBUcmFuc2xhdGVTZXJ2aWNlIH0gZnJvbSAnQG5neC10cmFuc2xhdGUvY29yZSc7XHJcbmltcG9ydCB7IEFwcE1lc3NhZ2VzIH0gZnJvbSAnLi9tZXNzYWdlL2FwcC5tZXNzYWdlcyc7XHJcbmltcG9ydCB7IENvbXBvbmVudCwgTmdab25lLCBPbkluaXQgfSBmcm9tICdAYW5ndWxhci9jb3JlJztcclxuaW1wb3J0IHsgQWN0aXZhdGVkUm91dGUsIEFjdGl2YXRpb25FbmQsIE5hdmlnYXRpb25FbmQsIFJvdXRlciB9IGZyb20gJ0Bhbmd1bGFyL3JvdXRlcic7XHJcbmltcG9ydCB7IEF1dGhFdmVudCB9IGZyb20gJy4vYXV0aC9hdXRoLmV2ZW50JztcclxuaW1wb3J0IHsgQXV0aFV0aWwgfSBmcm9tICcuL2F1dGgvYXV0aC51dGlsJztcclxuaW1wb3J0IHsgaW5pdExhbmcsIHNldExhbmcgfSBmcm9tICcuL2kxOG4vbGFuZy51dGlsJztcclxuXHJcbkBDb21wb25lbnQoeyB0ZW1wbGF0ZTogJycgfSlcclxuZXhwb3J0IGFic3RyYWN0IGNsYXNzIEJhc2VBcHBDb21wb25lbnQ8VCBleHRlbmRzIFdlYlNlc3Npb25JbmZvPiBpbXBsZW1lbnRzIE9uSW5pdCB7XHJcbiAgY3VycmVudFVzZXI6IFQ7XHJcbiAgcGFyYW1zTGFuZzogc3RyaW5nO1xyXG5cclxuICBjb25zdHJ1Y3RvcihcclxuICAgIHByb3RlY3RlZCByb3V0ZXI6IFJvdXRlcixcclxuICAgIHByb3RlY3RlZCBhcHBNZXNzYWdlczogQXBwTWVzc2FnZXMsXHJcbiAgICBwcm90ZWN0ZWQgdHJhbnNsYXRlOiBUcmFuc2xhdGVTZXJ2aWNlLFxyXG4gICAgcHJvdGVjdGVkIHJvdXRlOiBBY3RpdmF0ZWRSb3V0ZSxcclxuICAgIHByb3RlY3RlZCBuZ1pvbmU6IE5nWm9uZVxyXG4gICkge1xyXG4gICAgdGhpcy5yb3V0ZXIuZXZlbnRzLnN1YnNjcmliZSgoZXZlbnQpID0+IHtcclxuICAgICAgaWYgKGV2ZW50IGluc3RhbmNlb2YgQWN0aXZhdGlvbkVuZCkge1xyXG4gICAgICAgIHRoaXMucGFyYW1zTGFuZyA9IGV2ZW50LnNuYXBzaG90LnF1ZXJ5UGFyYW1zWydsYW5nJ10gYXMgc3RyaW5nO1xyXG4gICAgICAgIGlmICh0aGlzLnBhcmFtc0xhbmcpIHtcclxuICAgICAgICAgIHNldExhbmcodGhpcy50cmFuc2xhdGUsIHRoaXMucGFyYW1zTGFuZyk7XHJcbiAgICAgICAgfVxyXG4gICAgICB9XHJcbiAgICAgIGluaXRMYW5nKHRoaXMudHJhbnNsYXRlKTtcclxuICAgIH0pO1xyXG5cclxuICAgIEF1dGhFdmVudC51c2VyTG9nZ2VkSW4uc3Vic2NyaWJlKChyZXM6IEF1dGhFdmVudCkgPT4ge1xyXG4gICAgICBpZiAocmVzLnR5cGUgPT09IEF1dGhFdmVudC5BVVRIX0VWRU5UX1VQREFURSkge1xyXG4gICAgICAgIHRoaXMub25Vc2VySW5mb1VwZGF0ZShyZXMuc2Vzc2lvbiBhcyBUKTtcclxuICAgICAgfSBlbHNlIHtcclxuICAgICAgICBpZiAocmVzLnVzZXJMb2dnZWRJbikge1xyXG4gICAgICAgICAgdGhpcy5zZXRVc2VyKCk7XHJcbiAgICAgICAgICB0aGlzLm9uTG9naW4oKTtcclxuICAgICAgICAgIHRoaXMuaGFuZGxlUmVnU3RhdHVzKHJlcyk7XHJcbiAgICAgICAgfSBlbHNlIGlmIChyZXMudHlwZSAhPT0gQXV0aEV2ZW50LkFVVEhfRVZFTlRfTE9HT1VUKSB7XHJcbiAgICAgICAgICB0aGlzLmNsZWFyVXNlcigpO1xyXG4gICAgICAgICAgdGhpcy5vbkxvZ291dCgpO1xyXG4gICAgICAgICAgdGhpcy5uZ1pvbmUucnVuKCgpID0+IHtcclxuICAgICAgICAgICAgdm9pZCB0aGlzLnJvdXRlclxyXG4gICAgICAgICAgICAgIC5uYXZpZ2F0ZShbdGhpcy5nZXRMb2dpblVybCgpXSwgeyBxdWVyeVBhcmFtczogeyByZXR1cm5Vcmw6IHRoaXMuZ2V0UmV0dXJuVXJsKHJlcykgfSB9KVxyXG4gICAgICAgICAgICAgIC50aGVuKCgpID0+IHtcclxuICAgICAgICAgICAgICAgIGlmIChyZXMudHlwZSA9PT0gQXV0aEV2ZW50LkFVVEhfRVZFTlRfNDAxKSB7XHJcbiAgICAgICAgICAgICAgICAgIHRoaXMuc2hvd05vQXV0aFdhcm5pbmcoKTtcclxuICAgICAgICAgICAgICAgIH1cclxuICAgICAgICAgICAgICB9KTtcclxuICAgICAgICAgIH0pO1xyXG4gICAgICAgIH1cclxuICAgICAgICBpZiAocmVzLnR5cGUgPT09IEF1dGhFdmVudC5BVVRIX0VWRU5UX0xPR09VVCkge1xyXG4gICAgICAgICAgdGhpcy5jbGVhclVzZXIoKTtcclxuICAgICAgICAgIHRoaXMub25Mb2dvdXQoKTtcclxuICAgICAgICB9XHJcbiAgICAgIH1cclxuICAgIH0pO1xyXG4gIH1cclxuXHJcbiAgLy8gZXNsaW50LWRpc2FibGUtbmV4dC1saW5lIEB0eXBlc2NyaXB0LWVzbGludC9uby1lbXB0eS1mdW5jdGlvblxyXG4gIHByb3RlY3RlZCBvbkxvZ2luKCk6IHZvaWQge31cclxuXHJcbiAgLy8gZXNsaW50LWRpc2FibGUtbmV4dC1saW5lIEB0eXBlc2NyaXB0LWVzbGludC9uby1lbXB0eS1mdW5jdGlvblxyXG4gIHByb3RlY3RlZCBvbkxvZ291dCgpOiB2b2lkIHt9XHJcblxyXG4gIC8vIGVzbGludC1kaXNhYmxlLW5leHQtbGluZSBAdHlwZXNjcmlwdC1lc2xpbnQvbm8tZW1wdHktZnVuY3Rpb24sIEB0eXBlc2NyaXB0LWVzbGludC9uby11bnVzZWQtdmFyc1xyXG4gIHByb3RlY3RlZCBoYW5kbGVSZWdTdGF0dXMocmVnU3RhdHVzOiBBdXRoRXZlbnQpOiB2b2lkIHt9XHJcblxyXG4gIHByb3RlY3RlZCBvblVzZXJJbmZvVXBkYXRlKHNlc3Npb246IFQpOiB2b2lkIHtcclxuICAgIEF1dGhVdGlsLnVwZGF0ZVNlc3Npb25JbmZvKHNlc3Npb24pO1xyXG4gICAgdGhpcy5jdXJyZW50VXNlciA9IHNlc3Npb247XHJcbiAgfVxyXG5cclxuICBwcm90ZWN0ZWQgYWJzdHJhY3QgZ2V0TG9naW5VcmwoKTogc3RyaW5nO1xyXG5cclxuICBuZ09uSW5pdCgpOiB2b2lkIHtcclxuICAgIHRoaXMuc2V0VXNlcigpO1xyXG4gICAgdGhpcy5yb3V0ZXIuZXZlbnRzLnN1YnNjcmliZSgodmFsKSA9PiB7XHJcbiAgICAgIGlmICh2YWwgaW5zdGFuY2VvZiBOYXZpZ2F0aW9uRW5kKSB7XHJcbiAgICAgICAgdGhpcy5hcHBNZXNzYWdlcy5jbGVhck1lc3NhZ2VzRXhjZXB0U3VjY2VzcygpO1xyXG4gICAgICB9XHJcbiAgICB9KTtcclxuICB9XHJcblxyXG4gIHByb3RlY3RlZCBjbGVhclVzZXIoKTogdm9pZCB7XHJcbiAgICB0aGlzLmN1cnJlbnRVc2VyID0gbnVsbDtcclxuICAgIEF1dGhVdGlsLmNsZWFyU2Vzc2lvblN0b3JhZ2UoKTtcclxuICB9XHJcblxyXG4gIHByb3RlY3RlZCBzZXRVc2VyKCk6IHZvaWQge1xyXG4gICAgdGhpcy5jdXJyZW50VXNlciA9IEF1dGhVdGlsLmdldFNlc3Npb25JbmZvKCkgYXMgVDtcclxuICAgIGlmICh0aGlzLmN1cnJlbnRVc2VyICE9IG51bGwgJiYgdGhpcy5jdXJyZW50VXNlci5sYW5ndWFnZSAhPSBudWxsKSB7XHJcbiAgICAgIHNldExhbmcodGhpcy50cmFuc2xhdGUsIHRoaXMuY3VycmVudFVzZXIubGFuZ3VhZ2UudG9Mb3dlckNhc2UoKSk7XHJcbiAgICB9XHJcbiAgfVxyXG5cclxuICBwcml2YXRlIHNob3dOb0F1dGhXYXJuaW5nKCk6IHZvaWQge1xyXG4gICAgdGhpcy50cmFuc2xhdGUuZ2V0KCdjb21tb24ubWVzc2FnZS5ub1Nlc3Npb24nKS5zdWJzY3JpYmUoKHRyYW5zbGF0aW9uOiBzdHJpbmcpID0+IHtcclxuICAgICAgdGhpcy5hcHBNZXNzYWdlcy5zaG93V2FybmluZygnJywgdHJhbnNsYXRpb24sIEF1dGhFdmVudC5BVVRIX0VWRU5UX05PX0FVVEgpO1xyXG4gICAgfSk7XHJcbiAgfVxyXG5cclxuICBnZXRSZXR1cm5VcmwoYXV0aEV2ZW50OiBBdXRoRXZlbnQpOiBzdHJpbmcge1xyXG4gICAgcmV0dXJuIGF1dGhFdmVudC5yZXR1cm5VcmwgfHwgJyc7XHJcbiAgfVxyXG5cclxuICBpc0xvZ2dlZEluKCk6IGJvb2xlYW4ge1xyXG4gICAgcmV0dXJuIEF1dGhVdGlsLmlzTG9nZ2VkSW4oKTtcclxuICB9XHJcbn1cclxuIl19