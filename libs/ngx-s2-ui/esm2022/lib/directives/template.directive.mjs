import { Directive, Input } from '@angular/core';
import * as i0 from "@angular/core";
class TemplateDirective {
    template;
    s2Template = '';
    s2TemplateExtras;
    constructor(template) {
        this.template = template;
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: TemplateDirective, deps: [{ token: i0.TemplateRef }], target: i0.ɵɵFactoryTarget.Directive });
    static ɵdir = i0.ɵɵngDeclareDirective({ minVersion: "14.0.0", version: "16.0.2", type: TemplateDirective, isStandalone: true, selector: "[s2Template]", inputs: { s2Template: "s2Template", s2TemplateExtras: "s2TemplateExtras" }, ngImport: i0 });
}
export { TemplateDirective };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: TemplateDirective, decorators: [{
            type: Directive,
            args: [{
                    selector: '[s2Template]',
                    standalone: true,
                }]
        }], ctorParameters: function () { return [{ type: i0.TemplateRef }]; }, propDecorators: { s2Template: [{
                type: Input
            }], s2TemplateExtras: [{
                type: Input
            }] } });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoidGVtcGxhdGUuZGlyZWN0aXZlLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vLi4vLi4vLi4vcHJvamVjdHMvbmd4LXMyLXVpL3NyYy9saWIvZGlyZWN0aXZlcy90ZW1wbGF0ZS5kaXJlY3RpdmUudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsT0FBTyxFQUFFLFNBQVMsRUFBRSxLQUFLLEVBQWUsTUFBTSxlQUFlLENBQUM7O0FBRTlELE1BSWEsaUJBQWlCO0lBR1Q7SUFGVixVQUFVLEdBQUcsRUFBRSxDQUFDO0lBQ2hCLGdCQUFnQixDQUFJO0lBQzdCLFlBQW1CLFFBQThCO1FBQTlCLGFBQVEsR0FBUixRQUFRLENBQXNCO0lBQUcsQ0FBQzt1R0FIMUMsaUJBQWlCOzJGQUFqQixpQkFBaUI7O1NBQWpCLGlCQUFpQjsyRkFBakIsaUJBQWlCO2tCQUo3QixTQUFTO21CQUFDO29CQUNULFFBQVEsRUFBRSxjQUFjO29CQUN4QixVQUFVLEVBQUUsSUFBSTtpQkFDakI7a0dBRVUsVUFBVTtzQkFBbEIsS0FBSztnQkFDRyxnQkFBZ0I7c0JBQXhCLEtBQUsiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBEaXJlY3RpdmUsIElucHV0LCBUZW1wbGF0ZVJlZiB9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xyXG5cclxuQERpcmVjdGl2ZSh7XHJcbiAgc2VsZWN0b3I6ICdbczJUZW1wbGF0ZV0nLFxyXG4gIHN0YW5kYWxvbmU6IHRydWUsXHJcbn0pXHJcbmV4cG9ydCBjbGFzcyBUZW1wbGF0ZURpcmVjdGl2ZTxUID0gdW5rbm93bj4ge1xyXG4gIEBJbnB1dCgpIHMyVGVtcGxhdGUgPSAnJztcclxuICBASW5wdXQoKSBzMlRlbXBsYXRlRXh0cmFzOiBUO1xyXG4gIGNvbnN0cnVjdG9yKHB1YmxpYyB0ZW1wbGF0ZTogVGVtcGxhdGVSZWY8dW5rbm93bj4pIHt9XHJcbn1cclxuIl19