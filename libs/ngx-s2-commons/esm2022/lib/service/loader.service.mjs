import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import * as i0 from "@angular/core";
import * as i1 from "../message/app.messages";
class LoaderService {
    appMessages;
    showCount = 0;
    timeOutInterval;
    showLoader = new BehaviorSubject({ show: false });
    constructor(appMessages) {
        this.appMessages = appMessages;
    }
    show(message, dimmed) {
        if (this.showCount === 0) {
            this.showLoader.next({ show: true, message, dimmed });
        }
        this.showCount++;
    }
    hide() {
        if (this.showCount > 0) {
            this.showCount--;
        }
        if (this.showCount === 0) {
            this.showLoader.next({ show: false });
            if (this.timeOutInterval !== undefined) {
                clearTimeout(this.timeOutInterval);
                this.timeOutInterval = undefined;
            }
        }
    }
    showWithOptions({ message, dimmed, timeout, timeoutMessage, }) {
        this.show(message, dimmed);
        if (timeout) {
            this.timeOutInterval = setTimeout(() => {
                this.onTimeout(timeoutMessage);
            }, timeout);
        }
    }
    onTimeout(timeoutMessage) {
        this.hide();
        if (timeoutMessage) {
            this.appMessages.showError('', timeoutMessage);
        }
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: LoaderService, deps: [{ token: i1.AppMessages }], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: LoaderService, providedIn: 'root' });
}
export { LoaderService };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: LoaderService, decorators: [{
            type: Injectable,
            args: [{ providedIn: 'root' }]
        }], ctorParameters: function () { return [{ type: i1.AppMessages }]; } });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoibG9hZGVyLnNlcnZpY2UuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi8uLi8uLi8uLi8uLi9wcm9qZWN0cy9uZ3gtczItY29tbW9ucy9zcmMvbGliL3NlcnZpY2UvbG9hZGVyLnNlcnZpY2UudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsT0FBTyxFQUFFLFVBQVUsRUFBRSxNQUFNLGVBQWUsQ0FBQztBQUMzQyxPQUFPLEVBQUUsZUFBZSxFQUFFLE1BQU0sTUFBTSxDQUFDOzs7QUFTdkMsTUFDYSxhQUFhO0lBS0o7SUFKWixTQUFTLEdBQUcsQ0FBQyxDQUFDO0lBQ2QsZUFBZSxDQUFnQztJQUN2RCxVQUFVLEdBQWtDLElBQUksZUFBZSxDQUFlLEVBQUUsSUFBSSxFQUFFLEtBQUssRUFBRSxDQUFDLENBQUM7SUFFL0YsWUFBb0IsV0FBd0I7UUFBeEIsZ0JBQVcsR0FBWCxXQUFXLENBQWE7SUFBRyxDQUFDO0lBRWhELElBQUksQ0FBQyxPQUFnQixFQUFFLE1BQWdCO1FBQ3JDLElBQUksSUFBSSxDQUFDLFNBQVMsS0FBSyxDQUFDLEVBQUU7WUFDeEIsSUFBSSxDQUFDLFVBQVUsQ0FBQyxJQUFJLENBQUMsRUFBRSxJQUFJLEVBQUUsSUFBSSxFQUFFLE9BQU8sRUFBRSxNQUFNLEVBQUUsQ0FBQyxDQUFDO1NBQ3ZEO1FBQ0QsSUFBSSxDQUFDLFNBQVMsRUFBRSxDQUFDO0lBQ25CLENBQUM7SUFFRCxJQUFJO1FBQ0YsSUFBSSxJQUFJLENBQUMsU0FBUyxHQUFHLENBQUMsRUFBRTtZQUN0QixJQUFJLENBQUMsU0FBUyxFQUFFLENBQUM7U0FDbEI7UUFDRCxJQUFJLElBQUksQ0FBQyxTQUFTLEtBQUssQ0FBQyxFQUFFO1lBQ3hCLElBQUksQ0FBQyxVQUFVLENBQUMsSUFBSSxDQUFDLEVBQUUsSUFBSSxFQUFFLEtBQUssRUFBRSxDQUFDLENBQUM7WUFDdEMsSUFBSSxJQUFJLENBQUMsZUFBZSxLQUFLLFNBQVMsRUFBRTtnQkFDdEMsWUFBWSxDQUFDLElBQUksQ0FBQyxlQUFlLENBQUMsQ0FBQztnQkFDbkMsSUFBSSxDQUFDLGVBQWUsR0FBRyxTQUFTLENBQUM7YUFDbEM7U0FDRjtJQUNILENBQUM7SUFFRCxlQUFlLENBQUMsRUFDZCxPQUFPLEVBQ1AsTUFBTSxFQUNOLE9BQU8sRUFDUCxjQUFjLEdBTWY7UUFDQyxJQUFJLENBQUMsSUFBSSxDQUFDLE9BQU8sRUFBRSxNQUFNLENBQUMsQ0FBQztRQUMzQixJQUFJLE9BQU8sRUFBRTtZQUNYLElBQUksQ0FBQyxlQUFlLEdBQUcsVUFBVSxDQUFDLEdBQUcsRUFBRTtnQkFDckMsSUFBSSxDQUFDLFNBQVMsQ0FBQyxjQUFjLENBQUMsQ0FBQztZQUNqQyxDQUFDLEVBQUUsT0FBTyxDQUFDLENBQUM7U0FDYjtJQUNILENBQUM7SUFFUyxTQUFTLENBQUMsY0FBdUI7UUFDekMsSUFBSSxDQUFDLElBQUksRUFBRSxDQUFDO1FBQ1osSUFBSSxjQUFjLEVBQUU7WUFDbEIsSUFBSSxDQUFDLFdBQVcsQ0FBQyxTQUFTLENBQUMsRUFBRSxFQUFFLGNBQWMsQ0FBQyxDQUFDO1NBQ2hEO0lBQ0gsQ0FBQzt1R0FuRFUsYUFBYTsyR0FBYixhQUFhLGNBREEsTUFBTTs7U0FDbkIsYUFBYTsyRkFBYixhQUFhO2tCQUR6QixVQUFVO21CQUFDLEVBQUUsVUFBVSxFQUFFLE1BQU0sRUFBRSIsInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7IEluamVjdGFibGUgfSBmcm9tICdAYW5ndWxhci9jb3JlJztcclxuaW1wb3J0IHsgQmVoYXZpb3JTdWJqZWN0IH0gZnJvbSAncnhqcyc7XHJcbmltcG9ydCB7IEFwcE1lc3NhZ2VzIH0gZnJvbSAnLi4vbWVzc2FnZS9hcHAubWVzc2FnZXMnO1xyXG5cclxuZXhwb3J0IGludGVyZmFjZSBMb2FkZXJQYXJhbXMge1xyXG4gIHNob3c6IGJvb2xlYW47XHJcbiAgZGltbWVkPzogYm9vbGVhbjtcclxuICBtZXNzYWdlPzogc3RyaW5nO1xyXG59XHJcblxyXG5ASW5qZWN0YWJsZSh7IHByb3ZpZGVkSW46ICdyb290JyB9KVxyXG5leHBvcnQgY2xhc3MgTG9hZGVyU2VydmljZSB7XHJcbiAgcHJpdmF0ZSBzaG93Q291bnQgPSAwO1xyXG4gIHByaXZhdGUgdGltZU91dEludGVydmFsOiBSZXR1cm5UeXBlPHR5cGVvZiBzZXRUaW1lb3V0PjtcclxuICBzaG93TG9hZGVyOiBCZWhhdmlvclN1YmplY3Q8TG9hZGVyUGFyYW1zPiA9IG5ldyBCZWhhdmlvclN1YmplY3Q8TG9hZGVyUGFyYW1zPih7IHNob3c6IGZhbHNlIH0pO1xyXG5cclxuICBjb25zdHJ1Y3Rvcihwcml2YXRlIGFwcE1lc3NhZ2VzOiBBcHBNZXNzYWdlcykge31cclxuXHJcbiAgc2hvdyhtZXNzYWdlPzogc3RyaW5nLCBkaW1tZWQ/OiBib29sZWFuKTogdm9pZCB7XHJcbiAgICBpZiAodGhpcy5zaG93Q291bnQgPT09IDApIHtcclxuICAgICAgdGhpcy5zaG93TG9hZGVyLm5leHQoeyBzaG93OiB0cnVlLCBtZXNzYWdlLCBkaW1tZWQgfSk7XHJcbiAgICB9XHJcbiAgICB0aGlzLnNob3dDb3VudCsrO1xyXG4gIH1cclxuXHJcbiAgaGlkZSgpOiB2b2lkIHtcclxuICAgIGlmICh0aGlzLnNob3dDb3VudCA+IDApIHtcclxuICAgICAgdGhpcy5zaG93Q291bnQtLTtcclxuICAgIH1cclxuICAgIGlmICh0aGlzLnNob3dDb3VudCA9PT0gMCkge1xyXG4gICAgICB0aGlzLnNob3dMb2FkZXIubmV4dCh7IHNob3c6IGZhbHNlIH0pO1xyXG4gICAgICBpZiAodGhpcy50aW1lT3V0SW50ZXJ2YWwgIT09IHVuZGVmaW5lZCkge1xyXG4gICAgICAgIGNsZWFyVGltZW91dCh0aGlzLnRpbWVPdXRJbnRlcnZhbCk7XHJcbiAgICAgICAgdGhpcy50aW1lT3V0SW50ZXJ2YWwgPSB1bmRlZmluZWQ7XHJcbiAgICAgIH1cclxuICAgIH1cclxuICB9XHJcblxyXG4gIHNob3dXaXRoT3B0aW9ucyh7XHJcbiAgICBtZXNzYWdlLFxyXG4gICAgZGltbWVkLFxyXG4gICAgdGltZW91dCxcclxuICAgIHRpbWVvdXRNZXNzYWdlLFxyXG4gIH06IHtcclxuICAgIG1lc3NhZ2U/OiBzdHJpbmc7XHJcbiAgICBkaW1tZWQ/OiBib29sZWFuO1xyXG4gICAgdGltZW91dD86IG51bWJlcjtcclxuICAgIHRpbWVvdXRNZXNzYWdlPzogc3RyaW5nO1xyXG4gIH0pOiB2b2lkIHtcclxuICAgIHRoaXMuc2hvdyhtZXNzYWdlLCBkaW1tZWQpO1xyXG4gICAgaWYgKHRpbWVvdXQpIHtcclxuICAgICAgdGhpcy50aW1lT3V0SW50ZXJ2YWwgPSBzZXRUaW1lb3V0KCgpID0+IHtcclxuICAgICAgICB0aGlzLm9uVGltZW91dCh0aW1lb3V0TWVzc2FnZSk7XHJcbiAgICAgIH0sIHRpbWVvdXQpO1xyXG4gICAgfVxyXG4gIH1cclxuXHJcbiAgcHJvdGVjdGVkIG9uVGltZW91dCh0aW1lb3V0TWVzc2FnZT86IHN0cmluZyk6IHZvaWQge1xyXG4gICAgdGhpcy5oaWRlKCk7XHJcbiAgICBpZiAodGltZW91dE1lc3NhZ2UpIHtcclxuICAgICAgdGhpcy5hcHBNZXNzYWdlcy5zaG93RXJyb3IoJycsIHRpbWVvdXRNZXNzYWdlKTtcclxuICAgIH1cclxuICB9XHJcbn1cclxuIl19