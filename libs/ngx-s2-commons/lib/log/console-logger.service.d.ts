import { Logger } from './logger.service';
import * as i0 from "@angular/core";
export declare class ConsoleLoggerService implements Logger {
    get debug(): CallableFunction;
    get info(): CallableFunction;
    get warn(): CallableFunction;
    get error(): CallableFunction;
    static ɵfac: i0.ɵɵFactoryDeclaration<ConsoleLoggerService, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<ConsoleLoggerService>;
}
