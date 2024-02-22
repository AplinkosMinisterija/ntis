export class ServerError extends Error {

    constructor( public uuid: string ) {
        super();
        this.name = ServerError.name;
        Object.setPrototypeOf(this, ServerError.prototype);
    }
}
