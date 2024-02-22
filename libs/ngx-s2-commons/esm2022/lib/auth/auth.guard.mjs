import { Injectable } from '@angular/core';
import { AuthEvent } from './auth.event';
import { AuthUtil } from './auth.util';
import * as i0 from "@angular/core";
class AuthGuard {
    canActivate(route, state) {
        return this.checkSession(state) && this.checkStatus(state);
    }
    canActivateChild(childRoute, state) {
        return this.canActivate(childRoute, state);
    }
    checkSession(state) {
        if (AuthUtil.isLoggedIn()) {
            return true;
        }
        AuthEvent.userLoggedIn.next({
            userLoggedIn: false,
            session: null,
            type: AuthEvent.AUTH_EVENT_NO_AUTH,
            returnUrl: state.url,
        });
        return false;
    }
    checkStatus(state) {
        return true;
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AuthGuard, deps: [], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AuthGuard });
}
export { AuthGuard };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: AuthGuard, decorators: [{
            type: Injectable
        }] });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYXV0aC5ndWFyZC5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uLy4uLy4uLy4uL3Byb2plY3RzL25neC1zMi1jb21tb25zL3NyYy9saWIvYXV0aC9hdXRoLmd1YXJkLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBLE9BQU8sRUFBRSxVQUFVLEVBQUUsTUFBTSxlQUFlLENBQUM7QUFHM0MsT0FBTyxFQUFFLFNBQVMsRUFBRSxNQUFNLGNBQWMsQ0FBQztBQUN6QyxPQUFPLEVBQUUsUUFBUSxFQUFFLE1BQU0sYUFBYSxDQUFDOztBQUV2QyxNQUNhLFNBQVM7SUFDcEIsV0FBVyxDQUNULEtBQTZCLEVBQzdCLEtBQTBCO1FBRTFCLE9BQU8sSUFBSSxDQUFDLFlBQVksQ0FBQyxLQUFLLENBQUMsSUFBSSxJQUFJLENBQUMsV0FBVyxDQUFDLEtBQUssQ0FBQyxDQUFDO0lBQzdELENBQUM7SUFFRCxnQkFBZ0IsQ0FDZCxVQUFrQyxFQUNsQyxLQUEwQjtRQUUxQixPQUFPLElBQUksQ0FBQyxXQUFXLENBQUMsVUFBVSxFQUFFLEtBQUssQ0FBQyxDQUFDO0lBQzdDLENBQUM7SUFFUyxZQUFZLENBQUMsS0FBMEI7UUFDL0MsSUFBSSxRQUFRLENBQUMsVUFBVSxFQUFFLEVBQUU7WUFDekIsT0FBTyxJQUFJLENBQUM7U0FDYjtRQUNELFNBQVMsQ0FBQyxZQUFZLENBQUMsSUFBSSxDQUFDO1lBQzFCLFlBQVksRUFBRSxLQUFLO1lBQ25CLE9BQU8sRUFBRSxJQUFJO1lBQ2IsSUFBSSxFQUFFLFNBQVMsQ0FBQyxrQkFBa0I7WUFDbEMsU0FBUyxFQUFFLEtBQUssQ0FBQyxHQUFHO1NBQ3JCLENBQUMsQ0FBQztRQUNILE9BQU8sS0FBSyxDQUFDO0lBQ2YsQ0FBQztJQUVTLFdBQVcsQ0FDbkIsS0FBMEI7UUFFMUIsT0FBTyxJQUFJLENBQUM7SUFDZCxDQUFDO3VHQWhDVSxTQUFTOzJHQUFULFNBQVM7O1NBQVQsU0FBUzsyRkFBVCxTQUFTO2tCQURyQixVQUFVIiwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHsgSW5qZWN0YWJsZSB9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xyXG5pbXBvcnQgeyBBY3RpdmF0ZWRSb3V0ZVNuYXBzaG90LCBDYW5BY3RpdmF0ZSwgQ2FuQWN0aXZhdGVDaGlsZCwgUm91dGVyU3RhdGVTbmFwc2hvdCwgVXJsVHJlZSB9IGZyb20gJ0Bhbmd1bGFyL3JvdXRlcic7XHJcbmltcG9ydCB7IE9ic2VydmFibGUgfSBmcm9tICdyeGpzJztcclxuaW1wb3J0IHsgQXV0aEV2ZW50IH0gZnJvbSAnLi9hdXRoLmV2ZW50JztcclxuaW1wb3J0IHsgQXV0aFV0aWwgfSBmcm9tICcuL2F1dGgudXRpbCc7XHJcblxyXG5ASW5qZWN0YWJsZSgpXHJcbmV4cG9ydCBjbGFzcyBBdXRoR3VhcmQgaW1wbGVtZW50cyBDYW5BY3RpdmF0ZSwgQ2FuQWN0aXZhdGVDaGlsZCB7XHJcbiAgY2FuQWN0aXZhdGUoXHJcbiAgICByb3V0ZTogQWN0aXZhdGVkUm91dGVTbmFwc2hvdCxcclxuICAgIHN0YXRlOiBSb3V0ZXJTdGF0ZVNuYXBzaG90XHJcbiAgKTogYm9vbGVhbiB8IFVybFRyZWUgfCBPYnNlcnZhYmxlPGJvb2xlYW4gfCBVcmxUcmVlPiB8IFByb21pc2U8Ym9vbGVhbiB8IFVybFRyZWU+IHtcclxuICAgIHJldHVybiB0aGlzLmNoZWNrU2Vzc2lvbihzdGF0ZSkgJiYgdGhpcy5jaGVja1N0YXR1cyhzdGF0ZSk7XHJcbiAgfVxyXG5cclxuICBjYW5BY3RpdmF0ZUNoaWxkKFxyXG4gICAgY2hpbGRSb3V0ZTogQWN0aXZhdGVkUm91dGVTbmFwc2hvdCxcclxuICAgIHN0YXRlOiBSb3V0ZXJTdGF0ZVNuYXBzaG90XHJcbiAgKTogYm9vbGVhbiB8IFVybFRyZWUgfCBPYnNlcnZhYmxlPGJvb2xlYW4gfCBVcmxUcmVlPiB8IFByb21pc2U8Ym9vbGVhbiB8IFVybFRyZWU+IHtcclxuICAgIHJldHVybiB0aGlzLmNhbkFjdGl2YXRlKGNoaWxkUm91dGUsIHN0YXRlKTtcclxuICB9XHJcblxyXG4gIHByb3RlY3RlZCBjaGVja1Nlc3Npb24oc3RhdGU6IFJvdXRlclN0YXRlU25hcHNob3QpOiBib29sZWFuIHtcclxuICAgIGlmIChBdXRoVXRpbC5pc0xvZ2dlZEluKCkpIHtcclxuICAgICAgcmV0dXJuIHRydWU7XHJcbiAgICB9XHJcbiAgICBBdXRoRXZlbnQudXNlckxvZ2dlZEluLm5leHQoe1xyXG4gICAgICB1c2VyTG9nZ2VkSW46IGZhbHNlLFxyXG4gICAgICBzZXNzaW9uOiBudWxsLFxyXG4gICAgICB0eXBlOiBBdXRoRXZlbnQuQVVUSF9FVkVOVF9OT19BVVRILFxyXG4gICAgICByZXR1cm5Vcmw6IHN0YXRlLnVybCxcclxuICAgIH0pO1xyXG4gICAgcmV0dXJuIGZhbHNlO1xyXG4gIH1cclxuXHJcbiAgcHJvdGVjdGVkIGNoZWNrU3RhdHVzKFxyXG4gICAgc3RhdGU6IFJvdXRlclN0YXRlU25hcHNob3RcclxuICApOiBib29sZWFuIHwgVXJsVHJlZSB8IE9ic2VydmFibGU8Ym9vbGVhbiB8IFVybFRyZWU+IHwgUHJvbWlzZTxib29sZWFuIHwgVXJsVHJlZT4ge1xyXG4gICAgcmV0dXJuIHRydWU7XHJcbiAgfVxyXG59XHJcbiJdfQ==