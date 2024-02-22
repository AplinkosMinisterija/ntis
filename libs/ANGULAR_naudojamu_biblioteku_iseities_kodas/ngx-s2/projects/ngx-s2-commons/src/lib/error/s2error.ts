import { S2Message } from '../model/common.api';

export class S2Error extends Error {
  constructor(public messages: S2Message[]) {
    super();
    this.name = S2Error.name;
    Object.setPrototypeOf(this, S2Error.prototype);
  }
}
