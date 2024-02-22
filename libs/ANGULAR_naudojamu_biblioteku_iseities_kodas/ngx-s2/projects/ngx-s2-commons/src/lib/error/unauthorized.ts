export class UnauthorizedError extends Error {

    constructor() {
        super();
        this.name = UnauthorizedError.name;
        Object.setPrototypeOf(this, UnauthorizedError.prototype);
    }
}
