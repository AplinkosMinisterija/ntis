import { S2Message } from '../model/common.api';
export declare class S2Error extends Error {
    messages: S2Message[];
    constructor(messages: S2Message[]);
}
