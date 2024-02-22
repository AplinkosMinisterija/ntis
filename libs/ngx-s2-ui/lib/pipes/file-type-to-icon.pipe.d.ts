import { PipeTransform } from '@angular/core';
import * as i0 from "@angular/core";
export declare class FileTypeToIconPipe implements PipeTransform {
    static icons: Record<string, string>;
    transform(value: string, defaultIcon?: string): string;
    private getWildcardType;
    static ɵfac: i0.ɵɵFactoryDeclaration<FileTypeToIconPipe, never>;
    static ɵpipe: i0.ɵɵPipeDeclaration<FileTypeToIconPipe, "fileTypeToIcon", true>;
    static ɵprov: i0.ɵɵInjectableDeclaration<FileTypeToIconPipe>;
}
