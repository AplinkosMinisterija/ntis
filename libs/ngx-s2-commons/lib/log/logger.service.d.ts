import * as i0 from "@angular/core";
export declare abstract class Logger {
    debug: CallableFunction;
    info: CallableFunction;
    warn: CallableFunction;
    error: CallableFunction;
}
export declare class LoggerService implements Logger {
    debug: CallableFunction;
    info: CallableFunction;
    warn: CallableFunction;
    error: CallableFunction;
    static ɵfac: i0.ɵɵFactoryDeclaration<LoggerService, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<LoggerService>;
}
