export interface ChangePasswordRequest {
    newPassword: string;
    oldPassword: string;
}
export interface CheckUserNameExistRequest extends Serializable {
    username: string;
}
export interface ClientSideError {
    clientErrorTime: string;
    errorCode: string;
    errorMessage: string;
    user: string;
}
export interface CreateNewUserRequest extends Serializable {
    password: string;
    username: string;
}
export interface CreatePasswordRequest {
    password: string;
    token: string;
}
export interface Key_values_ot extends Serializable {
    code: string;
    display_text: string;
    key_value: string;
}
export interface LoginRequest {
    password: string;
    username: string;
}
export interface LoginResult<T> {
    session: T;
    token: string;
}
export interface NewPasswordRequest extends Serializable {
    email: string;
}
export interface RenewPasswordRequest extends Serializable {
    email: string;
    token: string;
}
export interface S2Message extends SparkUserMessage {
    key: string;
    params: string[];
    source: string;
    text: string;
    type: SparkMessageType;
}
export interface S2ViolatedConstraint {
    fields: string[];
    name: string;
}
export interface Serializable {
}
export interface ServerErrorForClientSide {
    cause: string;
    stackTrace: string;
}
export interface SparkUserMessage extends Serializable {
    code: string;
    default_text: string;
    item: string;
    module: string;
    param1: string;
    param2: string;
    param3: string;
    param4: string;
    param5: string;
    severity: string;
}
export interface Spr_paging_ot extends Serializable {
    cnt: number;
    order_clause: string;
    page_size: number;
    skip_rows: number;
    sum_values: Key_values_ot[];
}
export interface WebSessionInfo {
    defaultRoute: string;
    language: string;
    orgId: number;
    orgName: string;
    personLastName: string;
    personName: string;
    role: string;
    roleCode: string;
    roleId: number;
    roleName: string;
    userName: string;
    usrPasswordChangeToken: string;
    usrTermsAccepted: string;
}
export type SparkMessageType = "FATAL" | "ERROR" | "CONFIRM" | "WARNING" | "INFO" | "SUCCESS";
