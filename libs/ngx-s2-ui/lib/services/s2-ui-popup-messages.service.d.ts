import * as i0 from "@angular/core";
export interface S2UiPopupMessage<T = unknown> {
    closable?: boolean;
    containerKey?: string | number;
    data?: T;
    duration?: number;
    heading?: string | null;
    icon?: string;
    key?: string | number;
    severity: 'error' | 'info' | 'success' | 'warning' | null;
    text?: string;
}
export declare class S2UiPopupMessagesService {
    messages: S2UiPopupMessage[];
    add(message: S2UiPopupMessage): void;
    addAll(messages: S2UiPopupMessage[]): void;
    remove(message: S2UiPopupMessage): boolean;
    removeAt(index: number): boolean;
    removeByKey(key: S2UiPopupMessage['key']): boolean;
    clear(): void;
    static ɵfac: i0.ɵɵFactoryDeclaration<S2UiPopupMessagesService, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<S2UiPopupMessagesService>;
}
