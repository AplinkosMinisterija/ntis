import { Injectable } from '@angular/core';
import * as i0 from "@angular/core";
class S2UiPopupMessagesService {
    messages = [];
    add(message) {
        this.messages.push(message);
        if (message.duration === undefined || message.duration > 0) {
            setTimeout(() => {
                this.remove(message);
            }, message.duration || 5000);
        }
    }
    addAll(messages) {
        messages.forEach((message) => {
            this.add(message);
        });
    }
    remove(message) {
        if (message && this.messages.includes(message)) {
            this.messages.splice(this.messages.indexOf(message), 1);
            return true;
        }
        return false;
    }
    removeAt(index) {
        if (this.messages[index]) {
            this.messages.splice(index, 1);
            return true;
        }
        return false;
    }
    removeByKey(key) {
        const message = this.messages.find((message) => message.key === key);
        if (message) {
            this.messages.splice(this.messages.indexOf(message), 1);
            return true;
        }
        return false;
    }
    clear() {
        this.messages.splice(0, this.messages.length);
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2UiPopupMessagesService, deps: [], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2UiPopupMessagesService, providedIn: 'root' });
}
export { S2UiPopupMessagesService };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2UiPopupMessagesService, decorators: [{
            type: Injectable,
            args: [{
                    providedIn: 'root',
                }]
        }] });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiczItdWktcG9wdXAtbWVzc2FnZXMuc2VydmljZS5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uLy4uLy4uLy4uL3Byb2plY3RzL25neC1zMi11aS9zcmMvbGliL3NlcnZpY2VzL3MyLXVpLXBvcHVwLW1lc3NhZ2VzLnNlcnZpY2UudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsT0FBTyxFQUFFLFVBQVUsRUFBRSxNQUFNLGVBQWUsQ0FBQzs7QUFjM0MsTUFHYSx3QkFBd0I7SUFDbkMsUUFBUSxHQUF1QixFQUFFLENBQUM7SUFFbEMsR0FBRyxDQUFDLE9BQXlCO1FBQzNCLElBQUksQ0FBQyxRQUFRLENBQUMsSUFBSSxDQUFDLE9BQU8sQ0FBQyxDQUFDO1FBQzVCLElBQUksT0FBTyxDQUFDLFFBQVEsS0FBSyxTQUFTLElBQUksT0FBTyxDQUFDLFFBQVEsR0FBRyxDQUFDLEVBQUU7WUFDMUQsVUFBVSxDQUFDLEdBQUcsRUFBRTtnQkFDZCxJQUFJLENBQUMsTUFBTSxDQUFDLE9BQU8sQ0FBQyxDQUFDO1lBQ3ZCLENBQUMsRUFBRSxPQUFPLENBQUMsUUFBUSxJQUFJLElBQUksQ0FBQyxDQUFDO1NBQzlCO0lBQ0gsQ0FBQztJQUVELE1BQU0sQ0FBQyxRQUE0QjtRQUNqQyxRQUFRLENBQUMsT0FBTyxDQUFDLENBQUMsT0FBTyxFQUFFLEVBQUU7WUFDM0IsSUFBSSxDQUFDLEdBQUcsQ0FBQyxPQUFPLENBQUMsQ0FBQztRQUNwQixDQUFDLENBQUMsQ0FBQztJQUNMLENBQUM7SUFFRCxNQUFNLENBQUMsT0FBeUI7UUFDOUIsSUFBSSxPQUFPLElBQUksSUFBSSxDQUFDLFFBQVEsQ0FBQyxRQUFRLENBQUMsT0FBTyxDQUFDLEVBQUU7WUFDOUMsSUFBSSxDQUFDLFFBQVEsQ0FBQyxNQUFNLENBQUMsSUFBSSxDQUFDLFFBQVEsQ0FBQyxPQUFPLENBQUMsT0FBTyxDQUFDLEVBQUUsQ0FBQyxDQUFDLENBQUM7WUFDeEQsT0FBTyxJQUFJLENBQUM7U0FDYjtRQUNELE9BQU8sS0FBSyxDQUFDO0lBQ2YsQ0FBQztJQUVELFFBQVEsQ0FBQyxLQUFhO1FBQ3BCLElBQUksSUFBSSxDQUFDLFFBQVEsQ0FBQyxLQUFLLENBQUMsRUFBRTtZQUN4QixJQUFJLENBQUMsUUFBUSxDQUFDLE1BQU0sQ0FBQyxLQUFLLEVBQUUsQ0FBQyxDQUFDLENBQUM7WUFDL0IsT0FBTyxJQUFJLENBQUM7U0FDYjtRQUNELE9BQU8sS0FBSyxDQUFDO0lBQ2YsQ0FBQztJQUVELFdBQVcsQ0FBQyxHQUE0QjtRQUN0QyxNQUFNLE9BQU8sR0FBRyxJQUFJLENBQUMsUUFBUSxDQUFDLElBQUksQ0FBQyxDQUFDLE9BQU8sRUFBRSxFQUFFLENBQUMsT0FBTyxDQUFDLEdBQUcsS0FBSyxHQUFHLENBQUMsQ0FBQztRQUNyRSxJQUFJLE9BQU8sRUFBRTtZQUNYLElBQUksQ0FBQyxRQUFRLENBQUMsTUFBTSxDQUFDLElBQUksQ0FBQyxRQUFRLENBQUMsT0FBTyxDQUFDLE9BQU8sQ0FBQyxFQUFFLENBQUMsQ0FBQyxDQUFDO1lBQ3hELE9BQU8sSUFBSSxDQUFDO1NBQ2I7UUFDRCxPQUFPLEtBQUssQ0FBQztJQUNmLENBQUM7SUFFRCxLQUFLO1FBQ0gsSUFBSSxDQUFDLFFBQVEsQ0FBQyxNQUFNLENBQUMsQ0FBQyxFQUFFLElBQUksQ0FBQyxRQUFRLENBQUMsTUFBTSxDQUFDLENBQUM7SUFDaEQsQ0FBQzt1R0E3Q1Usd0JBQXdCOzJHQUF4Qix3QkFBd0IsY0FGdkIsTUFBTTs7U0FFUCx3QkFBd0I7MkZBQXhCLHdCQUF3QjtrQkFIcEMsVUFBVTttQkFBQztvQkFDVixVQUFVLEVBQUUsTUFBTTtpQkFDbkIiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBJbmplY3RhYmxlIH0gZnJvbSAnQGFuZ3VsYXIvY29yZSc7XHJcblxyXG5leHBvcnQgaW50ZXJmYWNlIFMyVWlQb3B1cE1lc3NhZ2U8VCA9IHVua25vd24+IHtcclxuICBjbG9zYWJsZT86IGJvb2xlYW47XHJcbiAgY29udGFpbmVyS2V5Pzogc3RyaW5nIHwgbnVtYmVyO1xyXG4gIGRhdGE/OiBUO1xyXG4gIGR1cmF0aW9uPzogbnVtYmVyO1xyXG4gIGhlYWRpbmc/OiBzdHJpbmcgfCBudWxsO1xyXG4gIGljb24/OiBzdHJpbmc7XHJcbiAga2V5Pzogc3RyaW5nIHwgbnVtYmVyO1xyXG4gIHNldmVyaXR5OiAnZXJyb3InIHwgJ2luZm8nIHwgJ3N1Y2Nlc3MnIHwgJ3dhcm5pbmcnIHwgbnVsbDtcclxuICB0ZXh0Pzogc3RyaW5nO1xyXG59XHJcblxyXG5ASW5qZWN0YWJsZSh7XHJcbiAgcHJvdmlkZWRJbjogJ3Jvb3QnLFxyXG59KVxyXG5leHBvcnQgY2xhc3MgUzJVaVBvcHVwTWVzc2FnZXNTZXJ2aWNlIHtcclxuICBtZXNzYWdlczogUzJVaVBvcHVwTWVzc2FnZVtdID0gW107XHJcblxyXG4gIGFkZChtZXNzYWdlOiBTMlVpUG9wdXBNZXNzYWdlKTogdm9pZCB7XHJcbiAgICB0aGlzLm1lc3NhZ2VzLnB1c2gobWVzc2FnZSk7XHJcbiAgICBpZiAobWVzc2FnZS5kdXJhdGlvbiA9PT0gdW5kZWZpbmVkIHx8IG1lc3NhZ2UuZHVyYXRpb24gPiAwKSB7XHJcbiAgICAgIHNldFRpbWVvdXQoKCkgPT4ge1xyXG4gICAgICAgIHRoaXMucmVtb3ZlKG1lc3NhZ2UpO1xyXG4gICAgICB9LCBtZXNzYWdlLmR1cmF0aW9uIHx8IDUwMDApO1xyXG4gICAgfVxyXG4gIH1cclxuXHJcbiAgYWRkQWxsKG1lc3NhZ2VzOiBTMlVpUG9wdXBNZXNzYWdlW10pOiB2b2lkIHtcclxuICAgIG1lc3NhZ2VzLmZvckVhY2goKG1lc3NhZ2UpID0+IHtcclxuICAgICAgdGhpcy5hZGQobWVzc2FnZSk7XHJcbiAgICB9KTtcclxuICB9XHJcblxyXG4gIHJlbW92ZShtZXNzYWdlOiBTMlVpUG9wdXBNZXNzYWdlKTogYm9vbGVhbiB7XHJcbiAgICBpZiAobWVzc2FnZSAmJiB0aGlzLm1lc3NhZ2VzLmluY2x1ZGVzKG1lc3NhZ2UpKSB7XHJcbiAgICAgIHRoaXMubWVzc2FnZXMuc3BsaWNlKHRoaXMubWVzc2FnZXMuaW5kZXhPZihtZXNzYWdlKSwgMSk7XHJcbiAgICAgIHJldHVybiB0cnVlO1xyXG4gICAgfVxyXG4gICAgcmV0dXJuIGZhbHNlO1xyXG4gIH1cclxuXHJcbiAgcmVtb3ZlQXQoaW5kZXg6IG51bWJlcik6IGJvb2xlYW4ge1xyXG4gICAgaWYgKHRoaXMubWVzc2FnZXNbaW5kZXhdKSB7XHJcbiAgICAgIHRoaXMubWVzc2FnZXMuc3BsaWNlKGluZGV4LCAxKTtcclxuICAgICAgcmV0dXJuIHRydWU7XHJcbiAgICB9XHJcbiAgICByZXR1cm4gZmFsc2U7XHJcbiAgfVxyXG5cclxuICByZW1vdmVCeUtleShrZXk6IFMyVWlQb3B1cE1lc3NhZ2VbJ2tleSddKTogYm9vbGVhbiB7XHJcbiAgICBjb25zdCBtZXNzYWdlID0gdGhpcy5tZXNzYWdlcy5maW5kKChtZXNzYWdlKSA9PiBtZXNzYWdlLmtleSA9PT0ga2V5KTtcclxuICAgIGlmIChtZXNzYWdlKSB7XHJcbiAgICAgIHRoaXMubWVzc2FnZXMuc3BsaWNlKHRoaXMubWVzc2FnZXMuaW5kZXhPZihtZXNzYWdlKSwgMSk7XHJcbiAgICAgIHJldHVybiB0cnVlO1xyXG4gICAgfVxyXG4gICAgcmV0dXJuIGZhbHNlO1xyXG4gIH1cclxuXHJcbiAgY2xlYXIoKTogdm9pZCB7XHJcbiAgICB0aGlzLm1lc3NhZ2VzLnNwbGljZSgwLCB0aGlzLm1lc3NhZ2VzLmxlbmd0aCk7XHJcbiAgfVxyXG59XHJcbiJdfQ==