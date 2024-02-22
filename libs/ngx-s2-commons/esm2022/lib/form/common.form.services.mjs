import { Injectable } from '@angular/core';
import * as i0 from "@angular/core";
import * as i1 from "@angular/router";
import * as i2 from "../message/app.messages";
import * as i3 from "@ngx-translate/core";
import * as i4 from "primeng/api";
class CommonFormServices {
    route;
    router;
    appMessages;
    translate;
    confirmationService;
    calendarLt = {
        closeText: 'Done',
        prevText: 'Prev',
        nextText: 'Next',
        currentText: 'Today',
        today: 'Šiandien',
        clear: 'Išvalyti',
        monthNames: [
            'sausis',
            'vasaris',
            'kovas',
            'balandis',
            'gegužė',
            'birželis',
            'liepa',
            'rugpjūtis',
            'rugsėjis',
            'spalis',
            'lapkritis',
            'gruodis',
        ],
        monthNamesShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
        dayNamesShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
        dayNamesMin: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
        weekHeader: 'Wk',
        dateFormat: 'dd/mm/yy',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
    };
    constructor(route, router, appMessages, translate, confirmationService) {
        this.route = route;
        this.router = router;
        this.appMessages = appMessages;
        this.translate = translate;
        this.confirmationService = confirmationService;
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CommonFormServices, deps: [{ token: i1.ActivatedRoute }, { token: i1.Router }, { token: i2.AppMessages }, { token: i3.TranslateService }, { token: i4.ConfirmationService }], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CommonFormServices, providedIn: 'root' });
}
export { CommonFormServices };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CommonFormServices, decorators: [{
            type: Injectable,
            args: [{
                    providedIn: 'root',
                }]
        }], ctorParameters: function () { return [{ type: i1.ActivatedRoute }, { type: i1.Router }, { type: i2.AppMessages }, { type: i3.TranslateService }, { type: i4.ConfirmationService }]; } });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY29tbW9uLmZvcm0uc2VydmljZXMuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi8uLi8uLi8uLi8uLi9wcm9qZWN0cy9uZ3gtczItY29tbW9ucy9zcmMvbGliL2Zvcm0vY29tbW9uLmZvcm0uc2VydmljZXMudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBRUEsT0FBTyxFQUFFLFVBQVUsRUFBRSxNQUFNLGVBQWUsQ0FBQzs7Ozs7O0FBSTNDLE1BR2Esa0JBQWtCO0lBbUNwQjtJQUNBO0lBQ0E7SUFDQTtJQUNBO0lBdENULFVBQVUsR0FBRztRQUNYLFNBQVMsRUFBRSxNQUFNO1FBQ2pCLFFBQVEsRUFBRSxNQUFNO1FBQ2hCLFFBQVEsRUFBRSxNQUFNO1FBQ2hCLFdBQVcsRUFBRSxPQUFPO1FBQ3BCLEtBQUssRUFBRSxVQUFVO1FBQ2pCLEtBQUssRUFBRSxVQUFVO1FBQ2pCLFVBQVUsRUFBRTtZQUNWLFFBQVE7WUFDUixTQUFTO1lBQ1QsT0FBTztZQUNQLFVBQVU7WUFDVixRQUFRO1lBQ1IsVUFBVTtZQUNWLE9BQU87WUFDUCxXQUFXO1lBQ1gsVUFBVTtZQUNWLFFBQVE7WUFDUixXQUFXO1lBQ1gsU0FBUztTQUNWO1FBQ0QsZUFBZSxFQUFFLENBQUMsS0FBSyxFQUFFLEtBQUssRUFBRSxLQUFLLEVBQUUsS0FBSyxFQUFFLEtBQUssRUFBRSxLQUFLLEVBQUUsS0FBSyxFQUFFLEtBQUssRUFBRSxLQUFLLEVBQUUsS0FBSyxFQUFFLEtBQUssRUFBRSxLQUFLLENBQUM7UUFDckcsUUFBUSxFQUFFLENBQUMsUUFBUSxFQUFFLFFBQVEsRUFBRSxTQUFTLEVBQUUsV0FBVyxFQUFFLFVBQVUsRUFBRSxRQUFRLEVBQUUsVUFBVSxDQUFDO1FBQ3hGLGFBQWEsRUFBRSxDQUFDLEtBQUssRUFBRSxLQUFLLEVBQUUsS0FBSyxFQUFFLEtBQUssRUFBRSxLQUFLLEVBQUUsS0FBSyxFQUFFLEtBQUssQ0FBQztRQUNoRSxXQUFXLEVBQUUsQ0FBQyxJQUFJLEVBQUUsSUFBSSxFQUFFLElBQUksRUFBRSxJQUFJLEVBQUUsSUFBSSxFQUFFLElBQUksRUFBRSxJQUFJLENBQUM7UUFDdkQsVUFBVSxFQUFFLElBQUk7UUFDaEIsVUFBVSxFQUFFLFVBQVU7UUFDdEIsUUFBUSxFQUFFLENBQUM7UUFDWCxLQUFLLEVBQUUsS0FBSztRQUNaLGtCQUFrQixFQUFFLEtBQUs7UUFDekIsVUFBVSxFQUFFLEVBQUU7S0FDZixDQUFDO0lBRUYsWUFDUyxLQUFxQixFQUNyQixNQUFjLEVBQ2QsV0FBd0IsRUFDeEIsU0FBMkIsRUFDM0IsbUJBQXdDO1FBSnhDLFVBQUssR0FBTCxLQUFLLENBQWdCO1FBQ3JCLFdBQU0sR0FBTixNQUFNLENBQVE7UUFDZCxnQkFBVyxHQUFYLFdBQVcsQ0FBYTtRQUN4QixjQUFTLEdBQVQsU0FBUyxDQUFrQjtRQUMzQix3QkFBbUIsR0FBbkIsbUJBQW1CLENBQXFCO0lBQzlDLENBQUM7dUdBeENPLGtCQUFrQjsyR0FBbEIsa0JBQWtCLGNBRmpCLE1BQU07O1NBRVAsa0JBQWtCOzJGQUFsQixrQkFBa0I7a0JBSDlCLFVBQVU7bUJBQUM7b0JBQ1YsVUFBVSxFQUFFLE1BQU07aUJBQ25CIiwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHsgQ29uZmlybWF0aW9uU2VydmljZSB9IGZyb20gJ3ByaW1lbmcvYXBpJztcclxuaW1wb3J0IHsgQWN0aXZhdGVkUm91dGUsIFJvdXRlciB9IGZyb20gJ0Bhbmd1bGFyL3JvdXRlcic7XHJcbmltcG9ydCB7IEluamVjdGFibGUgfSBmcm9tICdAYW5ndWxhci9jb3JlJztcclxuaW1wb3J0IHsgQXBwTWVzc2FnZXMgfSBmcm9tICcuLi9tZXNzYWdlL2FwcC5tZXNzYWdlcyc7XHJcbmltcG9ydCB7IFRyYW5zbGF0ZVNlcnZpY2UgfSBmcm9tICdAbmd4LXRyYW5zbGF0ZS9jb3JlJztcclxuXHJcbkBJbmplY3RhYmxlKHtcclxuICBwcm92aWRlZEluOiAncm9vdCcsXHJcbn0pXHJcbmV4cG9ydCBjbGFzcyBDb21tb25Gb3JtU2VydmljZXMge1xyXG4gIGNhbGVuZGFyTHQgPSB7XHJcbiAgICBjbG9zZVRleHQ6ICdEb25lJyxcclxuICAgIHByZXZUZXh0OiAnUHJldicsXHJcbiAgICBuZXh0VGV4dDogJ05leHQnLFxyXG4gICAgY3VycmVudFRleHQ6ICdUb2RheScsXHJcbiAgICB0b2RheTogJ8WgaWFuZGllbicsXHJcbiAgICBjbGVhcjogJ0nFoXZhbHl0aScsXHJcbiAgICBtb250aE5hbWVzOiBbXHJcbiAgICAgICdzYXVzaXMnLFxyXG4gICAgICAndmFzYXJpcycsXHJcbiAgICAgICdrb3ZhcycsXHJcbiAgICAgICdiYWxhbmRpcycsXHJcbiAgICAgICdnZWd1xb7ElycsXHJcbiAgICAgICdiaXLFvmVsaXMnLFxyXG4gICAgICAnbGllcGEnLFxyXG4gICAgICAncnVncGrFq3RpcycsXHJcbiAgICAgICdydWdzxJdqaXMnLFxyXG4gICAgICAnc3BhbGlzJyxcclxuICAgICAgJ2xhcGtyaXRpcycsXHJcbiAgICAgICdncnVvZGlzJyxcclxuICAgIF0sXHJcbiAgICBtb250aE5hbWVzU2hvcnQ6IFsnSmFuJywgJ0ZlYicsICdNYXInLCAnQXByJywgJ01heScsICdKdW4nLCAnSnVsJywgJ0F1ZycsICdTZXAnLCAnT2N0JywgJ05vdicsICdEZWMnXSxcclxuICAgIGRheU5hbWVzOiBbJ1N1bmRheScsICdNb25kYXknLCAnVHVlc2RheScsICdXZWRuZXNkYXknLCAnVGh1cnNkYXknLCAnRnJpZGF5JywgJ1NhdHVyZGF5J10sXHJcbiAgICBkYXlOYW1lc1Nob3J0OiBbJ1N1bicsICdNb24nLCAnVHVlJywgJ1dlZCcsICdUaHUnLCAnRnJpJywgJ1NhdCddLFxyXG4gICAgZGF5TmFtZXNNaW46IFsnU3UnLCAnTW8nLCAnVHUnLCAnV2UnLCAnVGgnLCAnRnInLCAnU2EnXSxcclxuICAgIHdlZWtIZWFkZXI6ICdXaycsXHJcbiAgICBkYXRlRm9ybWF0OiAnZGQvbW0veXknLFxyXG4gICAgZmlyc3REYXk6IDEsXHJcbiAgICBpc1JUTDogZmFsc2UsXHJcbiAgICBzaG93TW9udGhBZnRlclllYXI6IGZhbHNlLFxyXG4gICAgeWVhclN1ZmZpeDogJycsXHJcbiAgfTtcclxuXHJcbiAgY29uc3RydWN0b3IoXHJcbiAgICBwdWJsaWMgcm91dGU6IEFjdGl2YXRlZFJvdXRlLFxyXG4gICAgcHVibGljIHJvdXRlcjogUm91dGVyLFxyXG4gICAgcHVibGljIGFwcE1lc3NhZ2VzOiBBcHBNZXNzYWdlcyxcclxuICAgIHB1YmxpYyB0cmFuc2xhdGU6IFRyYW5zbGF0ZVNlcnZpY2UsXHJcbiAgICBwdWJsaWMgY29uZmlybWF0aW9uU2VydmljZTogQ29uZmlybWF0aW9uU2VydmljZVxyXG4gICkge31cclxufVxyXG4iXX0=