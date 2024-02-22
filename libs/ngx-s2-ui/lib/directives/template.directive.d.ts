import { TemplateRef } from '@angular/core';
import * as i0 from "@angular/core";
export declare class TemplateDirective<T = unknown> {
    template: TemplateRef<unknown>;
    s2Template: string;
    s2TemplateExtras: T;
    constructor(template: TemplateRef<unknown>);
    static ɵfac: i0.ɵɵFactoryDeclaration<TemplateDirective<any>, never>;
    static ɵdir: i0.ɵɵDirectiveDeclaration<TemplateDirective<any>, "[s2Template]", never, { "s2Template": { "alias": "s2Template"; "required": false; }; "s2TemplateExtras": { "alias": "s2TemplateExtras"; "required": false; }; }, {}, never, never, true, never>;
}
