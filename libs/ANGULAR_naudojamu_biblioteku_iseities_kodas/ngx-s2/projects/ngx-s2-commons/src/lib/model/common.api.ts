export interface ClientSideError {
  clientErrorTime: string;
  errorCode: string;
  errorMessage: string;
  user: string;
}

export interface LoginResult<T> {
  session: T;
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

export interface Serializable {}

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

export type SparkMessageType = 'FATAL' | 'ERROR' | 'CONFIRM' | 'WARNING' | 'INFO' | 'SUCCESS';
