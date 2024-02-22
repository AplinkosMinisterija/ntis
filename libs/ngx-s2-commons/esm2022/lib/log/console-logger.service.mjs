import { Injectable, isDevMode } from '@angular/core';
import * as i0 from "@angular/core";
const noop = () => undefined;
class ConsoleLoggerService {
    get debug() {
        if (isDevMode()) {
            return console.debug.bind(console);
        }
        else {
            return noop;
        }
    }
    get info() {
        return console.info.bind(console);
    }
    get warn() {
        return console.warn.bind(console);
    }
    get error() {
        return console.error.bind(console);
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ConsoleLoggerService, deps: [], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ConsoleLoggerService });
}
export { ConsoleLoggerService };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ConsoleLoggerService, decorators: [{
            type: Injectable
        }] });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY29uc29sZS1sb2dnZXIuc2VydmljZS5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uLy4uLy4uLy4uL3Byb2plY3RzL25neC1zMi1jb21tb25zL3NyYy9saWIvbG9nL2NvbnNvbGUtbG9nZ2VyLnNlcnZpY2UudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsT0FBTyxFQUFFLFVBQVUsRUFBRSxTQUFTLEVBQUUsTUFBTSxlQUFlLENBQUM7O0FBSXRELE1BQU0sSUFBSSxHQUFHLEdBQWMsRUFBRSxDQUFDLFNBQVMsQ0FBQztBQUV4QyxNQUNhLG9CQUFvQjtJQUMvQixJQUFJLEtBQUs7UUFDUCxJQUFJLFNBQVMsRUFBRSxFQUFFO1lBQ2YsT0FBTyxPQUFPLENBQUMsS0FBSyxDQUFDLElBQUksQ0FBQyxPQUFPLENBQUMsQ0FBQztTQUNwQzthQUFNO1lBQ0wsT0FBTyxJQUFJLENBQUM7U0FDYjtJQUNILENBQUM7SUFFRCxJQUFJLElBQUk7UUFDTixPQUFPLE9BQU8sQ0FBQyxJQUFJLENBQUMsSUFBSSxDQUFDLE9BQU8sQ0FBQyxDQUFDO0lBQ3BDLENBQUM7SUFFRCxJQUFJLElBQUk7UUFDTixPQUFPLE9BQU8sQ0FBQyxJQUFJLENBQUMsSUFBSSxDQUFDLE9BQU8sQ0FBQyxDQUFDO0lBQ3BDLENBQUM7SUFFRCxJQUFJLEtBQUs7UUFDUCxPQUFPLE9BQU8sQ0FBQyxLQUFLLENBQUMsSUFBSSxDQUFDLE9BQU8sQ0FBQyxDQUFDO0lBQ3JDLENBQUM7dUdBbkJVLG9CQUFvQjsyR0FBcEIsb0JBQW9COztTQUFwQixvQkFBb0I7MkZBQXBCLG9CQUFvQjtrQkFEaEMsVUFBVSIsInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7IEluamVjdGFibGUsIGlzRGV2TW9kZSB9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xyXG5cclxuaW1wb3J0IHsgTG9nZ2VyIH0gZnJvbSAnLi9sb2dnZXIuc2VydmljZSc7XHJcblxyXG5jb25zdCBub29wID0gKCk6IHVuZGVmaW5lZCA9PiB1bmRlZmluZWQ7XHJcblxyXG5ASW5qZWN0YWJsZSgpXHJcbmV4cG9ydCBjbGFzcyBDb25zb2xlTG9nZ2VyU2VydmljZSBpbXBsZW1lbnRzIExvZ2dlciB7XHJcbiAgZ2V0IGRlYnVnKCk6IENhbGxhYmxlRnVuY3Rpb24ge1xyXG4gICAgaWYgKGlzRGV2TW9kZSgpKSB7XHJcbiAgICAgIHJldHVybiBjb25zb2xlLmRlYnVnLmJpbmQoY29uc29sZSk7XHJcbiAgICB9IGVsc2Uge1xyXG4gICAgICByZXR1cm4gbm9vcDtcclxuICAgIH1cclxuICB9XHJcblxyXG4gIGdldCBpbmZvKCk6IENhbGxhYmxlRnVuY3Rpb24ge1xyXG4gICAgcmV0dXJuIGNvbnNvbGUuaW5mby5iaW5kKGNvbnNvbGUpO1xyXG4gIH1cclxuXHJcbiAgZ2V0IHdhcm4oKTogQ2FsbGFibGVGdW5jdGlvbiB7XHJcbiAgICByZXR1cm4gY29uc29sZS53YXJuLmJpbmQoY29uc29sZSk7XHJcbiAgfVxyXG5cclxuICBnZXQgZXJyb3IoKTogQ2FsbGFibGVGdW5jdGlvbiB7XHJcbiAgICByZXR1cm4gY29uc29sZS5lcnJvci5iaW5kKGNvbnNvbGUpO1xyXG4gIH1cclxufVxyXG4iXX0=