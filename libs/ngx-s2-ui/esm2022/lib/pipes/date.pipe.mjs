import { Pipe } from '@angular/core';
import * as i0 from "@angular/core";
import * as i1 from "@angular/common";
import * as i2 from "../services/s2-ui-settings.service";
class S2DatePipe {
    datePipe;
    settingsService;
    constructor(datePipe, settingsService) {
        this.datePipe = datePipe;
        this.settingsService = settingsService;
    }
    transform(value, showTime = false, ...options) {
        return this.datePipe.transform(value, this.settingsService.settings[showTime ? 'dateTimeFormat' : 'dateFormat'], ...options);
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2DatePipe, deps: [{ token: i1.DatePipe }, { token: i2.S2UiSettingsService }], target: i0.ɵɵFactoryTarget.Pipe });
    static ɵpipe = i0.ɵɵngDeclarePipe({ minVersion: "14.0.0", version: "16.0.2", ngImport: i0, type: S2DatePipe, isStandalone: true, name: "s2Date" });
}
export { S2DatePipe };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2DatePipe, decorators: [{
            type: Pipe,
            args: [{
                    name: 's2Date',
                    standalone: true,
                }]
        }], ctorParameters: function () { return [{ type: i1.DatePipe }, { type: i2.S2UiSettingsService }]; } });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZGF0ZS5waXBlLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vLi4vLi4vLi4vcHJvamVjdHMvbmd4LXMyLXVpL3NyYy9saWIvcGlwZXMvZGF0ZS5waXBlLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUNBLE9BQU8sRUFBRSxJQUFJLEVBQWlCLE1BQU0sZUFBZSxDQUFDOzs7O0FBR3BELE1BSWEsVUFBVTtJQUNEO0lBQTRCO0lBQWhELFlBQW9CLFFBQWtCLEVBQVUsZUFBb0M7UUFBaEUsYUFBUSxHQUFSLFFBQVEsQ0FBVTtRQUFVLG9CQUFlLEdBQWYsZUFBZSxDQUFxQjtJQUFHLENBQUM7SUFFeEYsU0FBUyxDQUFDLEtBQTZCLEVBQUUsUUFBUSxHQUFHLEtBQUssRUFBRSxHQUFHLE9BQWlCO1FBQzdFLE9BQU8sSUFBSSxDQUFDLFFBQVEsQ0FBQyxTQUFTLENBQzVCLEtBQUssRUFDTCxJQUFJLENBQUMsZUFBZSxDQUFDLFFBQVEsQ0FBQyxRQUFRLENBQUMsQ0FBQyxDQUFDLGdCQUFnQixDQUFDLENBQUMsQ0FBQyxZQUFZLENBQUMsRUFDekUsR0FBRyxPQUFPLENBQ1gsQ0FBQztJQUNKLENBQUM7dUdBVFUsVUFBVTtxR0FBVixVQUFVOztTQUFWLFVBQVU7MkZBQVYsVUFBVTtrQkFKdEIsSUFBSTttQkFBQztvQkFDSixJQUFJLEVBQUUsUUFBUTtvQkFDZCxVQUFVLEVBQUUsSUFBSTtpQkFDakIiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBEYXRlUGlwZSB9IGZyb20gJ0Bhbmd1bGFyL2NvbW1vbic7XHJcbmltcG9ydCB7IFBpcGUsIFBpcGVUcmFuc2Zvcm0gfSBmcm9tICdAYW5ndWxhci9jb3JlJztcclxuaW1wb3J0IHsgUzJVaVNldHRpbmdzU2VydmljZSB9IGZyb20gJy4uL3NlcnZpY2VzL3MyLXVpLXNldHRpbmdzLnNlcnZpY2UnO1xyXG5cclxuQFBpcGUoe1xyXG4gIG5hbWU6ICdzMkRhdGUnLFxyXG4gIHN0YW5kYWxvbmU6IHRydWUsXHJcbn0pXHJcbmV4cG9ydCBjbGFzcyBTMkRhdGVQaXBlIGltcGxlbWVudHMgUGlwZVRyYW5zZm9ybSB7XHJcbiAgY29uc3RydWN0b3IocHJpdmF0ZSBkYXRlUGlwZTogRGF0ZVBpcGUsIHByaXZhdGUgc2V0dGluZ3NTZXJ2aWNlOiBTMlVpU2V0dGluZ3NTZXJ2aWNlKSB7fVxyXG5cclxuICB0cmFuc2Zvcm0odmFsdWU6IERhdGUgfCBudW1iZXIgfCBzdHJpbmcsIHNob3dUaW1lID0gZmFsc2UsIC4uLm9wdGlvbnM6IHN0cmluZ1tdKTogc3RyaW5nIHtcclxuICAgIHJldHVybiB0aGlzLmRhdGVQaXBlLnRyYW5zZm9ybShcclxuICAgICAgdmFsdWUsXHJcbiAgICAgIHRoaXMuc2V0dGluZ3NTZXJ2aWNlLnNldHRpbmdzW3Nob3dUaW1lID8gJ2RhdGVUaW1lRm9ybWF0JyA6ICdkYXRlRm9ybWF0J10sXHJcbiAgICAgIC4uLm9wdGlvbnNcclxuICAgICk7XHJcbiAgfVxyXG59XHJcbiJdfQ==