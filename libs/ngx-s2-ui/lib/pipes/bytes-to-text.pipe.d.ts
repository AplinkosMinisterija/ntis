import { PipeTransform } from '@angular/core';
import * as i0 from "@angular/core";
export declare class BytesToTextPipe implements PipeTransform {
    static readonly defaultPrecision = 2;
    static readonly defaultUnits: string[];
    transform(bytes: number, precision?: number, units?: string[]): string;
    static ɵfac: i0.ɵɵFactoryDeclaration<BytesToTextPipe, never>;
    static ɵpipe: i0.ɵɵPipeDeclaration<BytesToTextPipe, "bytesToText", true>;
}
