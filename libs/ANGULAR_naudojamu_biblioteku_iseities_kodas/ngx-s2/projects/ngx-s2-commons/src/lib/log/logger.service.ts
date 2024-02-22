import { Injectable } from '@angular/core';

export abstract class Logger {
  debug: CallableFunction;
  info: CallableFunction;
  warn: CallableFunction;
  error: CallableFunction;
}

@Injectable()
export class LoggerService implements Logger {
  debug: CallableFunction;
  info: CallableFunction;
  warn: CallableFunction;
  error: CallableFunction;
}
