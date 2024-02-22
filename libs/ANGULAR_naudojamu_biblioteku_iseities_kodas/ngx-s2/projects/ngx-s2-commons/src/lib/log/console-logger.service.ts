import { Injectable, isDevMode } from '@angular/core';

import { Logger } from './logger.service';

const noop = (): undefined => undefined;

@Injectable()
export class ConsoleLoggerService implements Logger {
  get debug(): CallableFunction {
    if (isDevMode()) {
      return console.debug.bind(console);
    } else {
      return noop;
    }
  }

  get info(): CallableFunction {
    return console.info.bind(console);
  }

  get warn(): CallableFunction {
    return console.warn.bind(console);
  }

  get error(): CallableFunction {
    return console.error.bind(console);
  }
}
