import { Logger } from './logger.service';
import { ConsoleLoggerService } from './console-logger.service';

export class LoggerFactory {
  public static getLogger(): Logger {
    return new ConsoleLoggerService();
  }
}
