export class ServerUnavailableError extends Error {
  constructor() {
    super();
    this.name = ServerUnavailableError.name;
    Object.setPrototypeOf(this, ServerUnavailableError.prototype);
  }
}
