import { PipeTransform } from '@angular/core';
import * as i0 from "@angular/core";
export declare class ReplacePipe implements PipeTransform {
    transform(value: string, pattern: string | RegExp, replacement: string): unknown;
    static ɵfac: i0.ɵɵFactoryDeclaration<ReplacePipe, never>;
    static ɵpipe: i0.ɵɵPipeDeclaration<ReplacePipe, "replace", true>;
}
